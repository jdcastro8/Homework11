public class IdleState implements StateOfVendingMachine {
    private final VendingMachine machine;

    public IdleState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void selectSnack(String snackName) {
        Snack snack = machine.findSnack(snackName);

        if (snack == null) {
            System.out.println("Invalid snack selection.");
            return;
        }

        machine.setSelectedSnack(snack);

        System.out.println("Selected: " + snack.getName());
        System.out.printf("Price: $%.2f%n", snack.getPrice());

        machine.setState(machine.getWaitingForMoneyState());
    }

    @Override
    public void insertMoney(double amount) {
        System.out.println("Please select a snack first.");
        System.out.printf("Money returned: $%.2f%n", amount);
    }

    @Override
    public void dispenseSnack() {
        System.out.println("Please select a snack first.");
    }

    @Override
    public String getStateName() {
        return "Idle";
    }
}