import java.util.List;

public class VendingMachineDriver {

    public static void main(String[] args) {
        Snack coke = new Snack("Coke", 1.50, 5);
        Snack pepsi = new Snack("Pepsi", 1.50, 5);
        Snack cheetos = new Snack("Cheetos", 2.00, 5);
        Snack doritos = new Snack("Doritos", 2.00, 5);
        Snack kitkat = new Snack("KitKat", 1.75, 5);
        Snack snickers = new Snack("Snickers", 1.75, 1);

        VendingMachine machine = new VendingMachine(List.of(
                coke, pepsi, cheetos,
                doritos, kitkat, snickers));

        System.out.println("===== VENDING MACHINE =====");
        System.out.println("Initial state: "
                + machine.getCurrentState().getStateName());

        machine.displaySnacks();

        System.out.println("\n===== TEST 1: COKE - EXACT PAYMENT =====");
        machine.selectSnack("Coke");
        machine.insertMoney(1.50);

        System.out.println("\n===== TEST 2: PEPSI - INSUFFICIENT PAYMENT =====");
        machine.selectSnack("Pepsi");
        machine.insertMoney(0.50);
        machine.insertMoney(1.00);

        System.out.println("\n===== TEST 3: CHEETOS - MULTIPLE PAYMENTS =====");
        machine.selectSnack("Cheetos");
        machine.insertMoney(0.50);
        machine.insertMoney(0.75);
        machine.insertMoney(0.75);

        System.out.println("\n===== TEST 4: DORITOS - RETURN CHANGE =====");
        machine.selectSnack("Doritos");
        machine.insertMoney(3.00);

        System.out.println("\n===== TEST 5: KITKAT - SUCCESSFUL PURCHASE =====");
        machine.selectSnack("KitKat");
        machine.insertMoney(1.75);

        System.out.println("\n===== TEST 6: SNICKERS - LAST ITEM =====");
        machine.selectSnack("Snickers");
        machine.insertMoney(1.75);

        System.out.println("\n===== TEST 7: SNICKERS - SOLD OUT =====");
        machine.selectSnack("Snickers");
        machine.insertMoney(2.00);

        System.out.println("\n===== TEST 8: INVALID SELECTION =====");
        machine.selectSnack("Cookies");

        System.out.println("\n===== FINAL INVENTORY =====");
        machine.displaySnacks();

        System.out.println("\nFinal state: "
                + machine.getCurrentState().getStateName());

        System.out.println("===== END OF DEMONSTRATION =====");
    }
}