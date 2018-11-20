package javeriana.edu.co.classes;

public class ProviderXCategory {

    private float categoryId;
    private String description;
    private long id;
    private String name;
    private long personaId;
    private int price;
    private String uuidUser;

    public ProviderXCategory() {
    }

    public float getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(float categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(long personaId) {
        this.personaId = personaId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUuidUser() {
        return uuidUser;
    }

    public void setUuidUser(String uuidUser) {
        this.uuidUser = uuidUser;
    }
}
