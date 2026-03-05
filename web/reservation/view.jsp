<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="oceanview.model.User" %>
<%@ page import="oceanview.model.Reservation" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) { response.sendRedirect(request.getContextPath() + "/login"); return; }
    Reservation r = (Reservation) request.getAttribute("reservation");
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Reservation Details - Ocean View Resort</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <nav class="navbar navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="<%= user.getRole().equals("ADMIN") ? ctx+"/admin/dashboard" : ctx+"/guest/dashboard" %>">Ocean View Resort</a>
            <a class="btn btn-outline-light btn-sm" href="<%= ctx %>/logout">Logout</a>
        </div>
    </nav>
    <div class="container py-4">
        <h2>Reservation Details</h2>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
            <a href="<%= user.getRole().equals("ADMIN") ? ctx+"/admin/dashboard" : ctx+"/guest/dashboard" %>" class="btn btn-secondary">Back</a>
        <% } else if (r != null) { %>
            <div class="card">
                <div class="card-body">
                    <p><strong>Reservation #:</strong> <%= r.getReservationNumber() %></p>
                    <p><strong>Status:</strong> <span class="badge bg-<%= "CONFIRMED".equals(r.getStatus()) ? "success" : "CANCELLED".equals(r.getStatus()) ? "danger" : "warning" %>"><%= r.getStatus() %></span></p>
                    <p><strong>Guest:</strong> <%= r.getGuestName() %></p>
                    <p><strong>Address:</strong> <%= r.getGuestAddress() != null ? r.getGuestAddress() : "-" %></p>
                    <p><strong>Contact:</strong> <%= r.getGuestContact() %></p>
                    <p><strong>Room Type:</strong> <%= r.getRoomTypeName() %></p>
                    <p><strong>Check-in:</strong> <%= r.getCheckInDate() %></p>
                    <p><strong>Check-out:</strong> <%= r.getCheckOutDate() %></p>
                    <p><strong>Nights:</strong> <%= r.getNights() %></p>
                    <p><strong>Rate/night:</strong> LKR <%= String.format("%,.2f", r.getRatePerNight()) %></p>
                    <p><strong>Total:</strong> LKR <%= String.format("%,.2f", r.getTotalCost()) %></p>
                    <% if ("CANCELLED".equals(r.getStatus()) && r.getCancelReason() != null && !r.getCancelReason().isEmpty()) { %>
                        <p><strong>Cancelled by:</strong> <%= r.getCancelledBy() %>, <strong>Reason:</strong> <%= r.getCancelReason() %></p>
                    <% } %>
                </div>
            </div>
            <div class="mt-3">
                <% if ("CONFIRMED".equals(r.getStatus())) { %>
                    <a href="<%= ctx %>/reservation/cancel?number=<%= r.getReservationNumber() %>" class="btn btn-danger">Cancel Reservation</a>
                <% } %>
                <a href="<%= ctx %>/bill?number=<%= r.getReservationNumber() %>" class="btn btn-success">Print Bill</a>
                <a href="<%= user.getRole().equals("ADMIN") ? ctx+"/admin/dashboard" : ctx+"/guest/dashboard" %>" class="btn btn-secondary">Back to Dashboard</a>
            </div>
        <% } %>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
