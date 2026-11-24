public class DispensingSnackState
        implements StateOfVendingMachine {

    private final VendingMachine machine;

    public DispensingSnackState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void selectSnack(String snackName) {
        System.out.println("Please wait while dispensing.");
    }

    @Override
    public void insertMoney(double amount) {
        System.out.println("Please wait while dispensing.");
        System.out.printf("Money returned: $%.2f%n", amount);
    }

    @Override
    public void dispenseSnack() {
        if (!machine.hasEnoughMoney()) {
            machine.setState(machine.getWaitingForMoneyState());
            return;
        }

        Snack snack = machine.getSelectedSnack();

        boolean dispensed = machine.getSnackDispenser()
                .dispenseSnack(snack.getName());

        if (dispensed) {
            double change = machine.getMoneyInserted()
                    - snack.getPrice();

            machine.returnMoney(Math.max(0, change));
        } else {
            machine.returnMoney(machine.getMoneyInserted());
        }

        machine.resetTransaction();
        machine.setState(machine.getIdleState());
    }

    @Override
    public String getStateName() {
        return "Dispensing Snack";
    }
}