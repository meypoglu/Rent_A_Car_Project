package dao;

import core.Db;
import entity.User;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;

public class UserDao {
    private final Connection conn;

    public UserDao() {
        this.conn = Db.getInstance();
    }

    public ArrayList<User> findAll() {
        ArrayList<User> userList = new ArrayList<>();
        try {
            ResultSet rs = this.conn.createStatement().executeQuery("SELECT * FROM public.users");
            while (rs.next()) {
                userList.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public User findByLogin(String username, String password) {
        User obj = null;
        try {
            PreparedStatement pr = this.conn.prepareStatement("SELECT * FROM public.users WHERE user_name = ? AND user_password = ?");
            pr.setString(1, username);
            pr.setString(2, password);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = this.match(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }

    public User match(ResultSet rs) {
        User obj;
        obj = new User();
        try {
            obj.setId(rs.getInt("user_id"));
            obj.setUsername(rs.getString("user_name"));
            obj.setPassword(rs.getString("user_password"));
            obj.setRole(rs.getString("user_role"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public void getTableNames() {
        DatabaseMetaData md = null;
        try {
            md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                System.out.println(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
