package oceanview.servlet;

import oceanview.dao.ReservationDAO;
import oceanview.dao.RoomTypeDAO;
import oceanview.factory.UserFactory;
import oceanview.model.RoomType;
import oceanview.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@WebServlet(name = "AvailabilityServlet", urlPatterns = {"/availability"})
public class AvailabilityServlet extends HttpServlet {
    private final RoomTypeDAO roomTypeDAO = new RoomTypeDAO();
    private final ReservationDAO reservationDAO = new ReservationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.setAttribute("roomTypes", roomTypeDAO.getAll());
        String checkIn = request.getParameter("checkIn");
        String checkOut = request.getParameter("checkOut");
        if (checkIn != null && !checkIn.isEmpty() && checkOut != null && !checkOut.isEmpty()) {
            Date ci = parseDate(checkIn);
            Date co = parseDate(checkOut);
            if (ci != null && co != null && !co.before(ci) && !co.equals(ci)) {
                List<RoomAvailability> list = new ArrayList<>();
                for (RoomType rt : roomTypeDAO.getAll()) {
                    int avail = reservationDAO.countAvailableByRoomTypeAndDates(rt.getRoomTypeId(), ci, co);
                    list.add(new RoomAvailability(rt.getTypeName(), avail, rt.getTotalRooms(), avail > 0));
                }
                request.setAttribute("availabilityList", list);
                request.setAttribute("checkIn", checkIn);
                request.setAttribute("checkOut", checkOut);
            }
        }
        request.getRequestDispatcher("/availability.jsp").forward(request, response);
    }

    private Date parseDate(String s) {
        if (s == null || s.trim().isEmpty()) return null;
        try {
            String[] p = s.trim().split("-");
            if (p.length != 3) return null;
            Calendar cal = Calendar.getInstance();
            cal.set(Integer.parseInt(p[0]), Integer.parseInt(p[1]) - 1, Integer.parseInt(p[2]), 0, 0, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    public static class RoomAvailability {
        public final String typeName;
        public final int available;
        public final int total;
        public final boolean hasAvailability;

        public RoomAvailability(String typeName, int available, int total, boolean hasAvailability) {
            this.typeName = typeName;
            this.available = available;
            this.total = total;
            this.hasAvailability = hasAvailability;
        }
    }
}
