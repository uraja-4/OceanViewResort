package oceanview.dao;

import oceanview.db.DBConnection;
import oceanview.model.Reservation;
import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    private final DBConnection db = DBConnection.getInstance();

    public String generateReservationNumber() {
        String sql = "SELECT COALESCE(MAX(reservation_id), 0) + 1 FROM reservations";
        try (Statement st = db.getConnection().createStatement(); ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                int next = rs.getInt(1);
                return "RES" + String.format("%06d", next);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return "RES000001";
    }

    public boolean reservationNumberExists(String num) {
        String sql = "SELECT 1 FROM reservations WHERE reservation_number = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, num);
            return ps.executeQuery().next();
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean add(Reservation r) {
        String sql = "INSERT INTO reservations (reservation_number, guest_id, guest_name, guest_address, guest_contact, room_type_id, check_in_date, check_out_date, status) VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, r.getReservationNumber());
            ps.setInt(2, r.getGuestId());
            ps.setString(3, r.getGuestName());
            ps.setString(4, r.getGuestAddress());
            ps.setString(5, r.getGuestContact());
            ps.setInt(6, r.getRoomTypeId());
            ps.setDate(7, new java.sql.Date(r.getCheckInDate().getTime()));
            ps.setDate(8, new java.sql.Date(r.getCheckOutDate().getTime()));
            ps.setString(9, r.getStatus() != null ? r.getStatus() : "CONFIRMED");
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public Reservation getByReservationNumber(String reservationNumber) {
        String sql = "SELECT r.*, rt.type_name, rt.rate_per_night FROM reservations r JOIN room_types rt ON r.room_type_id = rt.room_type_id WHERE r.reservation_number = ?";
        return getOne(sql, reservationNumber, true);
    }

    public Reservation getById(int id) {
        String sql = "SELECT r.*, rt.type_name, rt.rate_per_night FROM reservations r JOIN room_types rt ON r.room_type_id = rt.room_type_id WHERE r.reservation_id = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return mapReservation(rs);
        } catch (SQLException e) { e.printStackTrace(); return null; }
    }

    private Reservation getOne(String sql, String param, boolean isString) {
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            if (isString) ps.setString(1, param);
            else ps.setInt(1, Integer.parseInt(param));
            ResultSet rs = ps.executeQuery();
            return mapReservation(rs);
        } catch (SQLException e) { e.printStackTrace(); return null; }
    }

    private Reservation mapReservation(ResultSet rs) throws SQLException {
        if (!rs.next()) return null;
        Reservation r = new Reservation();
        r.setReservationId(rs.getInt("reservation_id"));
        r.setReservationNumber(rs.getString("reservation_number"));
        r.setGuestId(rs.getInt("guest_id"));
        r.setGuestName(rs.getString("guest_name"));
        r.setGuestAddress(rs.getString("guest_address"));
        r.setGuestContact(rs.getString("guest_contact"));
        r.setRoomTypeId(rs.getInt("room_type_id"));
        r.setRoomTypeName(rs.getString("type_name"));
        r.setRatePerNight(rs.getBigDecimal("rate_per_night"));
        r.setCheckInDate(rs.getDate("check_in_date"));
        r.setCheckOutDate(rs.getDate("check_out_date"));
        long diff = r.getCheckOutDate().getTime() - r.getCheckInDate().getTime();
        r.setNights((int) (diff / (24 * 60 * 60 * 1000)));
        r.setTotalCost(r.getRatePerNight().multiply(BigDecimal.valueOf(r.getNights())));
        r.setStatus(rs.getString("status"));
        r.setCancelledBy(rs.getString("cancelled_by"));
        r.setCancelledAt(rs.getTimestamp("cancelled_at") != null ? new Date(rs.getTimestamp("cancelled_at").getTime()) : null);
        r.setCancelReason(rs.getString("cancel_reason"));
        r.setCreatedAt(rs.getTimestamp("created_at"));
        return r;
    }

    public List<Reservation> getByGuestId(int guestId) {
        String sql = "SELECT r.*, rt.type_name, rt.rate_per_night FROM reservations r JOIN room_types rt ON r.room_type_id = rt.room_type_id WHERE r.guest_id = ? ORDER BY r.check_in_date DESC";
        return getList(sql, guestId);
    }

    public List<Reservation> getAll() {
        String sql = "SELECT r.*, rt.type_name, rt.rate_per_night FROM reservations r JOIN room_types rt ON r.room_type_id = rt.room_type_id ORDER BY r.check_in_date DESC";
        List<Reservation> list = new ArrayList<>();
        try (Statement st = db.getConnection().createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Reservation r = new Reservation();
                r.setReservationId(rs.getInt("reservation_id"));
                r.setReservationNumber(rs.getString("reservation_number"));
                r.setGuestId(rs.getInt("guest_id"));
                r.setGuestName(rs.getString("guest_name"));
                r.setGuestAddress(rs.getString("guest_address"));
                r.setGuestContact(rs.getString("guest_contact"));
                r.setRoomTypeId(rs.getInt("room_type_id"));
                r.setRoomTypeName(rs.getString("type_name"));
                r.setRatePerNight(rs.getBigDecimal("rate_per_night"));
                r.setCheckInDate(rs.getDate("check_in_date"));
                r.setCheckOutDate(rs.getDate("check_out_date"));
                long diff = r.getCheckOutDate().getTime() - r.getCheckInDate().getTime();
                r.setNights((int) (diff / (24 * 60 * 60 * 1000)));
                r.setTotalCost(r.getRatePerNight().multiply(BigDecimal.valueOf(r.getNights())));
                r.setStatus(rs.getString("status"));
                r.setCancelledBy(rs.getString("cancelled_by"));
                r.setCancelledAt(rs.getTimestamp("cancelled_at") != null ? new Date(rs.getTimestamp("cancelled_at").getTime()) : null);
                r.setCancelReason(rs.getString("cancel_reason"));
                r.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(r);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    private List<Reservation> getList(String sql, int guestId) {
        List<Reservation> list = new ArrayList<>();
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, guestId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reservation r = new Reservation();
                r.setReservationId(rs.getInt("reservation_id"));
                r.setReservationNumber(rs.getString("reservation_number"));
                r.setGuestId(rs.getInt("guest_id"));
                r.setGuestName(rs.getString("guest_name"));
                r.setGuestAddress(rs.getString("guest_address"));
                r.setGuestContact(rs.getString("guest_contact"));
                r.setRoomTypeId(rs.getInt("room_type_id"));
                r.setRoomTypeName(rs.getString("type_name"));
                r.setRatePerNight(rs.getBigDecimal("rate_per_night"));
                r.setCheckInDate(rs.getDate("check_in_date"));
                r.setCheckOutDate(rs.getDate("check_out_date"));
                long diff = r.getCheckOutDate().getTime() - r.getCheckInDate().getTime();
                r.setNights((int) (diff / (24 * 60 * 60 * 1000)));
                r.setTotalCost(r.getRatePerNight().multiply(BigDecimal.valueOf(r.getNights())));
                r.setStatus(rs.getString("status"));
                r.setCancelledBy(rs.getString("cancelled_by"));
                r.setCancelledAt(rs.getTimestamp("cancelled_at") != null ? new Date(rs.getTimestamp("cancelled_at").getTime()) : null);
                r.setCancelReason(rs.getString("cancel_reason"));
                r.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(r);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public int countToday() {
        String sql = "SELECT COUNT(*) FROM reservations WHERE status = 'CONFIRMED' AND check_in_date = CURDATE()";
        return countQuery(sql);
    }

    public int countThisWeek() {
        String sql = "SELECT COUNT(*) FROM reservations WHERE status = 'CONFIRMED' AND YEARWEEK(check_in_date) = YEARWEEK(CURDATE())";
        return countQuery(sql);
    }

    public int countThisMonth() {
        String sql = "SELECT COUNT(*) FROM reservations WHERE status = 'CONFIRMED' AND YEAR(check_in_date) = YEAR(CURDATE()) AND MONTH(check_in_date) = MONTH(CURDATE())";
        return countQuery(sql);
    }

    private int countQuery(String sql) {
        try (Statement st = db.getConnection().createStatement(); ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) { e.printStackTrace(); return 0; }
    }

    public List<Reservation> upcomingCheckIns(int days) {
        String sql = "SELECT r.*, rt.type_name, rt.rate_per_night FROM reservations r JOIN room_types rt ON r.room_type_id = rt.room_type_id WHERE r.status = 'CONFIRMED' AND r.check_in_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL ? DAY) ORDER BY r.check_in_date LIMIT 20";
        return getListRange(sql, days);
    }

    public List<Reservation> upcomingCheckOuts(int days) {
        String sql = "SELECT r.*, rt.type_name, rt.rate_per_night FROM reservations r JOIN room_types rt ON r.room_type_id = rt.room_type_id WHERE r.status = 'CONFIRMED' AND r.check_out_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL ? DAY) ORDER BY r.check_out_date LIMIT 20";
        return getListRange(sql, days);
    }

    private List<Reservation> getListRange(String sql, int days) {
        List<Reservation> list = new ArrayList<>();
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, days);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reservation r = new Reservation();
                r.setReservationId(rs.getInt("reservation_id"));
                r.setReservationNumber(rs.getString("reservation_number"));
                r.setGuestName(rs.getString("guest_name"));
                r.setRoomTypeName(rs.getString("type_name"));
                r.setCheckInDate(rs.getDate("check_in_date"));
                r.setCheckOutDate(rs.getDate("check_out_date"));
                list.add(r);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public BigDecimal revenueThisMonth() {
        String sql = "SELECT COALESCE(SUM(DATEDIFF(check_out_date, check_in_date) * (SELECT rate_per_night FROM room_types WHERE room_type_id = r.room_type_id)), 0) FROM reservations r WHERE r.status = 'CONFIRMED' AND YEAR(r.check_in_date) = YEAR(CURDATE()) AND MONTH(r.check_in_date) = MONTH(CURDATE())";
        try (Statement st = db.getConnection().createStatement(); ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getBigDecimal(1) : BigDecimal.ZERO;
        } catch (SQLException e) { e.printStackTrace(); return BigDecimal.ZERO; }
    }

    public boolean cancel(int reservationId, String cancelledBy, String reason) {
        String sql = "UPDATE reservations SET status = 'CANCELLED', cancelled_by = ?, cancelled_at = NOW(), cancel_reason = ? WHERE reservation_id = ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, cancelledBy);
            ps.setString(2, reason != null ? reason : "");
            ps.setInt(3, reservationId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public int countAvailableByRoomTypeAndDates(int roomTypeId, java.util.Date checkIn, java.util.Date checkOut) {
        String sql = "SELECT total_rooms FROM room_types WHERE room_type_id = ?";
        int total = 0;
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, roomTypeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) total = rs.getInt("total_rooms");
        } catch (SQLException e) { e.printStackTrace(); return 0; }
        sql = "SELECT COUNT(DISTINCT reservation_id) FROM reservations WHERE room_type_id = ? AND status = 'CONFIRMED' AND check_in_date < ? AND check_out_date > ?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setInt(1, roomTypeId);
            ps.setDate(2, new java.sql.Date(checkOut.getTime()));
            ps.setDate(3, new java.sql.Date(checkIn.getTime()));
            ResultSet rs = ps.executeQuery();
            int booked = rs.next() ? rs.getInt(1) : 0;
            return Math.max(0, total - booked);
        } catch (SQLException e) { e.printStackTrace(); return 0; }
    }
}
