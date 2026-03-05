<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Register - Ocean View Resort</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background: linear-gradient(135deg, #1e3a5f 0%, #0d2137 100%); min-height: 100vh; padding: 2rem 0; }
        .card { border-radius: 1rem; box-shadow: 0 10px 40px rgba(0,0,0,0.3); }
        .card-header { background: #0d6efd; color: white; border-radius: 1rem 1rem 0 0; padding: 1rem; font-weight: bold; }
    </style>
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header text-center">Guest Registration</div>
                    <div class="card-body p-4">
                        <% if (request.getAttribute("error") != null) { %>
                            <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
                        <% } %>
                        <form action="<%= request.getContextPath() %>/register" method="post">
                            <div class="mb-3">
                                <label class="form-label">Username *</label>
                                <input type="text" name="username" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Password *</label>
                                <input type="password" name="password" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Full Name</label>
                                <input type="text" name="fullName" class="form-control">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Address</label>
                                <input type="text" name="address" class="form-control">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Contact Number</label>
                                <input type="text" name="contactNumber" class="form-control">
                            </div>
                            <button type="submit" class="btn btn-primary w-100">Register</button>
                        </form>
                        <hr>
                        <p class="text-center mb-0"><a href="<%= request.getContextPath() %>/login">Back to Login</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
