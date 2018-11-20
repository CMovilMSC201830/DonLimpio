package javeriana.edu.co.classes;

import java.util.Date;

public class RequestUser {

    private String uuidUser;
    private int active;
    private String uuidRequest;
    private Date createdAt;
    private int price;

    public RequestUser() {
    }

    public RequestUser(String uuidUser, int active, String uuidRequest, Date createdAt, int price) {
        this.uuidUser = uuidUser;
        this.active = active;
        this.uuidRequest = uuidRequest;
        this.createdAt = createdAt;
        this.price = price;
    }

    public String getUuidUser() {
        return uuidUser;
    }

    public void setUuidUser(String uuidUser) {
        this.uuidUser = uuidUser;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getUuidRequest() {
        return uuidRequest;
    }

    public void setUuidRequest(String uuidRequest) {
        this.uuidRequest = uuidRequest;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
