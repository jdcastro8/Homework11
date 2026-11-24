public class SnackDispenseHandler {
    private final Snack snack;
    private SnackDispenseHandler nextHandler;

    public SnackDispenseHandler(Snack snack) {
        this.snack = snack;
    }

    public SnackDispenseHandler setNextHandler(
            SnackDispenseHandler nextHandler) {
        this.nextHandler = nextHandler;
        return nextHandler;
    }

    public boolean dispenseSnack(String snackName) {
        if (snack.getName().equalsIgnoreCase(snackName)) {
            if (snack.dispense()) {
                System.out.println("Dispensing " + snack.getName());
                System.out.println("Remaining quantity: "
                        + snack.getQuantity());
                return true;
            }

            System.out.println(snack.getName() + " is sold out.");
            return false;
        }

        if (nextHandler != null) {
            return nextHandler.dispenseSnack(snackName);
        }

        System.out.println("Snack not found: " + snackName);
        return false;
    }
}