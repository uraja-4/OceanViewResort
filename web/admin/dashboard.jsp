<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="oceanview.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="oceanview.model.Reservation" %>
<%@ page import="java.math.BigDecimal" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) { response.sendRedirect(request.getContextPath() + "/login"); return; }
    Integer countToday = (Integer) request.getAttribute("countToday");
    Integer countWeek = (Integer) request.getAttribute("countWeek");
    Integer countMonth = (Integer) request.getAttribute("countMonth");
    List<Reservation> checkIns = (List<Reservation>) request.getAttribute("upcomingCheckIns");
    List<Reservation> checkOuts = (List<Reservation>) request.getAttribute("upcomingCheckOuts");
    BigDecimal revenueMonth = (BigDecimal) request.getAttribute("revenueMonth");
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Admin Dashboard - Ocean View Resort</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
      <style>
       
      
       
    </style>
</head>
<body class="bg-light">
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary" background="black">
        <div class="container-fluid">
            <a class="navbar-brand" href="<%= ctx %>/admin/dashboard">Ocean View Resort</a>
            <span class="navbar-text text-white me-3">Admin: <%= user.getUsername() %></span>
            <a class="btn btn-outline-light btn-sm" href="<%= ctx %>/logout">Logout</a>
        </div>
    </nav>
    <div class="container py-4">
        <h2 class="mb-4">Admin Dashboard</h2>
        <div class="row g-3 mb-4">
            <div class="col-md-4">
                <div class="card border-primary">
                    <div class="card-body">
                        <h6 class="text-muted">Reservations Today</h6>
                        <h3><%= countToday != null ? countToday : 0 %></h3>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card border-primary">
                    <div class="card-body">
                        <h6 class="text-muted">This Week</h6>
                        <h3><%= countWeek != null ? countWeek : 0 %></h3>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card border-primary">
                    <div class="card-body">
                        <h6 class="text-muted">This Month</h6>
                        <h3><%= countMonth != null ? countMonth : 0 %></h3>
                    </div>
                </div>
            </div>
        </div>
        <div class="row g-3 mb-4">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <h6 class="text-muted">Estimated Revenue (This Month)</h6>
                        <h4>LKR <%= revenueMonth != null ? String.format("%,.2f", revenueMonth) : "0.00" %></h4>
                    </div>
                </div>
            </div>
        </div>
        <div class="row g-3 mb-4">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">Upcoming Check-ins (7 days)</div>
                    <div class="card-body p-0">
                        <ul class="list-group list-group-flush">
                            <% if (checkIns != null && !checkIns.isEmpty()) {
                                for (Reservation r : checkIns) { %>
                                    <li class="list-group-item d-flex justify-content-between">
                                        <span><%= r.getGuestName() %> - <%= r.getRoomTypeName() %></span>
                                        <span><%= r.getCheckInDate() %></span>
                                    </li>
                            <% } } else { %>
                                <li class="list-group-item">No upcoming check-ins</li>
                            <% } %>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">Upcoming Check-outs (7 days)</div>
                    <div class="card-body p-0">
                        <ul class="list-group list-group-flush">
                            <% if (checkOuts != null && !checkOuts.isEmpty()) {
                                for (Reservation r : checkOuts) { %>
                                    <li class="list-group-item d-flex justify-content-between">
                                        <span><%= r.getGuestName() %> - <%= r.getRoomTypeName() %></span>
                                        <span><%= r.getCheckOutDate() %></span>
                                    </li>
                            <% } } else { %>
                                <li class="list-group-item">No upcoming check-outs</li>
                            <% } %>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="card">
            <div class="card-header">Quick Actions</div>
            <div class="card-body">
                <a href="<%= ctx %>/reservation/add" class="btn btn-primary me-2">Add New Reservation</a>
                <a href="<%= ctx %>/reservation/search" class="btn btn-secondary me-2">Search Reservation</a>
                <a href="<%= ctx %>/availability" class="btn btn-info me-2">Room Availability</a>
                <a href="<%= ctx %>/bill" class="btn btn-success me-2">Print Bill</a>
                <a href="<%= ctx %>/help" class="btn btn-outline-secondary">Help</a>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
