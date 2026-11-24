public class WaitingForMoneyState
        implements StateOfVendingMachine {

    private final VendingMachine machine;

    public WaitingForMoneyState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void selectSnack(String snackName) {
        System.out.println("Please complete the current purchase.");
    }

    @Override
    public void insertMoney(double amount) {
        machine.addMoney(amount);

        System.out.printf("Money inserted: $%.2f%n", amount);
        System.out.printf("Total inserted: $%.2f%n",
                machine.getMoneyInserted());

        if (machine.hasEnoughMoney()) {
            machine.setState(machine.getDispensingSnackState());
            machine.dispenseSnack();
        } else {
            System.out.printf("Additional money needed: $%.2f%n",
                    machine.getSelectedSnack().getPrice()
                            - machine.getMoneyInserted());
        }
    }

    @Override
    public void dispenseSnack() {
        if (machine.hasEnoughMoney()) {
            machine.setState(machine.getDispensingSnackState());
            machine.dispenseSnack();
        } else {
            System.out.println("Insufficient money.");
        }
    }

    @Override
    public String getStateName() {
        return "Waiting for Money";
    }
}