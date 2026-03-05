package oceanview.servlet;

import oceanview.dao.ReservationDAO;
import oceanview.dao.RoomTypeDAO;
import oceanview.factory.UserFactory;
import oceanview.model.Reservation;
import oceanview.model.User;
import oceanview.observer.ReservationSubject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@WebServlet(name = "AddReservationServlet", urlPatterns = {"/reservation/add"})
public class AddReservationServlet extends HttpServlet {
    private final ReservationDAO reservationDAO = new ReservationDAO();
    private final RoomTypeDAO roomTypeDAO = new RoomTypeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.setAttribute("roomTypes", roomTypeDAO.getAll());
        request.getRequestDispatcher("/reservation/add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        String guestName = request.getParameter("guestName");
        String guestAddress = request.getParameter("guestAddress");
        String guestContact = request.getParameter("guestContact");
        String roomTypeIdStr = request.getParameter("roomTypeId");
        String checkInStr = request.getParameter("checkInDate");
        String checkOutStr = request.getParameter("checkOutDate");

        String error = validate(guestName, guestAddress, guestContact, roomTypeIdStr, checkInStr, checkOutStr);
        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("roomTypes", roomTypeDAO.getAll());
            request.getRequestDispatcher("/reservation/add.jsp").forward(request, response);
            return;
        }

        int roomTypeId = Integer.parseInt(roomTypeIdStr);
        Date checkIn = parseDate(checkInStr);
        Date checkOut = parseDate(checkOutStr);
        if (checkOut.before(checkIn) || checkOut.equals(checkIn)) {
            request.setAttribute("error", "Check-out date must be after check-in date.");
            request.setAttribute("roomTypes", roomTypeDAO.getAll());
            request.getRequestDispatcher("/reservation/add.jsp").forward(request, response);
            return;
        }

        if (!guestContact.matches("\\d+")) {
            request.setAttribute("error", "Contact number must be numeric.");
            request.setAttribute("roomTypes", roomTypeDAO.getAll());
            request.getRequestDispatcher("/reservation/add.jsp").forward(request, response);
            return;
        }

        int available = reservationDAO.countAvailableByRoomTypeAndDates(roomTypeId, checkIn, checkOut);
        if (available <= 0) {
            request.setAttribute("error", "No rooms available for selected dates and room type.");
            request.setAttribute("roomTypes", roomTypeDAO.getAll());
            request.getRequestDispatcher("/reservation/add.jsp").forward(request, response);
            return;
        }

        String resNumber = reservationDAO.generateReservationNumber();
        Reservation r = new Reservation();
        r.setReservationNumber(resNumber);
        r.setGuestId(user.getUserId());
        r.setGuestName(guestName.trim());
        r.setGuestAddress(guestAddress != null ? guestAddress.trim() : "");
        r.setGuestContact(guestContact.trim());
        r.setRoomTypeId(roomTypeId);
        r.setCheckInDate(checkIn);
        r.setCheckOutDate(checkOut);
        r.setStatus("CONFIRMED");

        if (reservationDAO.add(r)) {
            Reservation full = reservationDAO.getByReservationNumber(resNumber);
            if (full != null) {
                ReservationSubject.getInstance().notifyReservationCreated(full);
            }
            response.sendRedirect(request.getContextPath() + "/reservation/view?number=" + resNumber);
            return;
        }
        request.setAttribute("error", "Failed to create reservation.");
        request.setAttribute("roomTypes", roomTypeDAO.getAll());
        request.getRequestDispatcher("/reservation/add.jsp").forward(request, response);
    }

    private String validate(String guestName, String guestAddress, String guestContact, String roomTypeIdStr, String checkInStr, String checkOutStr) {
        if (guestName == null || guestName.trim().isEmpty()) return "Guest name is required.";
        if (guestContact == null || guestContact.trim().isEmpty()) return "Contact number is required.";
        if (roomTypeIdStr == null || roomTypeIdStr.trim().isEmpty()) return "Room type is required.";
        if (checkInStr == null || checkInStr.trim().isEmpty()) return "Check-in date is required.";
        if (checkOutStr == null || checkOutStr.trim().isEmpty()) return "Check-out date is required.";
        if (parseDate(checkInStr) == null) return "Invalid check-in date.";
        if (parseDate(checkOutStr) == null) return "Invalid check-out date.";
        return null;
    }

    private Date parseDate(String s) {
        if (s == null || s.trim().isEmpty()) return null;
        try {
            String[] p = s.trim().split("-");
            if (p.length != 3) return null;
            int y = Integer.parseInt(p[0]);
            int m = Integer.parseInt(p[1]) - 1;
            int d = Integer.parseInt(p[2]);
            Calendar cal = Calendar.getInstance();
            cal.set(y, m, d, 0, 0, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime();
        } catch (Exception e) {
            return null;
        }
    }
}
