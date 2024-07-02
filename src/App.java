import business.UserManager;
import core.Db;
import core.Helper;
import dao.UserDao;
import entity.User;
import view.AdminView;
import view.LoginView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class App {
    public static void main(String[] args) {
        Helper.setTheme("Nimbus");

        UserManager userManager = new UserManager();
        AdminView adminView = new AdminView(userManager.findByLogin("admin", "123"));
    }
}
