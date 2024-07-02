package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends Layout{
    private JPanel container;
    private JPanel w_top;
    private JLabel lbl_welcome;
    private JLabel lbl_welcome2;
    private JPanel w_bottom;
    private JTextField fld_username;
    private JPasswordField fld_password;
    private JButton btn_login;
    private JLabel lbl_username;
    private JLabel lbl_password;
    private final UserManager userManager;

    public LoginView() {
        this.userManager = new UserManager();
        this.add(container);
        this.guiInitialize(400, 400);

        btn_login.addActionListener(e -> {
            if (Helper.isFieldEmpty(this.fld_username)) {
                JOptionPane.showMessageDialog(null, "Kullanıcı adı giriniz!", "HATA", JOptionPane.INFORMATION_MESSAGE);
            }

            if (Helper.isFieldEmpty(this.fld_password)) {
                JOptionPane.showMessageDialog(null, "Şifre Giriniz!", "HATA", JOptionPane.INFORMATION_MESSAGE);
            }

            if (!Helper.isFieldEmpty(this.fld_username) && !Helper.isFieldEmpty(this.fld_password)) {
                User loginUser = this.userManager.findByLogin(this.fld_username.getText(), this.fld_password.getText());
                if (loginUser == null) {
                    JOptionPane.showMessageDialog(null, "Kullanıcı bulunamadı!", "HATA", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    AdminView adminView = new AdminView(loginUser);
                    dispose();
                }
            }
        });


    }
}
