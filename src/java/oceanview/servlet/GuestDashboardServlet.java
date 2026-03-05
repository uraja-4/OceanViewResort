package oceanview.servlet;

import oceanview.dao.ReservationDAO;
import oceanview.factory.UserFactory;
import oceanview.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "GuestDashboardServlet", urlPatterns = {"/guest/dashboard"})
public class GuestDashboardServlet extends HttpServlet {
    private final ReservationDAO reservationDAO = new ReservationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        if (!UserFactory.isGuest(user)) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }
        List<?> myReservations = reservationDAO.getByGuestId(user.getUserId());
        request.setAttribute("reservations", myReservations);
        request.getRequestDispatcher("/guest/dashboard.jsp").forward(request, response);
    }
}
