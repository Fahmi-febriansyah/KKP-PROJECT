package login;
public class Session {
    public static int id;
    public static String username;
    public static String role;

    public static void setSession(int id, String username, String role) {
        Session.id = id;
        Session.username = username;
        Session.role = role;
    }

    public static void clearSession() {
        Session.id = 0;
        Session.username = null;
        Session.role = null;
    }
}
