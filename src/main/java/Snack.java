public class Snack {
    private final String name;
    private final double price;
    private int quantity;

    public Snack(String name, double price, int quantity) {
        if (price < 0 || quantity < 0) {
            throw new IllegalArgumentException(
                    "Price and quantity cannot be negative");
        }

        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isAvailable() {
        return quantity > 0;
    }

    public boolean dispense() {
        if (!isAvailable()) {
            return false;
        }

        quantity--;
        return true;
    }
}