package javeriana.edu.co.classes;

import java.util.List;

public class User {

    String firstName;
    String lastName;
    long userPhoneNumber;
    double userScore;
    UserRole userRole;
    List<Service> services;
    List<CreditCard> creditCards;

    public User() {
    }

    public User(String firstName, String lastName, long userPhoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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
