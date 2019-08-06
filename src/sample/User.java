
package sample;

class User {
    String username;
    String password;
    String firstName;
    String lastName;

    User() {

    }

    /**
     * A User of the HRPortal.
     * It can be an Applicant or one of two employees of a specified Company,
     * either an Coordinator or an Interviewer.
     *
     * @param username a String of a User's username for logging in the HRPortal
     * @param password a String of a User's password for logging in to HRPortal
     */
    User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns this User's username.
     *
     * @return username
     */
    protected String getUsername() {
        return this.username;
    }

    /**
     * Returns this User's password.
     *
     * @return password
     */
    protected String getPassword() {
        return this.password;
    }

    protected boolean isEmpty() {
        return this.username == null || this.password == null;
    }
}
