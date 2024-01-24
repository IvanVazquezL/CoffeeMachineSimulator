import java.util.Scanner;

public class CoffeeMachine {
    public static Scanner scanner = new Scanner(System.in);
    public static VirtualCoffeeMachine virtualCoffeeMachine;

    public static void main(String[] args) {
        virtualCoffeeMachine = new VirtualCoffeeMachine(
                400,
                540,
                120,
                9,
                550
        );

        System.out.println("Write action (buy, fill, take, remaining, exit):");
        String action = scanner.next();

        while (!action.equals("exit")) {
            switch (action) {
                case "buy":
                    executeBuyProcess();
                    break;
                case "fill":
                    executeFillProcess();
                    break;
                case "take":
                    executeTakeProcess();
                    break;
                case "remaining":
                    System.out.println(virtualCoffeeMachine.toString());
                    break;
                default:
                    System.out.println("Invalid action");
                    break;
            }

            System.out.println("Write action (buy, fill, take, remaining, exit):");
            action = scanner.next();
        }
    }

    public static void executeBuyProcess() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        String option = scanner.next();

        if (option.equals("back")) return;

        Coffee selectedCoffee = virtualCoffeeMachine.getCoffeeByOption(option);

        if (selectedCoffee == null) {
            System.out.println("We don't have that type of coffee");
            return;
        }

        virtualCoffeeMachine.prepareCoffee(selectedCoffee);
    }

    public static void executeFillProcess() {
        System.out.println("Write how many ml of water you want to add:");
        int waterToAdd = scanner.nextInt();

        System.out.println("Write how many ml of milk you want to add:");
        int milkToAdd = scanner.nextInt();

        System.out.println("Write how many grams of coffee beans you want to add:");
        int coffeeBeansToAdd = scanner.nextInt();

        System.out.println("Write how many disposable cups you want to add:");
        int disposableCupsToAdd = scanner.nextInt();

        virtualCoffeeMachine.fillCoffeeMachine(waterToAdd, milkToAdd, coffeeBeansToAdd, disposableCupsToAdd);
    }

    public static void executeTakeProcess() {
        int moneyToTake = virtualCoffeeMachine.retrieveCoffeeMachineMoney();
        System.out.printf("I gave you $%d\n", moneyToTake);
    }
}

class VirtualCoffeeMachine {
    private int waterAmount;
    private int milkAmount;
    private int coffeeBeansAmount;
    private int disposableCups;
    private int money;
    private Coffee espresso;
    private Coffee latte;
    private Coffee cappuccino;

    VirtualCoffeeMachine(int waterAmount, int milkAmount, int coffeeBeansAmount, int disposableCups, int money) {
        this.waterAmount = waterAmount;
        this.milkAmount = milkAmount;
        this.coffeeBeansAmount = coffeeBeansAmount;
        this.disposableCups = disposableCups;
        this.money = money;
        this.espresso = new Coffee(250, 0, 16, 4);
        this.latte = new Coffee(350, 75, 20, 7);
        this.cappuccino = new Coffee(200, 100, 12, 6);
    }

    public void prepareCoffee(Coffee coffee) {
        if (areThereEnoughResources(coffee)) {
            this.waterAmount -= coffee.getWaterQuantityPerCup();
            this.milkAmount -= coffee.getMilkQuantityPerCup();
            this.coffeeBeansAmount -= coffee.getCoffeeBeansQuantityPerCup();
            this.disposableCups -= 1;
            this.money += coffee.getPrice();
        }
    }

    private boolean areThereEnoughResources(Coffee coffee) {
        if (disposableCups == 0) {
            System.out.println("Sorry, not enough disposable cups!");
            return false;
        }

        int cupsPossibleByWater = waterAmount / coffee.getWaterQuantityPerCup();
        int cupsPossibleByMilk = coffee.getMilkQuantityPerCup() == 0 ?
                0:
                milkAmount / coffee.getMilkQuantityPerCup();
        int cupsPossibleByCoffeeBeans = coffeeBeansAmount / coffee.getCoffeeBeansQuantityPerCup();

        int cupsPossible = coffee.getMilkQuantityPerCup() == 0 ?
                Math.min(cupsPossibleByWater, cupsPossibleByCoffeeBeans) :
                Math.min(Math.min(cupsPossibleByWater, cupsPossibleByCoffeeBeans), cupsPossibleByMilk) ;

        if (cupsPossible > 0) {
            System.out.println("I have enough resources, making you a coffee!");
            return true;
        }

        if (cupsPossibleByWater < 1) {
            System.out.println("Sorry, not enough disposable water!");
        } else if (coffee.getMilkQuantityPerCup() > 0 && cupsPossibleByMilk < 1) {
            System.out.println("Sorry, not enough disposable milk!");
        } else if (cupsPossibleByCoffeeBeans < 1) {
            System.out.println("Sorry, not enough disposable coffee beans!");
        }

        return false;
    }

    public Coffee getCoffeeByOption(String option) {
        switch(option) {
            case "1":
                return this.getEspresso();
            case "2":
                return this.getLatte();
            case "3":
                return this.getCappuccino();
            default:
                return null;
        }
    }

    public void fillCoffeeMachine(int waterToAdd, int milkToAdd, int coffeeBeansToAdd, int disposableCupsToAdd) {
        this.waterAmount += waterToAdd;
        this.milkAmount += milkToAdd;
        this.coffeeBeansAmount += coffeeBeansToAdd;
        this.disposableCups += disposableCupsToAdd;
    }

    public int retrieveCoffeeMachineMoney() {
        int moneyToTake = this.money;
        this.money = 0;
        return moneyToTake;
    }

    @Override
    public String toString() {
        return String.format(
                "The coffee machine has:\n%d ml of water\n%d ml of milk\n%d g of coffee beans\n%d disposable cups\n$%d of money",
                waterAmount, milkAmount, coffeeBeansAmount, disposableCups, money
        );
    }

    public Coffee getEspresso() {
        return espresso;
    }

    public Coffee getLatte() {
        return latte;
    }

    public Coffee getCappuccino() {
        return cappuccino;
    }
}

class Coffee {
    private int waterQuantityPerCup;
    private int milkQuantityPerCup;
    private int coffeeBeansQuantityPerCup;
    private int price;

    Coffee(int waterQuantityPerCup, int milkQuantityPerCup, int coffeeBeansQuantityPerCup, int price) {
        this.waterQuantityPerCup = waterQuantityPerCup;
        this.milkQuantityPerCup = milkQuantityPerCup;
        this.coffeeBeansQuantityPerCup = coffeeBeansQuantityPerCup;
        this.price = price;
    }

    public int getCoffeeBeansQuantityPerCup() {
        return coffeeBeansQuantityPerCup;
    }

    public int getMilkQuantityPerCup() {
        return milkQuantityPerCup;
    }

    public int getWaterQuantityPerCup() {
        return waterQuantityPerCup;
    }

    public int getPrice() {
        return price;
    }
}
