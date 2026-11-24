import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineTest {
    private VendingMachine machine;
    private Snack coke;
    private Snack pepsi;
    private Snack cheetos;
    private Snack doritos;
    private Snack kitkat;
    private Snack snickers;

    @BeforeEach
    void setUp() {
        coke = new Snack("Coke", 1.50, 5);
        pepsi = new Snack("Pepsi", 1.50, 5);
        cheetos = new Snack("Cheetos", 2.00, 5);
        doritos = new Snack("Doritos", 2.00, 5);
        kitkat = new Snack("KitKat", 1.75, 5);
        snickers = new Snack("Snickers", 1.75, 1);

        machine = new VendingMachine(List.of(
                coke, pepsi, cheetos,
                doritos, kitkat, snickers));
    }

    @Test
    void testInitialState() {
        assertInstanceOf(IdleState.class,
                machine.getCurrentState(),
                "Machine should start in Idle state");
    }

    @Test
    void testSnackSelectionChangesState() {
        machine.selectSnack("Coke");

        assertInstanceOf(WaitingForMoneyState.class,
                machine.getCurrentState(),
                "Selecting a snack should change the state");

        assertEquals(coke, machine.getSelectedSnack(),
                "Coke should be the selected snack");
    }

    @Test
    void testInsufficientMoney() {
        machine.selectSnack("Coke");
        machine.insertMoney(0.50);

        assertInstanceOf(WaitingForMoneyState.class,
                machine.getCurrentState(),
                "Machine should continue waiting for money");

        assertEquals(0.50, machine.getMoneyInserted(), 0.001,
                "Inserted money should be retained");

        assertEquals(5, coke.getQuantity(),
                "Snack should not dispense without enough money");
    }

    @Test
    void testExactPayment() {
        machine.selectSnack("Coke");
        machine.insertMoney(1.50);

        assertEquals(4, coke.getQuantity(),
                "Coke should dispense after exact payment");

        assertInstanceOf(IdleState.class,
                machine.getCurrentState(),
                "Machine should return to Idle state");

        assertEquals(0.00, machine.getLastMoneyReturned(),
                0.001, "Exact payment should require no change");
    }

    @Test
    void testMultiplePayments() {
        machine.selectSnack("Cheetos");
        machine.insertMoney(0.50);
        machine.insertMoney(0.75);
        machine.insertMoney(0.75);

        assertEquals(4, cheetos.getQuantity(),
                "Combined payments should dispense the snack");

        assertInstanceOf(IdleState.class,
                machine.getCurrentState(),
                "Machine should return to Idle state");
    }

    @Test
    void testReturnChange() {
        machine.selectSnack("Doritos");
        machine.insertMoney(3.00);

        assertEquals(4, doritos.getQuantity(),
                "Doritos should dispense successfully");

        assertEquals(1.00, machine.getLastMoneyReturned(),
                0.001, "Machine should return correct change");
    }

    @Test
    void testSnickersQuantityReachesZero() {
        machine.selectSnack("Snickers");
        machine.insertMoney(1.75);

        assertEquals(0, snickers.getQuantity(),
                "Snickers quantity should reach zero");

        assertFalse(snickers.isAvailable(),
                "Snickers should be sold out");
    }

    @Test
    void testSoldOutSnackRefund() {
        machine.selectSnack("Snickers");
        machine.insertMoney(1.75);

        machine.selectSnack("Snickers");
        machine.insertMoney(2.00);

        assertEquals(0, snickers.getQuantity(),
                "Sold out inventory should remain zero");

        assertEquals(2.00, machine.getLastMoneyReturned(),
                0.001, "Full payment should be refunded");

        assertInstanceOf(IdleState.class,
                machine.getCurrentState(),
                "Machine should return to Idle after refund");
    }

    @Test
    void testInvalidSnackSelection() {
        machine.selectSnack("Cookies");

        assertInstanceOf(IdleState.class,
                machine.getCurrentState(),
                "Invalid selection should not change the state");

        assertNull(machine.getSelectedSnack(),
                "No snack should be selected");
    }

    @Test
    void testMoneyWithoutSelection() {
        machine.insertMoney(2.00);

        assertEquals(0.00, machine.getMoneyInserted(),
                0.001, "Money should not be retained");

        assertInstanceOf(IdleState.class,
                machine.getCurrentState(),
                "Machine should remain in Idle state");
    }

    @Test
    void testInvalidPayment() {
        machine.selectSnack("Pepsi");

        assertThrows(IllegalArgumentException.class,
                () -> machine.insertMoney(-1.00),
                "Negative payment should be rejected");

        assertThrows(IllegalArgumentException.class,
                () -> machine.insertMoney(0.00),
                "Zero payment should be rejected");
    }

    @Test
    void testTransactionReset() {
        machine.selectSnack("KitKat");
        machine.insertMoney(2.00);

        assertNull(machine.getSelectedSnack(),
                "Selected snack should be cleared");

        assertEquals(0.00, machine.getMoneyInserted(),
                0.001, "Payment balance should reset");
    }

    @Test
    void testCannotChangeSelectionDuringPayment() {
        machine.selectSnack("Coke");
        machine.insertMoney(0.50);
        machine.selectSnack("Pepsi");

        assertEquals(coke, machine.getSelectedSnack(),
                "Original selection should remain active");

        assertEquals(0.50, machine.getMoneyInserted(),
                0.001, "Existing payment should be retained");
    }

    @Test
    void testManualDispenseWithoutEnoughMoney() {
        machine.selectSnack("Coke");
        machine.insertMoney(0.50);
        machine.dispenseSnack();

        assertEquals(5, coke.getQuantity(),
                "Insufficient payment should prevent dispensing");

        assertInstanceOf(WaitingForMoneyState.class,
                machine.getCurrentState(),
                "Machine should continue waiting for payment");
    }
}