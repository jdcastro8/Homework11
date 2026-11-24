import java.util.ArrayList;
import java.util.List;

public class VendingMachine {
    private final StateOfVendingMachine idleState;
    private final StateOfVendingMachine waitingForMoneyState;
    private final StateOfVendingMachine dispensingSnackState;

    private StateOfVendingMachine currentState;

    private final List<Snack> snacks;
    private final SnackDispenseHandler snackDispenser;

    private Snack selectedSnack;
    private long moneyInsertedCents;
    private double lastMoneyReturned;

    public VendingMachine(List<Snack> snacks) {
        if (snacks == null || snacks.isEmpty()) {
            throw new IllegalArgumentException(
                    "Vending machine requires snacks");
        }

        this.snacks = new ArrayList<>(snacks);

        idleState = new IdleState(this);
        waitingForMoneyState = new WaitingForMoneyState(this);
        dispensingSnackState = new DispensingSnackState(this);

        currentState = idleState;

        snackDispenser = new SnackDispenseHandler(
                this.snacks.get(0));

        SnackDispenseHandler currentHandler = snackDispenser;

        for (int i = 1; i < this.snacks.size(); i++) {
            currentHandler = currentHandler.setNextHandler(
                    new SnackDispenseHandler(this.snacks.get(i)));
        }
    }

    public void selectSnack(String snackName) {
        currentState.selectSnack(snackName);
    }

    public void insertMoney(double amount) {
        if (!Double.isFinite(amount) || amount <= 0) {
            throw new IllegalArgumentException(
                    "Money inserted must be positive");
        }

        currentState.insertMoney(amount);
    }

    public void dispenseSnack() {
        currentState.dispenseSnack();
    }

    public Snack findSnack(String snackName) {
        for (Snack snack : snacks) {
            if (snack.getName().equalsIgnoreCase(snackName)) {
                return snack;
            }
        }

        return null;
    }

    public void displaySnacks() {
        System.out.println("Available snacks:");

        for (Snack snack : snacks) {
            System.out.printf("%s - $%.2f - Quantity: %d%n",
                    snack.getName(),
                    snack.getPrice(),
                    snack.getQuantity());
        }
    }

    public void setState(StateOfVendingMachine state) {
        currentState = state;
        System.out.println("Current state: " + state.getStateName());
    }

    public StateOfVendingMachine getCurrentState() {
        return currentState;
    }

    public StateOfVendingMachine getIdleState() {
        return idleState;
    }

    public StateOfVendingMachine getWaitingForMoneyState() {
        return waitingForMoneyState;
    }

    public StateOfVendingMachine getDispensingSnackState() {
        return dispensingSnackState;
    }

    public SnackDispenseHandler getSnackDispenser() {
        return snackDispenser;
    }

    public Snack getSelectedSnack() {
        return selectedSnack;
    }

    public void setSelectedSnack(Snack snack) {
        selectedSnack = snack;
        lastMoneyReturned = 0;
    }

    public void addMoney(double amount) {
        moneyInsertedCents += Math.round(amount * 100);
    }

    public double getMoneyInserted() {
        return moneyInsertedCents / 100.0;
    }

    public boolean hasEnoughMoney() {
        return selectedSnack != null
                && moneyInsertedCents >=
                Math.round(selectedSnack.getPrice() * 100);
    }

    public void returnMoney(double amount) {
        lastMoneyReturned = Math.round(amount * 100) / 100.0;

        if (lastMoneyReturned > 0) {
            System.out.printf("Money returned: $%.2f%n",
                    lastMoneyReturned);
        }
    }

    public double getLastMoneyReturned() {
        return lastMoneyReturned;
    }

    public void resetTransaction() {
        selectedSnack = null;
        moneyInsertedCents = 0;
    }
}