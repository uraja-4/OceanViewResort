package oceanview.dao;

import oceanview.db.DBConnection;
import oceanview.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private final DBConnection db = DBConnection.getInstance();

    public User login(String username, String password) {
        User user = null;
        String sql = "SELECT user_id, username, password, role, full_name, address, contact_number FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                user.setFullName(rs.getString("full_name"));
                user.setAddress(rs.getString("address"));
                user.setContactNumber(rs.getString("contact_number"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return user;
    }

    public boolean register(User user) {
        String sql = "INSERT INTO users (username, password, role, full_name, address, contact_number) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, "GUEST");
            ps.setString(4, user.getFullName());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getContactNumber());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public User getById(int userId) {
        User user = null;
        String sql = "SELECT user_id, username, role, full_name, address, contact_number FROM users WHERE user_id = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                user.setFullName(rs.getString("full_name"));
                user.setAddress(rs.getString("address"));
                user.setContactNumber(rs.getString("contact_number"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return user;
    }

    public boolean usernameExists(String username) {
        String sql = "SELECT 1 FROM users WHERE username = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, username);
            return ps.executeQuery().next();
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}
