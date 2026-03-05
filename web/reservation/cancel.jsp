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
    <title>Cancel Reservation - Ocean View Resort</title>
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
        <h2>Cancel Reservation</h2>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
            <a href="<%= user.getRole().equals("ADMIN") ? ctx+"/admin/dashboard" : ctx+"/guest/dashboard" %>" class="btn btn-secondary">Back</a>
        <% } else if (r != null) { %>
            <div class="card mb-3">
                <div class="card-body">
                    <p><strong>Reservation #:</strong> <%= r.getReservationNumber() %></p>
                    <p><strong>Guest:</strong> <%= r.getGuestName() %></p>
                    <p><strong>Room:</strong> <%= r.getRoomTypeName() %>, Check-in: <%= r.getCheckInDate() %></p>
                </div>
            </div>
            <form action="<%= ctx %>/reservation/cancel" method="post">
                <input type="hidden" name="number" value="<%= r.getReservationNumber() %>">
                <div class="mb-3">
                    <label class="form-label">Cancellation Reason <%= user.getRole().equals("ADMIN") ? "(recommended)" : "" %></label>
                    <input type="text" name="reason" class="form-control" placeholder="Optional">
                </div>
                <button type="submit" class="btn btn-danger">Confirm Cancel</button>
                <a href="<%= ctx %>/reservation/view?number=<%= r.getReservationNumber() %>" class="btn btn-secondary">Back</a>
            </form>
        <% } else { %>
            <div class="card">
                <div class="card-body">
                    <p>Enter reservation number to cancel:</p>
                    <form action="<%= ctx %>/reservation/cancel" method="get" class="row g-2">
                        <div class="col-auto">
                            <input type="text" name="number" class="form-control" placeholder="Reservation number">
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-primary">Load</button>
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
