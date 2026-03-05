<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="oceanview.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="oceanview.model.Reservation" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) { response.sendRedirect(request.getContextPath() + "/login"); return; }
    List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Guest Dashboard - Ocean View Resort</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="<%= ctx %>/guest/dashboard">Ocean View Resort</a>
            <span class="navbar-text text-white me-3"><%= user.getFullName() != null && !user.getFullName().isEmpty() ? user.getFullName() : user.getUsername() %></span>
            <a class="btn btn-outline-light btn-sm" href="<%= ctx %>/logout">Logout</a>
        </div>
    </nav>
    <div class="container py-4">
        <h2 class="mb-4">Guest Dashboard</h2>
        <div class="card mb-4">
            <div class="card-header">Profile Summary</div>
            <div class="card-body">
                <p class="mb-1"><strong>Username:</strong> <%= user.getUsername() %></p>
                <p class="mb-1"><strong>Name:</strong> <%= user.getFullName() != null ? user.getFullName() : "-" %></p>
                <p class="mb-1"><strong>Contact:</strong> <%= user.getContactNumber() != null ? user.getContactNumber() : "-" %></p>
                <p class="mb-0"><strong>Address:</strong> <%= user.getAddress() != null ? user.getAddress() : "-" %></p>
            </div>
        </div>
        <div class="card mb-4">
            <div class="card-header">My Reservations</div>
            <div class="card-body p-0">
                <% if (reservations != null && !reservations.isEmpty()) { %>
                    <table class="table table-hover mb-0">
                        <thead><tr><th>Reservation #</th><th>Room</th><th>Check-in</th><th>Check-out</th><th>Status</th><th></th></tr></thead>
                        <tbody>
                            <% for (Reservation r : reservations) { %>
                                <tr>
                                    <td><%= r.getReservationNumber() %></td>
                                    <td><%= r.getRoomTypeName() %></td>
                                    <td><%= r.getCheckInDate() %></td>
                                    <td><%= r.getCheckOutDate() %></td>
                                    <td><span class="badge bg-<%= "CONFIRMED".equals(r.getStatus()) ? "success" : "CANCELLED".equals(r.getStatus()) ? "danger" : "warning" %>"><%= r.getStatus() %></span></td>
                                    <td>
                                        <a href="<%= ctx %>/reservation/view?number=<%= r.getReservationNumber() %>" class="btn btn-sm btn-outline-primary">View</a>
                                        <% if ("CONFIRMED".equals(r.getStatus())) { %>
                                            <a href="<%= ctx %>/reservation/cancel?number=<%= r.getReservationNumber() %>" class="btn btn-sm btn-outline-danger">Cancel</a>
                                        <% } %>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                <% } else { %>
                    <p class="p-3 mb-0">No reservations yet.</p>
                <% } %>
            </div>
        </div>
        <div class="card">
            <div class="card-header">Quick Actions</div>
            <div class="card-body">
                <a href="<%= ctx %>/reservation/add" class="btn btn-primary me-2">New Reservation</a>
                <a href="<%= ctx %>/availability" class="btn btn-info me-2">Check Availability</a>
                <a href="<%= ctx %>/help" class="btn btn-outline-secondary">Help</a>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
