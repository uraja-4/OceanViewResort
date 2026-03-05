package oceanview.factory;

import oceanview.model.User;

/**
 * Factory Method: Creates User instances by role (Admin / Guest).
 */
public class UserFactory {

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_GUEST = "GUEST";

    public static User createUser(int userId, String username, String role, String fullName, String address, String contactNumber) {
        User user = new User();
        user.setUserId(userId);
        user.setUsername(username);
        user.setRole(role != null ? role : ROLE_GUEST);
        user.setFullName(fullName);
        user.setAddress(address);
        user.setContactNumber(contactNumber);
        return user;
    }

    public static boolean isAdmin(User user) {
        return user != null && ROLE_ADMIN.equalsIgnoreCase(user.getRole());
    }

    public static boolean isGuest(User user) {
        return user != null && ROLE_GUEST.equalsIgnoreCase(user.getRole());
    }
}
