package oceanview.servlet;

import oceanview.dao.ReservationDAO;
import oceanview.factory.UserFactory;
import oceanview.model.Reservation;
import oceanview.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "SearchReservationServlet", urlPatterns = {"/reservation/search"})
public class SearchReservationServlet extends HttpServlet {
    private final ReservationDAO reservationDAO = new ReservationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        if (!UserFactory.isAdmin(user)) {
            response.sendRedirect(request.getContextPath() + "/guest/dashboard");
            return;
        }
        request.getRequestDispatcher("/reservation/search.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        if (!UserFactory.isAdmin(user)) {
            response.sendRedirect(request.getContextPath() + "/guest/dashboard");
            return;
        }
        String number = request.getParameter("number");
        if (number != null && !number.trim().isEmpty()) {
            Reservation r = reservationDAO.getByReservationNumber(number.trim());
            request.setAttribute("reservation", r);
            if (r == null) request.setAttribute("error", "Reservation not found.");
        }
        request.getRequestDispatcher("/reservation/search.jsp").forward(request, response);
    }
}
