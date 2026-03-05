<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="oceanview.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="oceanview.model.RoomType" %>
<%@ page import="oceanview.servlet.AvailabilityServlet.RoomAvailability" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) { response.sendRedirect(request.getContextPath() + "/login"); return; }
    List<RoomType> roomTypes = (List<RoomType>) request.getAttribute("roomTypes");
    List<RoomAvailability> availabilityList = (List<RoomAvailability>) request.getAttribute("availabilityList");
    String checkIn = (String) request.getAttribute("checkIn");
    String checkOut = (String) request.getAttribute("checkOut");
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Room Availability - Ocean View Resort</title>
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
        <h2>Room Availability Calendar</h2>
        <div class="card mb-4">
            <div class="card-body">
                <form action="<%= ctx %>/availability" method="get" class="row g-3">
                    <div class="col-md-4">
                        <label class="form-label">Check-in Date</label>
                        <input type="date" name="checkIn" class="form-control" value="<%= checkIn != null ? checkIn : "" %>" required>
                    </div>
                    <div class="col-md-4">
                        <label class="form-label">Check-out Date</label>
                        <input type="date" name="checkOut" class="form-control" value="<%= checkOut != null ? checkOut : "" %>" required>
                    </div>
                    <div class="col-md-4 d-flex align-items-end">
                        <button type="submit" class="btn btn-primary">Check Availability</button>
                    </div>
                </form>
            </div>
        </div>
        <% if (availabilityList != null && !availabilityList.isEmpty()) { %>
            <div class="card">
                <div class="card-header">Availability by Room Type (<%= checkIn %> to <%= checkOut %>)</div>
                <div class="card-body p-0">
                    <table class="table table-hover mb-0">
                        <thead><tr><th>Room Type</th><th>Available</th><th>Total</th><th>Status</th><th>Action</th></tr></thead>
                        <tbody>
                            <% for (RoomAvailability a : availabilityList) { %>
                                <tr>
                                    <td><%= a.typeName %></td>
                                    <td><%= a.available %></td>
                                    <td><%= a.total %></td>
                                    <td>
                                        <% if (a.hasAvailability) { %>
                                            <span class="badge bg-success">Available</span>
                                        <% } else { %>
                                            <span class="badge bg-danger">Not Available</span>
                                        <% } %>
                                    </td>
                                    <td>
                                        <% if (a.hasAvailability) { %>
                                            <a href="<%= ctx %>/reservation/add" class="btn btn-sm btn-primary">Book</a>
                                        <% } %>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        <% } %>
        <a href="<%= user.getRole().equals("ADMIN") ? ctx+"/admin/dashboard" : ctx+"/guest/dashboard" %>" class="btn btn-secondary mt-3">Back to Dashboard</a>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
