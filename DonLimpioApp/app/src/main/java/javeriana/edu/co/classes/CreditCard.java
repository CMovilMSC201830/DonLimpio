package javeriana.edu.co.classes;

import java.util.Date;

public class CreditCard {

    String cardNumber;
    String cvv;
    String cardType; // Visa, Master Card, American Express
    Date validThru;
    Bank cardBank;

    public CreditCard(String cardNumber, String cvv, String cardType, Date validThru, Bank cardBank) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.cardType = cardType;
        this.validThru = validThru;
        this.cardBank = cardBank;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Date getValidThru() {
        return validThru;
    }

    public void setValidThru(Date validThru) {
        this.validThru = validThru;
    }

    public Bank getCardBank() {
        return cardBank;
    }

    public void setCardBank(Bank cardBank) {
        this.cardBank = cardBank;
    }
}
