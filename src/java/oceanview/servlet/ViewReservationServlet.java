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

@WebServlet(name = "ViewReservationServlet", urlPatterns = {"/reservation/view"})
public class ViewReservationServlet extends HttpServlet {
    private final ReservationDAO reservationDAO = new ReservationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        String number = request.getParameter("number");
        if (number == null || number.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + (UserFactory.isAdmin(user) ? "/admin/dashboard" : "/guest/dashboard"));
            return;
        }
        Reservation r = reservationDAO.getByReservationNumber(number.trim());
        if (r == null) {
            request.setAttribute("error", "Reservation not found.");
            request.getRequestDispatcher("/reservation/view.jsp").forward(request, response);
            return;
        }
        if (UserFactory.isGuest(user) && r.getGuestId() != user.getUserId()) {
            request.setAttribute("error", "Access denied.");
            request.getRequestDispatcher("/reservation/view.jsp").forward(request, response);
            return;
        }
        request.setAttribute("reservation", r);
        request.getRequestDispatcher("/reservation/view.jsp").forward(request, response);
    }
}
