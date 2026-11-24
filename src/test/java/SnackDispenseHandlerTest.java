import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SnackDispenseHandlerTest {
    private Snack coke;
    private Snack pepsi;
    private Snack cheetos;
    private Snack doritos;
    private Snack kitkat;
    private Snack snickers;
    private SnackDispenseHandler handler;

    @BeforeEach
    void setUp() {
        coke = new Snack("Coke", 1.50, 5);
        pepsi = new Snack("Pepsi", 1.50, 5);
        cheetos = new Snack("Cheetos", 2.00, 5);
        doritos = new Snack("Doritos", 2.00, 5);
        kitkat = new Snack("KitKat", 1.75, 5);
        snickers = new Snack("Snickers", 1.75, 1);

        handler = new SnackDispenseHandler(coke);

        handler.setNextHandler(new SnackDispenseHandler(pepsi))
                .setNextHandler(new SnackDispenseHandler(cheetos))
                .setNextHandler(new SnackDispenseHandler(doritos))
                .setNextHandler(new SnackDispenseHandler(kitkat))
                .setNextHandler(new SnackDispenseHandler(snickers));
    }

    @Test
    void testFirstHandler() {
        assertTrue(handler.dispenseSnack("Coke"),
                "First handler should dispense Coke");
        assertEquals(4, coke.getQuantity(),
                "Coke quantity should decrease");
    }

    @Test
    void testMiddleHandler() {
        assertTrue(handler.dispenseSnack("Doritos"),
                "Request should reach the Doritos handler");
        assertEquals(4, doritos.getQuantity(),
                "Doritos quantity should decrease");
    }

    @Test
    void testLastHandler() {
        assertTrue(handler.dispenseSnack("Snickers"),
                "Request should reach the last handler");
        assertEquals(0, snickers.getQuantity(),
                "Snickers quantity should reach zero");
    }

    @Test
    void testSoldOutSnack() {
        assertTrue(handler.dispenseSnack("Snickers"),
                "First Snickers request should succeed");
        assertFalse(handler.dispenseSnack("Snickers"),
                "Second request should fail because it is sold out");
        assertEquals(0, snickers.getQuantity(),
                "Quantity should remain zero");
    }

    @Test
    void testUnknownSnack() {
        assertFalse(handler.dispenseSnack("Cookies"),
                "Unknown snack should not be dispensed");
    }

    @Test
    void testOtherInventoryUnchanged() {
        handler.dispenseSnack("KitKat");

        assertEquals(5, coke.getQuantity());
        assertEquals(5, pepsi.getQuantity());
        assertEquals(5, cheetos.getQuantity());
        assertEquals(5, doritos.getQuantity());
        assertEquals(4, kitkat.getQuantity());
        assertEquals(1, snickers.getQuantity());
    }

    @Test
    void testCaseInsensitiveSelection() {
        assertTrue(handler.dispenseSnack("pepsi"),
                "Selection should ignore capitalization");
        assertEquals(4, pepsi.getQuantity());
    }
}