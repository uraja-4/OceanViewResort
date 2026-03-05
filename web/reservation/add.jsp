<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="oceanview.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="oceanview.model.RoomType" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) { response.sendRedirect(request.getContextPath() + "/login"); return; }
    List<RoomType> roomTypes = (List<RoomType>) request.getAttribute("roomTypes");
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Add Reservation - Ocean View Resort</title>
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
        <h2>Add New Reservation</h2>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
        <% } %>
        <div class="card">
            <div class="card-body">
                <form action="<%= ctx %>/reservation/add" method="post">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">Guest Name *</label>
                            <input type="text" name="guestName" class="form-control" value="<%= user.getFullName() != null ? user.getFullName() : "" %>" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Contact Number *</label>
                            <input type="text" name="guestContact" class="form-control" value="<%= user.getContactNumber() != null ? user.getContactNumber() : "" %>" required pattern="[0-9]+" title="Numeric only">
                        </div>
                        <div class="col-12">
                            <label class="form-label">Address</label>
                            <input type="text" name="guestAddress" class="form-control" value="<%= user.getAddress() != null ? user.getAddress() : "" %>">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Room Type *</label>
                            <select name="roomTypeId" class="form-select" required>
                                <option value="">-- Select --</option>
                                <% if (roomTypes != null) for (RoomType rt : roomTypes) { %>
                                    <option value="<%= rt.getRoomTypeId() %>"><%= rt.getTypeName() %> - LKR <%= String.format("%,.0f", rt.getRatePerNight()) %>/night</option>
                                <% } %>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Check-in Date *</label>
                            <input type="date" name="checkInDate" class="form-control" required>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Check-out Date *</label>
                            <input type="date" name="checkOutDate" class="form-control" required>
                        </div>
                        <div class="col-12">
                            <button type="submit" class="btn btn-primary">Create Reservation</button>
                            <a href="<%= user.getRole().equals("ADMIN") ? ctx+"/admin/dashboard" : ctx+"/guest/dashboard" %>" class="btn btn-secondary">Cancel</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
