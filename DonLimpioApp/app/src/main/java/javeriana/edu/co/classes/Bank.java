package javeriana.edu.co.classes;

public class Bank {

    int bankId;
    String bankName;

    public Bank(int bankId, String bankName) {
        this.bankId = bankId;
        this.bankName = bankName;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
