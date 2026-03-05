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
import java.math.BigDecimal;

@WebServlet(name = "AdminDashboardServlet", urlPatterns = {"/admin/dashboard"})
public class AdminDashboardServlet extends HttpServlet {
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
        int today = reservationDAO.countToday();
        int week = reservationDAO.countThisWeek();
        int month = reservationDAO.countThisMonth();
        request.setAttribute("countToday", today);
        request.setAttribute("countWeek", week);
        request.setAttribute("countMonth", month);
        request.setAttribute("upcomingCheckIns", reservationDAO.upcomingCheckIns(7));
        request.setAttribute("upcomingCheckOuts", reservationDAO.upcomingCheckOuts(7));
        BigDecimal revenue = reservationDAO.revenueThisMonth();
        request.setAttribute("revenueMonth", revenue);
        request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
    }
}
