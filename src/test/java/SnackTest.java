import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SnackTest {

    @Test
    void testSnackCreation() {
        Snack snack = new Snack("Coke", 1.50, 5);

        assertEquals("Coke", snack.getName(),
                "Snack name should be Coke");
        assertEquals(1.50, snack.getPrice(), 0.001,
                "Snack price should be 1.50");
        assertEquals(5, snack.getQuantity(),
                "Initial quantity should be 5");
    }

    @Test
    void testSnackAvailability() {
        Snack snack = new Snack("Pepsi", 1.50, 3);

        assertTrue(snack.isAvailable(),
                "Snack should be available");
    }

    @Test
    void testDispenseReducesQuantity() {
        Snack snack = new Snack("Cheetos", 2.00, 3);

        assertTrue(snack.dispense(),
                "Dispensing should succeed");
        assertEquals(2, snack.getQuantity(),
                "Quantity should decrease by one");
    }

    @Test
    void testSnackBecomesSoldOut() {
        Snack snack = new Snack("Snickers", 1.75, 1);

        assertTrue(snack.dispense(),
                "Last snack should dispense successfully");
        assertEquals(0, snack.getQuantity(),
                "Quantity should reach zero");
        assertFalse(snack.isAvailable(),
                "Snack should now be sold out");
    }

    @Test
    void testCannotDispenseSoldOutSnack() {
        Snack snack = new Snack("Snickers", 1.75, 0);

        assertFalse(snack.dispense(),
                "Sold out snack should not dispense");
        assertEquals(0, snack.getQuantity(),
                "Quantity should never become negative");
    }

    @Test
    void testNegativePrice() {
        assertThrows(IllegalArgumentException.class,
                () -> new Snack("Doritos", -1.00, 5),
                "Negative price should be rejected");
    }

    @Test
    void testNegativeQuantity() {
        assertThrows(IllegalArgumentException.class,
                () -> new Snack("KitKat", 1.50, -1),
                "Negative quantity should be rejected");
    }
}