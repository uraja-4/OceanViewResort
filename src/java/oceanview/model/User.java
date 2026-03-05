package oceanview.model;

public class User {
    private int userId;
    private String username;
    private String password;
    private String role; // ADMIN or GUEST
    private String fullName;
    private String address;
    private String contactNumber;

    public User() {}

    public User(int userId, String username, String role, String fullName, String address, String contactNumber) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.fullName = fullName;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
}
