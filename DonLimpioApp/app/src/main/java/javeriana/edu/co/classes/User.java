package javeriana.edu.co.classes;

import java.util.List;

public class User {

    long userId;
    String firstName;
    String lastName;
    String username;
    String password;
    String userEmail;
    long userPhoneNumber;
    double userScore;
    UserRole userRole;
    List<Service> services;
    List<CreditCard> creditCards;

    public User(String firstName, String lastName, String username, String password, String userEmail, long userPhoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
    }

    public long getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public long getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public double getUserScore() {
        return userScore;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public List<Service> getServices() {
        return services;
    }

    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserPhoneNumber(long userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public void setUserScore(double userScore) {
        this.userScore = userScore;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
