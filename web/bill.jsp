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
    <title>Bill - Ocean View Resort</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        @media print { .no-print { display: none !important; } }
    </style>
</head>
<body class="bg-light">
    <nav class="navbar navbar-dark bg-primary no-print">
        <div class="container-fluid">
            <a class="navbar-brand" href="<%= user.getRole().equals("ADMIN") ? ctx+"/admin/dashboard" : ctx+"/guest/dashboard" %>">Ocean View Resort</a>
            <a class="btn btn-outline-light btn-sm" href="<%= ctx %>/logout">Logout</a>
        </div>
    </nav>
    <div class="container py-4">
        <h2 class="no-print">Print Bill / Receipt</h2>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
            <a href="<%= user.getRole().equals("ADMIN") ? ctx+"/admin/dashboard" : ctx+"/guest/dashboard" %>" class="btn btn-secondary no-print">Back</a>
        <% } else if (r != null) { %>
            <div class="card" id="bill">
                <div class="card-header bg-primary text-white">
                    <h4 class="mb-0">Ocean View Resort - Bill / Receipt</h4>
                </div>
                <div class="card-body">
                    <p><strong>Reservation #:</strong> <%= r.getReservationNumber() %></p>
                    <p><strong>Guest Name:</strong> <%= r.getGuestName() %></p>
                    <p><strong>Room Type:</strong> <%= r.getRoomTypeName() %></p>
                    <p><strong>Check-in:</strong> <%= r.getCheckInDate() %></p>
                    <p><strong>Check-out:</strong> <%= r.getCheckOutDate() %></p>
                    <p><strong>Number of Nights:</strong> <%= r.getNights() %></p>
                    <p><strong>Rate per Night:</strong> LKR <%= String.format("%,.2f", r.getRatePerNight()) %></p>
                    <hr>
                    <p class="h5"><strong>Total Amount Payable: LKR <%= String.format("%,.2f", r.getTotalCost()) %></strong></p>
                    <p class="text-muted small mt-3">Thank you for choosing Ocean View Resort, Galle.</p>
                </div>
            </div>
            <div class="no-print mt-3">
                <button onclick="window.print()" class="btn btn-success">Print Bill</button>
                <a href="<%= ctx %>/reservation/view?number=<%= r.getReservationNumber() %>" class="btn btn-secondary">View Reservation</a>
                <a href="<%= user.getRole().equals("ADMIN") ? ctx+"/admin/dashboard" : ctx+"/guest/dashboard" %>" class="btn btn-outline-secondary">Dashboard</a>
            </div>
        <% } else { %>
            <div class="card">
                <div class="card-body">
                    <p>Enter reservation number to view/print bill:</p>
                    <form action="<%= ctx %>/bill" method="get" class="row g-2">
                        <div class="col-auto">
                            <input type="text" name="number" class="form-control" placeholder="Reservation number" required>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-primary">Get Bill</button>
                        </div>
                    </form>
                </div>
            </div>
            <a href="<%= user.getRole().equals("ADMIN") ? ctx+"/admin/dashboard" : ctx+"/guest/dashboard" %>" class="btn btn-secondary mt-3">Back to Dashboard</a>
        <% } %>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
