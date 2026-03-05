<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="oceanview.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) { response.sendRedirect(request.getContextPath() + "/login"); return; }
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Help - Ocean View Resort</title>
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
        <h2>Help / User Guide</h2>
        <div class="card">
            <div class="card-body">
                <h5>How to login</h5>
                <p>Go to the login page, enter your username and password, then click Login. You will be redirected to Admin Dashboard (staff) or Guest Dashboard (guests).</p>
                <h5>How to create a reservation</h5>
                <p>From the dashboard, click "Add New Reservation" or "New Reservation". Fill in guest name, contact (numeric), address, room type, check-in and check-out dates. Check-out must be after check-in. Click Create Reservation.</p>
                <h5>How to search a reservation</h5>
                <p>Admins: use "Search Reservation" and enter the reservation number. Guests: use "My Reservations" on the Guest Dashboard and click View on a reservation.</p>
                <h5>How billing is calculated</h5>
                <p>Total Bill = Number of Nights × Room Rate. Number of nights = (Check-out date − Check-in date). Room rate depends on room type (Single, Double, Family, Suite).</p>
                <h5>How to logout / exit safely</h5>
                <p>Click "Logout" in the top navigation. Your session will be cleared. For a web application, closing the browser also ends the session.</p>
            </div>
        </div>
        <a href="<%= user.getRole().equals("ADMIN") ? ctx+"/admin/dashboard" : ctx+"/guest/dashboard" %>" class="btn btn-secondary mt-3">Back to Dashboard</a>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
