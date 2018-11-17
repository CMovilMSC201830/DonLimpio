package javeriana.edu.co.classes;

import java.util.List;

public class User {

    String email;
    String firstName;
    String lastName;
    long userPhoneNumber;
    String uuid;
    DocumentTypes doc;

    public User() {
    }

    public User(String email, String firstName, String lastName, long userPhoneNumber) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(long userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public DocumentTypes getDoc() {
        return doc;
    }

    public void setDoc(DocumentTypes doc) {
        this.doc = doc;
    }
}
