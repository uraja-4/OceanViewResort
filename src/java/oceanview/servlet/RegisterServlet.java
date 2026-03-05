package oceanview.servlet;

import oceanview.dao.UserDAO;
import oceanview.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");
        String contact = request.getParameter("contactNumber");
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Username and password are required.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        if (userDAO.usernameExists(username.trim())) {
            request.setAttribute("error", "Username already exists.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        User u = new User();
        u.setUsername(username.trim());
        u.setPassword(password.trim());
        u.setFullName(fullName != null ? fullName.trim() : "");
        u.setAddress(address != null ? address.trim() : "");
        u.setContactNumber(contact != null ? contact.trim() : "");
        if (userDAO.register(u)) {
            request.setAttribute("success", "Registration successful. Please login.");
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            request.setAttribute("error", "Registration failed.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}
