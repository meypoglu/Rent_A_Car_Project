package core;

import java.sql.Connection;
import java.sql.DriverManager;

public class Db {
    private static Db instance = null;
    private Connection connection = null;
    private final String DB_URL = "jdbc:postgresql://localhost:5432/Rent_A_Car";
    private final String DB_USERNAME = "postgres";
    private final String DB_PASSWORD = "12345678";

    private Db() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static Connection getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new Db();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return instance.getConnection();
    }
}
