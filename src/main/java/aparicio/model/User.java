package aparicio.model;


/** This class creates a user.*/
public class User {

    private int userId;
    private String userName;
    private String password;

    /**This method is used as the constructor for users.
     * @param userId the user ID
     * @param userName the username
     * @param password the password
     */
    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**
     * @param userId the user ID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @param userName the username to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @param password   the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**This method overrides the hashCode method.
     * @return the hashCode
     */
    @Override
    public int hashCode() {
        return userId;
    }

    /**
     * @return the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
}
