package domain.entity;

public class Item {

    private final int id;

    private final String name;

    private int quantity;

    private final String category;

    public Item(
            int id,
            String name,
            int quantity,
            String category
    ) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }
}