package javeriana.edu.co.classes;

import java.util.Date;

class Invoice {
    private Long id;

    private Long paymentTotal;

    private String comments;

    private PaymentMethod paymentMethod;

    private Date invoiceDate;

    public Invoice() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPaymentTotal() {
        return paymentTotal;
    }

    public void setPaymentTotal(Long paymentTotal) {
        this.paymentTotal = paymentTotal;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
}
