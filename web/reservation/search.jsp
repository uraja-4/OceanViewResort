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
    <title>Search Reservation - Ocean View Resort</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <nav class="navbar navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="<%= ctx %>/admin/dashboard">Ocean View Resort</a>
            <a class="btn btn-outline-light btn-sm" href="<%= ctx %>/logout">Logout</a>
        </div>
    </nav>
    <div class="container py-4">
        <h2>Search Reservation</h2>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-warning"><%= request.getAttribute("error") %></div>
        <% } %>
        <div class="card mb-4">
            <div class="card-body">
                <form action="<%= ctx %>/reservation/search" method="post" class="row g-2">
                    <div class="col-auto">
                        <input type="text" name="number" class="form-control" placeholder="Reservation number (e.g. RES000001)" required>
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="btn btn-primary">Search</button>
                    </div>
                </form>
            </div>
        </div>
        <% if (r != null) { %>
            <div class="card">
                <div class="card-body">
                    <p><strong>Reservation #:</strong> <%= r.getReservationNumber() %></p>
                    <p><strong>Guest:</strong> <%= r.getGuestName() %></p>
                    <p><strong>Room:</strong> <%= r.getRoomTypeName() %></p>
                    <p><strong>Check-in / Check-out:</strong> <%= r.getCheckInDate() %> - <%= r.getCheckOutDate() %></p>
                    <p><strong>Status:</strong> <%= r.getStatus() %></p>
                    <a href="<%= ctx %>/reservation/view?number=<%= r.getReservationNumber() %>" class="btn btn-primary">View Full Details</a>
                </div>
            </div>
        <% } %>
        <a href="<%= ctx %>/admin/dashboard" class="btn btn-secondary mt-3">Back to Dashboard</a>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
