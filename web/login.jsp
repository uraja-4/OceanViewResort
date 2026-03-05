<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login - Ocean View Resort</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background: linear-gradient(135deg, #1e3a5f 0%, #0d2137 100%); min-height: 100vh; display: flex; align-items: center; justify-content: center; }
        .card { border-radius: 1rem; box-shadow: 0 10px 40px rgba(0,0,0,0.3); }
        .card-header { background: #0d6efd; color: white; border-radius: 1rem 1rem 0 0; padding: 1rem; font-weight: bold; }
        .btn-login { background: #0d6efd; border: none; }
    </style>
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-5">
                <div class="card">
                    <div class="card-header text-center">Ocean View Resort - Login</div>
                    <div class="card-body p-4">
                        <% if (request.getAttribute("error") != null) { %>
                            <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
                        <% } %>
                        <% if (request.getAttribute("success") != null) { %>
                            <div class="alert alert-success"><%= request.getAttribute("success") %></div>
                        <% } %>
                        <form action="<%= request.getContextPath() %>/login" method="post">
                            <div class="mb-3">
                                <label class="form-label">Username</label>
                                <input type="text" name="username" class="form-control" required autofocus>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Password</label>
                                <input type="password" name="password" class="form-control" required>
                            </div>
                            <button type="submit" class="btn btn-primary btn-login w-100">Login</button>
                        </form>
                        <hr>
                        <p class="text-center mb-0"><a href="<%= request.getContextPath() %>/register">Register as Guest</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
