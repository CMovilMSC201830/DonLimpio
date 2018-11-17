package javeriana.edu.co.classes;

class PaymentMethod {

    private Integer id;

    private String type;

    private int validThru;

    private String number;

    private String ccv;

    private String cardHolder;

    private String franchise;

    private Long persona_doc_id;

    public PaymentMethod() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValidThru() {
        return validThru;
    }

    public void setValidThru(int validThru) {
        this.validThru = validThru;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCcv() {
        return ccv;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getFranchise() {
        return franchise;
    }

    public void setFranchise(String franchise) {
        this.franchise = franchise;
    }

    public Long getPersona_doc_id() {
        return persona_doc_id;
    }

    public void setPersona_doc_id(Long persona_doc_id) {
        this.persona_doc_id = persona_doc_id;
    }
}
