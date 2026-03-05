package oceanview.servlet;

import oceanview.dao.UserDAO;
import oceanview.factory.UserFactory;
import oceanview.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User u = (User) session.getAttribute("user");
            if (UserFactory.isAdmin(u)) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/guest/dashboard");
            }
            return;
        }
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Invalid login. Please try again.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        User user = userDAO.login(username.trim(), password.trim());
        if (user == null) {
            request.setAttribute("error", "Invalid login. Please try again.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
        if (UserFactory.isAdmin(user)) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        } else {
            response.sendRedirect(request.getContextPath() + "/guest/dashboard");
        }
    }
}
