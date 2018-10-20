package javeriana.edu.co.classes;

import java.util.Date;

class Invoice {

    long invoiceId;
    double totalCost;
    Date createdAt;
    Date paidAt;
    String transactionNumber;
    CreditCard creditCard;

    public Invoice(long invoiceId, double totalCost, Date createdAt, Date paidAt, String transactionNumber, CreditCard creditCard) {
        this.invoiceId = invoiceId;
        this.totalCost = totalCost;
        this.createdAt = createdAt;
        this.paidAt = paidAt;
        this.transactionNumber = transactionNumber;
        this.creditCard = creditCard;
    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(Date paidAt) {
        this.paidAt = paidAt;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

}
