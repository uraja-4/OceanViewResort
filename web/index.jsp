<%@ page contentType="text/html;charset=UTF-8" %>
<%
    if (session != null && session.getAttribute("user") != null) {
        String role = ((oceanview.model.User) session.getAttribute("user")).getRole();
        if ("ADMIN".equals(role))
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        else
            response.sendRedirect(request.getContextPath() + "/guest/dashboard");
    } else {
        response.sendRedirect(request.getContextPath() + "/login");
    }
%>
