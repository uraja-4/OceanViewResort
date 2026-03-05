package oceanview.servlet;

import oceanview.dao.ReservationDAO;
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
import java.util.Calendar;
import java.util.Date;

@WebServlet(name = "CancelReservationServlet", urlPatterns = {"/reservation/cancel"})
public class CancelReservationServlet extends HttpServlet {
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
            if (r != null && "CONFIRMED".equals(r.getStatus())) {
                if (UserFactory.isGuest(user) && r.getGuestId() != user.getUserId()) {
                    request.setAttribute("error", "Access denied.");
                } else if (UserFactory.isGuest(user) && isCheckInDatePassed(r.getCheckInDate())) {
                    request.setAttribute("error", "Cannot cancel after check-in date. Please contact admin.");
                } else {
                    request.setAttribute("reservation", r);
                }
            } else if (r != null) {
                request.setAttribute("error", "Reservation is already cancelled.");
            } else {
                request.setAttribute("error", "Reservation not found.");
            }
        }
        request.getRequestDispatcher("/reservation/cancel.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        String number = request.getParameter("number");
        String reason = request.getParameter("reason");
        if (number == null || number.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/reservation/cancel");
            return;
        }
        Reservation r = reservationDAO.getByReservationNumber(number.trim());
        if (r == null) {
            request.setAttribute("error", "Reservation not found.");
            request.getRequestDispatcher("/reservation/cancel.jsp").forward(request, response);
            return;
        }
        if (!"CONFIRMED".equals(r.getStatus())) {
            request.setAttribute("error", "Reservation is already cancelled.");
            request.getRequestDispatcher("/reservation/cancel.jsp").forward(request, response);
            return;
        }
        if (UserFactory.isGuest(user) && r.getGuestId() != user.getUserId()) {
            request.setAttribute("error", "Access denied.");
            request.getRequestDispatcher("/reservation/cancel.jsp").forward(request, response);
            return;
        }
        if (UserFactory.isGuest(user) && isCheckInDatePassed(r.getCheckInDate())) {
            request.setAttribute("error", "Cannot cancel after check-in date.");
            request.getRequestDispatcher("/reservation/cancel.jsp").forward(request, response);
            return;
        }
        String cancelledBy = UserFactory.isAdmin(user) ? "ADMIN" : "GUEST";
        if (reservationDAO.cancel(r.getReservationId(), cancelledBy, reason != null ? reason : "")) {
            ReservationSubject.getInstance().notifyReservationCancelled(r);
            response.sendRedirect(request.getContextPath() + "/reservation/view?number=" + number);
            return;
        }
        request.setAttribute("error", "Failed to cancel reservation.");
        request.getRequestDispatcher("/reservation/cancel.jsp").forward(request, response);
    }

    private boolean isCheckInDatePassed(Date checkInDate) {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        return checkInDate != null && checkInDate.before(today.getTime());
    }
}
