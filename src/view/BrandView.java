package view;

import business.BrandManager;
import core.Helper;
import entity.Brand;

import javax.swing.*;

public class BrandView extends Layout{
    private JPanel container;
    private JLabel lbl_brand;
    private JLabel lbl_brand_name;
    private JTextField fld_brand_name;
    private JButton btn_brand_save;
    private Brand brand;
    private BrandManager brandManager;

    public BrandView(Brand brand) {
        this.brandManager = new BrandManager();
        this.add(container);
        this.guiInitialize(300, 200);
        this.brand = brand;

        if (brand != null) {
            fld_brand_name.setText(brand.getName());
        }

        btn_brand_save.addActionListener(e -> {
            if (Helper.isFieldEmpty(this.fld_brand_name)) {
                System.out.println("Alanları doldurunuz!");
            } else {
                boolean result = false;
                if (this.brand == null) {
                    result = this.brandManager.save(new Brand(fld_brand_name.getText()));
                } else {
                    this.brand.setName(fld_brand_name.getText());
                    result = this.brandManager.update(this.brand);
                }

                if (result == true) {
                    Helper.showMsg("done");
                    dispose();
                } else {
                    Helper.showMsg("error");
                }
            }
        });
    }
}
