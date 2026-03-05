package oceanview.dao;

import oceanview.db.DBConnection;
import oceanview.model.RoomType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomTypeDAO {
    private final DBConnection db = DBConnection.getInstance();

    public List<RoomType> getAll() {
        List<RoomType> list = new ArrayList<>();
        String sql = "SELECT room_type_id, type_name, rate_per_night, total_rooms, description FROM room_types ORDER BY type_name";
        try (Statement st = db.getConnection().createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                RoomType rt = new RoomType();
                rt.setRoomTypeId(rs.getInt("room_type_id"));
                rt.setTypeName(rs.getString("type_name"));
                rt.setRatePerNight(rs.getBigDecimal("rate_per_night"));
                rt.setTotalRooms(rs.getInt("total_rooms"));
                rt.setDescription(rs.getString("description"));
                list.add(rt);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public RoomType getById(int id) {
        RoomType rt = null;
        String sql = "SELECT room_type_id, type_name, rate_per_night, total_rooms, description FROM room_types WHERE room_type_id = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rt = new RoomType();
                rt.setRoomTypeId(rs.getInt("room_type_id"));
                rt.setTypeName(rs.getString("type_name"));
                rt.setRatePerNight(rs.getBigDecimal("rate_per_night"));
                rt.setTotalRooms(rs.getInt("total_rooms"));
                rt.setDescription(rs.getString("description"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return rt;
    }
}
