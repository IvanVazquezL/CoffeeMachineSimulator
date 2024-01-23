import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Write how many ml of water the coffee machine has:");
        int waterAmount = scanner.nextInt();

        System.out.println("Write how many ml of milk the coffee machine has:");
        int milkAmount = scanner.nextInt();

        System.out.println("Write how many grams of coffee beans the coffee machine has:");
        int coffeeBeansAmount = scanner.nextInt();

        VirtualCoffeeMachine virtualCoffeeMachine = new VirtualCoffeeMachine(waterAmount, milkAmount, coffeeBeansAmount);

        System.out.println("Write how many cups of coffee you will need:");
        int cupsOfCoffee = scanner.nextInt();
        virtualCoffeeMachine.CanTheCoffeeMachineMakeTheCupsOfCoffee(cupsOfCoffee);
    }

    public static void CalculateIngredientsByCupsOfCoffee(int cupsOfCoffee) {
        System.out.printf("%d %s of water%n", Coffee.waterQuantityPerCup * cupsOfCoffee, Coffee.waterUnit);
        System.out.printf("%d %s of milk%n", Coffee.milkQuantityPerCup * cupsOfCoffee, Coffee.milkUnit);
        System.out.printf("%d %s of coffee beans%n", Coffee.coffeeBeansQuantityPerCup * cupsOfCoffee, Coffee.coffeeBeansUnit);
    }
}

class VirtualCoffeeMachine {
    int waterAmount;
    int milkAmount;
    int coffeeBeansAmount;

    VirtualCoffeeMachine(int waterAmount, int milkAmount, int coffeeBeansAmount) {
        this.waterAmount = waterAmount;
        this.milkAmount = milkAmount;
        this.coffeeBeansAmount = coffeeBeansAmount;
    }

    public int getMaxPossibleCupsOfCoffee() {
        int waterRatio = waterAmount / Coffee.waterQuantityPerCup;
        int milkRatio = milkAmount / Coffee.milkQuantityPerCup;
        int coffeeBeansRatio = coffeeBeansAmount / Coffee.coffeeBeansQuantityPerCup;

        return Math.min(Math.min(waterRatio, milkRatio),coffeeBeansRatio);
    }

    public void CanTheCoffeeMachineMakeTheCupsOfCoffee(int cupsOfCoffee) {
        int maxPossibleCupsOfCoffee = getMaxPossibleCupsOfCoffee();

        if (cupsOfCoffee == maxPossibleCupsOfCoffee) {
            System.out.println("Yes, I can make that amount of coffee");
        } else if(maxPossibleCupsOfCoffee < cupsOfCoffee) {
            System.out.printf("No, I can make only %d cup(s) of coffee\n", maxPossibleCupsOfCoffee);
        } else {
            System.out.printf("Yes, I can make that amount of coffee (and even %d more than that)\n", maxPossibleCupsOfCoffee - cupsOfCoffee);
        }
    }
}

class Coffee {
    static final int milkQuantityPerCup = 50;
    static final String milkUnit = "ml";
    static final int waterQuantityPerCup = 200;
    static final String waterUnit = "ml";
    static final int coffeeBeansQuantityPerCup = 15;
    static final String coffeeBeansUnit = "g";

}