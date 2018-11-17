package javeriana.edu.co.classes;

import java.util.Date;

public class Service {

    private long id;
    private String professional;
    private Category category;
    private Invoice invoice;
    private User persona;
    private PersonaAddresses personaAddress;
    private Date reservationDate;
    private int status;

    public Service() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public User getPersona() {
        return persona;
    }

    public void setPersona(User persona) {
        this.persona = persona;
    }

    public PersonaAddresses getPersonaAddress() {
        return personaAddress;
    }

    public void setPersonaAddress(PersonaAddresses personaAddress) {
        this.personaAddress = personaAddress;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
