package javeriana.edu.co.classes;

import java.util.Date;

public class ServiceRequest {
    private int categoryId;
    private Date requestDate;
    private int serviceStatus;

    public ServiceRequest() {
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public int getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(int serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    @Override
    public String toString() {
        return "ServiceRequest{" +
                "categoryId=" + categoryId +
                ", requestDate=" + requestDate +
                ", serviceStatus=" + serviceStatus +
                '}';
    }
}
