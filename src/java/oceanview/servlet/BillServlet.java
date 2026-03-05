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

@WebServlet(name = "BillServlet", urlPatterns = {"/bill"})
public class BillServlet extends HttpServlet {
    private final ReservationDAO reservationDAO = new ReservationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        String number = request.getParameter("number");
        if (number != null && !number.trim().isEmpty()) {
            Reservation r = reservationDAO.getByReservationNumber(number.trim());
            User user = (User) session.getAttribute("user");
            if (r != null) {
                if (UserFactory.isGuest(user) && r.getGuestId() != user.getUserId()) {
                    request.setAttribute("error", "Access denied.");
                } else {
                    request.setAttribute("reservation", r);
                }
            } else {
                request.setAttribute("error", "Reservation not found.");
            }
        }
        request.getRequestDispatcher("/bill.jsp").forward(request, response);
    }
}
