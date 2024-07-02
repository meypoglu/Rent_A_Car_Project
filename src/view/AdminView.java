package view;

import business.BookManager;
import business.BrandManager;
import business.CarManager;
import business.ModelManager;
import core.ComboItem;
import core.Helper;
import entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Array;
import java.text.ParseException;
import java.util.ArrayList;

public class AdminView extends Layout{
    private JPanel container;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JTabbedPane tab_menu;
    private JButton btn_logout;
    private JPanel pnl_brand;
    private JScrollPane scrl_brand;
    private JTable tbl_brand;
    private JPanel pnl_model;
    private JScrollPane scrl_model;
    private JTable tbl_model;
    private JComboBox cmb_s_brand;
    private JComboBox cmb_s_type;
    private JComboBox cmb_s_fuel;
    private JComboBox cmb_s_gear;
    private JButton btn_search_model;
    private JButton btn_clear;
    private JPanel pnl_car;
    private JTable tbl_car;
    private JScrollPane scrl_car;
    private JPanel pnl_booking;
    private JScrollPane scrl_booking;
    private JPanel pnl_booking_search;
    private JFormattedTextField fld_strt_date;
    private JFormattedTextField fld_fnsh_date;
    private JComboBox cmb_booking_gear;
    private JComboBox cmb_booking_type;
    private JComboBox cmb_booking_fuel;
    private JButton btn_search;
    private JTable tbl_booking;
    private JButton btn_booking_clear;
    private JTable tbl_all_bookings;
    private JScrollPane scrl_all_bookings;
    private JPanel pnl_all_bookings;
    private JComboBox cmb_plate;
    private JButton btn_filter;
    private User user;
    private DefaultTableModel tmdl_brand = new DefaultTableModel();
    private DefaultTableModel tmdl_model = new DefaultTableModel();
    private DefaultTableModel tmdl_car = new DefaultTableModel();
    private DefaultTableModel tmdl_booking = new DefaultTableModel();
    private DefaultTableModel tmdl_all_bookings = new DefaultTableModel();
    private BrandManager brandManager;
    private JPopupMenu brand_menu;
    private JPopupMenu model_menu;
    private JPopupMenu car_menu;
    private JPopupMenu booking_menu;
    private JPopupMenu all_bookings_menu;
    private ModelManager modelManager;
    private CarManager carManager;
    private BookManager bookManager;
    private CarView carView;
    private Object[] col_model;
    private Object[] col_car;

    public AdminView(User user) {
        this.brandManager = new BrandManager();
        this.modelManager = new ModelManager();
        this.carManager = new CarManager();
        this.bookManager = new BookManager();
        this.add(container);
        this.guiInitialize(1000, 500);
        this.user = user;
        if (this.user == null) {
            dispose();
        }
        this.lbl_welcome.setText("Hoşgeldiniz " + this.user.getUsername());

        loadBrandTable();
        loadBrandComponent();
        loadModelTable(null);
        loadModelComponent();
        loadModelFilter();
        loadCarTable();
        loadCarComponent();
        loadBookingTable(null);
        loadBookingComponent();
        loadBookingFilter();

        loadAllBookingsTable();
        loadAllBookingsComponent();
        loadAllBookingsFilter();


        btn_logout.addActionListener(e -> {
            dispose();
        });
    }

    private void loadAllBookingsTable() {
        Object[] col_booking_list = {"Plaka", "Marka", "Model", "Renk"};
        ArrayList<Book> bookList = this.bookManager.findAll();
        ArrayList<Object[]> rowList = new ArrayList<>();

        for(Book book : bookList) {
            Object[] row = {
                    book.getId(),
                    book.getCarPlate(),
                    book.getCarBrand(),
                    book.getCarModel(),
                    book.getCar().getColor()
            };
            rowList.add(row);
        }
        createTable(this.tmdl_all_bookings, this.tbl_all_bookings, col_booking_list, rowList);

    }

    private void loadBookingTable(ArrayList<Object[]> carList) {
        Object[] col_booking_list = {"ID", "Marka", "Model", "Plaka", "Renk", "KM", "Yıl", "Tip", "Yakıt Türü", "Vites"};
        createTable(this.tmdl_booking, this.tbl_booking, col_booking_list, carList);
    }

    public void loadAllBookingsFilter() {
        this.cmb_plate.removeAllItems();
        this.cmb_plate.addItem("Tümü");
        for (Car car : carManager.findAll()) {
            cmb_plate.addItem(car.getPlate());
        }

        btn_filter.addActionListener(e -> {
            String selectedPlate =  (String) cmb_plate.getSelectedItem();
            if (selectedPlate.equals("Tümü")) {
                loadAllBookingsTable();
            } else {
                ArrayList<Book> filteredBookings = bookManager.findByPlate(selectedPlate);
                ArrayList<Object[]> rowList = new ArrayList<>();

                for (Book book : filteredBookings) {
                    Object[] row = {
                            book.getCar().getPlate(),
                            book.getCar().getBrand(),
                            book.getCar().getModel(),
                            book.getCar().getColor()
                    };
                    rowList.add(row);
                }
                createTable(tmdl_all_bookings, tbl_all_bookings, new Object[]{"Plaka", "Marka", "Model", "Renk"}, rowList);
            }
        });
    }

    public void loadBookingFilter() {
        this.cmb_booking_type.setModel(new DefaultComboBoxModel<>(Model.Type.values()));
        this.cmb_booking_type.setSelectedItem(null);
        this.cmb_booking_gear.setModel(new DefaultComboBoxModel<>(Model.Gear.values()));
        this.cmb_booking_gear.setSelectedItem(null);
        this.cmb_booking_fuel.setModel(new DefaultComboBoxModel<>(Model.Fuel.values()));
        this.cmb_booking_fuel.setSelectedItem(null);
    }

    private void loadAllBookingsComponent() {
        tableRowSelect(tbl_all_bookings);
        this.all_bookings_menu = new JPopupMenu();
        this.all_bookings_menu.add("Rezervasyonu sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectedRow = tbl_all_bookings.getSelectedRow();
                if (selectedRow != -1) {
                    try {
                        int bookingId = Integer.parseInt(tbl_all_bookings.getValueAt(selectedRow, 0).toString());
                        if (bookManager.delete(bookingId)) {
                            Helper.showMsg("done");
                            loadAllBookingsTable();
                        } else {
                            Helper.showMsg("error");
                        }
                    } catch (Exception event) {
                        Helper.showMsg("Geçersiz rezervasyon ID'si !");
                    }
                }
            }
        });
        tbl_all_bookings.setComponentPopupMenu(all_bookings_menu);
    }

    private void loadBookingComponent() {
        tableRowSelect(this.tbl_booking);
        this.booking_menu = new JPopupMenu();
        this.booking_menu.add("Rezervasyon Yap").addActionListener(e -> {
            int selectCarId = this.getTableSelectedRow(this.tbl_booking, 0);
            BookingView bookingView = new BookingView(this.carManager.getById(selectCarId), this.fld_strt_date.getText(), this.fld_fnsh_date.getText());
            bookingView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBookingTable(null);
                    loadBookingFilter();
                }
            });
        });
        this.tbl_booking.setComponentPopupMenu(booking_menu);

        btn_search.addActionListener(e -> {
            ArrayList<Car> carList = this.carManager.searchForBooking(fld_strt_date.getText(), fld_fnsh_date.getText(), (Model.Type) cmb_booking_type.getSelectedItem(), (Model.Gear) cmb_booking_gear.getSelectedItem(), (Model.Fuel) cmb_booking_fuel.getSelectedItem());
            ArrayList<Object[]> carBookingRow = this.carManager.getForTable(this.col_car.length, carList);
            loadBookingTable(carBookingRow);
        });

        btn_booking_clear.addActionListener(e -> {
            loadBookingFilter();
        });
    }

    private void loadCarComponent() {
        tableRowSelect(this.tbl_car);
        this.car_menu = new JPopupMenu();
        this.car_menu.add("Yeni").addActionListener(e -> {
            CarView carView = new CarView(new Car());
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCarTable();
                }
            });
        });
        this.car_menu.add("Güncelle").addActionListener(e -> {
            int selectModelId = this.getTableSelectedRow(tbl_car, 0);
            CarView carView = new CarView(this.carManager.getById(selectModelId));
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCarTable();
                }
            });
        });
        this.car_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectCarId = this.getTableSelectedRow(tbl_car, 0);
                if (this.carManager.delete(selectCarId)) {
                    Helper.showMsg("done");
                    loadCarTable();
                } else {
                    Helper.showMsg("error");
                }
            }
        });
        this.tbl_car.setComponentPopupMenu(car_menu);
    }

    private void loadModelComponent() {
        tableRowSelect(this.tbl_model);
        this.model_menu = new JPopupMenu();

        this.model_menu.add("Yeni").addActionListener(e -> {
            ModelView modelView = new ModelView(new Model());
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable(null);
                }
            });
        });
        this.model_menu.add("Güncelle").addActionListener(e -> {
            int selectModelId = this.getTableSelectedRow(tbl_model, 0);
            ModelView modelView = new ModelView(this.modelManager.getById(selectModelId));
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable(null);
                }
            });
        });
        this.model_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectModelId = this.getTableSelectedRow(tbl_model, 0);
                if (this.modelManager.delete(selectModelId)) {
                    Helper.showMsg("done");
                    loadModelTable(null);
                } else {
                    Helper.showMsg("error");
                }
            }
        });
        this.tbl_model.setComponentPopupMenu(model_menu);

        this.btn_search_model.addActionListener(e -> {
            ComboItem selectedBrand = (ComboItem) this.cmb_s_brand.getSelectedItem();
            int brandId = 0;
            if (selectedBrand != null) {
                brandId = selectedBrand.getKey();
            }
            ArrayList<Model> modelListBySearch = this.modelManager.searchForTable(brandId, (Model.Fuel) cmb_s_fuel.getSelectedItem(), (Model.Gear) cmb_s_gear.getSelectedItem(), (Model.Type) cmb_s_type.getSelectedItem());
            ArrayList<Object[]> modelRowListBySearch = this.modelManager.getForTable(this.col_model.length, modelListBySearch);
            loadModelTable(modelRowListBySearch);
        });

        this.btn_clear.addActionListener(e -> {
            this.cmb_s_type.setSelectedItem(null);
            this.cmb_s_gear.setSelectedItem(null);
            this.cmb_s_fuel.setSelectedItem(null);
            this.cmb_s_brand.setSelectedItem(null);
            loadModelTable(null);
        });
    }

    public void loadCarTable() {
        col_car = new Object[]{"ID", "Marka", "Model", "Plaka", "Renk", "KM", "Yıl", "Tip", "Yakıt Türü", "Vites"};
        ArrayList<Object[]> carList = this.carManager.getForTable(col_car.length, this.carManager.findAll());
        createTable(this.tmdl_car, this.tbl_car, col_car, carList);
    }

    public void loadModelTable(ArrayList<Object[]> modelList) {
        this.col_model = new Object[]{"Model ID", "Marka", "Model Adı", "Tip", "Yıl", "Yakıt Türü", "Vites"};
        if (modelList == null) {
            modelList = this.modelManager.getForTable(col_model.length, this.modelManager.findAll());
        }
        createTable(this.tmdl_model, this.tbl_model, col_model, modelList);
    }

    public void loadBrandTable() {
        Object[] col_brand = {"Marka ID", "Marka Adı"};
        ArrayList<Object[]> brandList = this.brandManager.getForTable(col_brand.length);
        this.createTable(this.tmdl_brand, this.tbl_brand, col_brand, brandList);
    }

    public void loadBrandComponent() {
        tableRowSelect(this.tbl_brand);

        this.brand_menu = new JPopupMenu();

        this.brand_menu.add("Yeni").addActionListener(e -> {
            BrandView brandView = new BrandView(null);
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilterBrand();
                }
            });

        });
        this.brand_menu.add("Güncelle").addActionListener(e -> {
            int selectId = this.getTableSelectedRow(tbl_brand, 0);
            BrandView brandView = new BrandView(this.brandManager.getById(selectId));
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilterBrand();
                    loadCarTable();
                }
            });
        });
        this.brand_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectId = this.getTableSelectedRow(tbl_brand, 0);
                if (this.brandManager.delete(selectId)) {
                    Helper.showMsg("done");
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilterBrand();
                    loadCarTable();
                } else {
                    Helper.showMsg("error");
                }
            }
        });

        this.tbl_brand.setComponentPopupMenu(this.brand_menu);
    }

    public void loadModelFilter() {
        this.cmb_s_type.setModel(new DefaultComboBoxModel<>(Model.Type.values()));
        this.cmb_s_type.setSelectedItem(null);
        this.cmb_s_gear.setModel(new DefaultComboBoxModel<>(Model.Gear.values()));
        this.cmb_s_gear.setSelectedItem(null);
        this.cmb_s_fuel.setModel(new DefaultComboBoxModel<>(Model.Fuel.values()));
        this.cmb_s_fuel.setSelectedItem(null);
        loadModelFilterBrand();

    }

    public void loadModelFilterBrand() {
        this.cmb_s_brand.removeAllItems();
        for (Brand obj : brandManager.findAll()) {
            this.cmb_s_brand.addItem(new ComboItem(obj.getId(), obj.getName()));
        }
        this.cmb_s_brand.setSelectedItem(null);
    }

    private void createUIComponents() throws ParseException {
        this.fld_strt_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.fld_strt_date.setText("10/10/2023");
        this.fld_fnsh_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.fld_fnsh_date.setText("16/10/2023");
    }
}

