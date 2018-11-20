package javeriana.edu.co.classes;

public class Provider {

    private int categoryId;
    private String category;
    private String description;
    private int price;

    public Provider() {
    }

    public Provider(String category, String description, int price) {
        this.category = category;
        this.description = description;
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
