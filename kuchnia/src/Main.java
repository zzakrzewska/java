import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Main
    implements Serializable {

    //order
    public static void createOrder(int type, ArrayList<Order> orders){

        Order order = new Order();

        if(type == 1) {

            Scanner table = new Scanner(System.in);
            System.out.print("Enter table number: ");
            int number = table.nextInt();
            order = new DineIn(number);

        } else if(type == 2){

            Scanner ad = new Scanner(System.in);
            System.out.print("Enter delivery address: ");
            String address = ad.nextLine();
            order = new Delivery(address);
        }

        orders.add(order);
        System.out.println("Order created");
    }

    public static void createRandomOrder(Menu menu, ArrayList<Order> orders){
        int type = (int)(Math.random()*2);
        Order order = new Order();

        if(type == 0) {
            order = new DineIn((int)(Math.random()*21));
        } else {
            order = new Delivery("");
        }

        int meals = (int)(Math.random()*6);

        for (int a = 0; a < meals; a++){
            order.addMeal(menu.getMeal((int)(Math.random()*(menu.getSize()))));
        }

        orders.add(order);
        System.out.println("Random order created");
    }

    public static void addToOrder(Meal meal, Order order){
        order.addMeal(meal);
        System.out.println(meal.getName() + " added to order");
    }

    //meal
    public static void createMeal(double price, String name, Menu menu){
        Meal meal = new Meal(price, name);
        menu.add(meal);
    }

    public static void modifyMeal(Meal meal){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Modify:");
        System.out.println("1. name, 2. price, 3. description");
        int number = scanner.nextInt();

        switch(number){
            case 1:
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Current name: " + meal.getName());
                System.out.print("Select new name: ");
                meal.changeName(scanner1.nextLine());
                System.out.println("Name changed");
                break;
            case 2:
                Scanner scanner2 = new Scanner(System.in);
                System.out.println("Current price: " + meal.getPrice());
                System.out.print("Select new price: ");
                meal.changePrice(scanner2.nextDouble());
                System.out.println("Price changed");
            case 3:
                Scanner scanner3 = new Scanner(System.in);
                System.out.println("Current description: " + meal.getDescription());
                System.out.print("Select new description: ");
                meal.changeDescription(scanner3.nextLine());
                System.out.println("Description changed");
            default:
                System.out.println("invalid number!");
                break;
        }

    }

    //menu
    public static void addMeal(Meal meal, Menu menu){
        menu.add(meal);
        System.out.println(meal.getName() + " added to menu");
    }

    public static void viewMenu(Menu menu){
        menu.show();
    }

    public static void saveMenu(Menu menu) {
        try {
            menu.save();
            System.out.println("Menu saved");
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void loadMenu(Menu menu){
        try {
            menu.load();
            System.out.println("Menu loaded");
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void addEmployee(int type, Kitchen kitchen, ArrayList<Driver> drivers, ArrayList<Waiter> waiters){

        String name;
        String surname;
        int contact;
        int time;

        Scanner nameS = new Scanner(System.in);
        System.out.print("Enter name: ");
        name = nameS.nextLine();

        Scanner surnameS = new Scanner(System.in);
        System.out.print("Enter surname: ");
        surname = surnameS.nextLine();

        Scanner contactS = new Scanner(System.in);
        System.out.print("Enter contact: ");
        contact = contactS.nextInt();

        switch(type) {
            case 1:
                Scanner timeS = new Scanner(System.in);
                System.out.print("Enter time shortened: ");
                time = timeS.nextInt();
                Cook cook = new Cook(name, surname, contact, time);
                kitchen.addCook(cook);
                System.out.println("Hired a new cook");
                break;

            case 2:
                Waiter waiter = new Waiter(kitchen, name, surname, contact);
                waiters.add(waiter);
                System.out.println("Hired a new waiter");
                break;

            case 3:
                Driver driver = new Driver(kitchen, name, surname, contact);
                drivers.add(driver);
                System.out.println("Hired a new driver");
                break;

            default:
                System.out.println("invalid number");
                break;
        }

    }

    public static void fireEmployee(int type, Kitchen kitchen, ArrayList<Driver> drivers, ArrayList<Waiter> waiters){

        switch(type){
            case 1:
                kitchen.showCooks();
                Scanner scanner = new Scanner(System.in);
                System.out.print("Type number of the cook to be fired: ");
                int fired = scanner.nextInt();
                kitchen.removeCook(fired, kitchen.getCook(fired));
                System.out.println("Cook fired");

                break;

            case 2:
                for(int a = 0; a < waiters.size(); a++)
                    System.out.println(a + ". " + waiters.get(a));

                Scanner scanner2 = new Scanner(System.in);
                System.out.print("Type number of the waiter to be fired: ");
                int fired2 = scanner2.nextInt();
                waiters.remove(fired2);
                System.out.println("Waiter fired");

                break;

            case 3:
            for(int a = 0; a < drivers.size(); a++)
                System.out.println(a + ". " + drivers.get(a));

            Scanner scanner3 = new Scanner(System.in);
            System.out.print("Type number of the driver to be fired: ");
            int fired3 = scanner3.nextInt();
            drivers.remove(fired3);
            System.out.println("Driver fired");

            break;
        }
    }

    public static void show(ArrayList<Order> orders) {
        for(int a = 0; a < orders.size(); a++) {
            System.out.println(orders.get(a));
        }
    }

    public static double showEarnings(Kitchen kitchen, ArrayList<Driver> drivers, ArrayList<Waiter> waiters) {
        double earnings = kitchen.getEarnings();

        for(int a = 0; a < drivers.size(); a++)
            earnings += drivers.get(a).getEarnings();

        for(int a = 0; a < waiters.size(); a++)
            earnings += waiters.get(a).getEarnings();

        return earnings;
    }

    public static void main(String[] args) {

        Menu menu = new Menu();
        ArrayList<Order> orders = new ArrayList<>();
        Kitchen kitchen = new Kitchen();
        ArrayList<Waiter> waiters = new ArrayList<>();
        ArrayList<Driver> drivers = new ArrayList<>();
        Waiter waiter1 = new Waiter(kitchen, "John", "Brown", 123456789);
        Driver driver1 = new Driver(kitchen, "Ann", "White", 8127428);
        Cook cook1 = new Cook("Tom", "Don", 123, 10);
        Cook cook2 = new Cook("Tim", "Cook", 1243545, 20);
        waiters.add(waiter1);
        drivers.add(driver1);
        kitchen.addCook(cook1);
        kitchen.addCook(cook2);
        boolean end = false;

        Meal chicken = new Meal(10.99, "Chicken", "chicken with sauce");
        Meal fries = new Meal(5.99, "Fries", "french fries");
        Meal carbonara = new Meal(15.99, "Carbonara");
        Meal potatoes = new Meal(3.99, "Potatoes", "potatoes with seasoning");
        Meal water = new Meal(0.99, "Water", "plain water, 250 ml");

        Delivery delivery1 = new Delivery("address 1");
        delivery1.addMeal(chicken);
        delivery1.addMeal(fries);
        orders.add(delivery1);

        Delivery delivery2 = new Delivery("address 2");
        delivery2.addMeal(carbonara);
        delivery2.addMeal(water);
        orders.add(delivery2);

        Delivery delivery3 = new Delivery("address 3");
        delivery3.addMeal(fries);
        delivery3.addMeal(potatoes);
        delivery3.addMeal(water);
        orders.add(delivery3);

        Delivery delivery4 = new Delivery("address 4");
        delivery4.addMeal(fries);
        orders.add(delivery4);

        Delivery delivery5 = new Delivery("address 5");
        delivery5.addMeal(water);
        delivery5.addMeal(water);
        orders.add(delivery5);

        DineIn dineIn1 = new DineIn(1);
        dineIn1.addMeal(water);
        dineIn1.addMeal(water);
        dineIn1.addMeal(water);
        orders.add(dineIn1);

        DineIn dineIn2 = new DineIn(2);
        dineIn2.addMeal(chicken);
        dineIn2.addMeal(carbonara);
        orders.add(dineIn2);

        DineIn dineIn3 = new DineIn(3);
        dineIn3.addMeal(potatoes);
        orders.add(dineIn3);

        DineIn dineIn4 = new DineIn(1);
        dineIn4.addMeal(chicken);
        dineIn4.addMeal(potatoes);
        dineIn4.addMeal(water);
        orders.add(dineIn4);

        DineIn dineIn5 = new DineIn(1);
        dineIn5.addMeal(water);
        orders.add(dineIn5);

        menu.add(chicken);
        menu.add(fries);
        menu.add(carbonara);
        menu.add(potatoes);
        menu.add(water);

        for(int a = 0; a < 10; a++)
            kitchen.passOrder(orders.get(a));

        while(!end){

            System.out.println("\n" + "What would you like to do?" + "\n");
            System.out.println("-----ORDERS-----");
            System.out.println("1. create an order" + "\n" + "2. create a random order" + "\n" + "3. add to order"
                                + "\n" + "4. pass order to the kitchen" + "\n" + "5. show incomplete orders" + "\n"
                                + "6. show complete orders");
            System.out.println("-----MENU-----");
            System.out.println("7. view current menu" + "\n" + "8. remove a meal from menu"
                                + "\n" + "9. save current menu" + "\n" + "10. load saved menu");
            System.out.println("-----MEALS-----");
            System.out.println("11. create a new meal" + "\n" + "12. modify an existing meal");
            System.out.println("----EMPLOYEES-----");
            System.out.println("13. hire an employee" + "\n" + "14. fire an employee" + "\n" + "15. show employee info");
            System.out.println("-----RESTAURANT-----");
            System.out.println("16. start work" + "\n" + "17. end work" + "\n" + "18. show today's earnings");
            System.out.println();

            Scanner number = new Scanner(System.in);
            System.out.print("enter 0 to end, or select action number: ");
            int next = number.nextInt();
            System.out.println();

            switch(next){
                case 0:
                    kitchen.interrupt();

                    for(int a = 0; a < drivers.size(); a++)
                    drivers.get(a).interrupt();

                    for(int a = 0; a < waiters.size(); a++)
                        waiters.get(a).interrupt();

                    end = true;
                    break;

                case 1:
                    System.out.println("---CREATING ORDER---");
                    boolean done1 = false;
                    while(!done1) {
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("type: " + "\n" + "1 - for dine in" + "\n" + "2 - for delivery");
                        int type = scanner.nextInt();
                        createOrder(type, orders);

                        Scanner scanner1 = new Scanner(System.in);
                        System.out.print("Type 1 to create another order, 0 to stop: ");
                        if(scanner1.nextInt() == 0)
                            done1 = true;
                    }
                    break;

                case 2:
                    System.out.println("---CREATING RANDOM ORDER---");
                    boolean done2 = false;
                    while(!done2) {
                        createRandomOrder(menu, orders);
                        Scanner scanner = new Scanner(System.in);
                        System.out.print("Type 1 to create another random order, 0 to stop: ");
                        if(scanner.nextInt() == 0)
                            done2 = true;
                    }
                    break;

                case 3:
                    System.out.println("---ADDING TO ORDER---");
                    boolean done3 = false;

                        Scanner scanner = new Scanner(System.in);
                        Scanner scanner2 = new Scanner(System.in);

                        show(orders);
                        System.out.print("Select order to add to: ");
                        int num = (scanner.nextInt()-1);

                        menu.show();

                    while(!done3) {
                        System.out.print("\n" + "Select menu item to add: ");
                        int num2 = (scanner2.nextInt());

                        orders.get(num).addMeal(menu.getMeal(num2));
                        System.out.println("Added " + menu.getMeal(num2).getName() + " to order number " + (num+1));

                        Scanner scanner3 = new Scanner(System.in);
                        System.out.print("Type 1 to add another meal, 0 to stop: ");
                        if(scanner3.nextInt() == 0)
                            done3 = true;
                    }
                    break;

                case 4:
                    System.out.println("---PASSING ORDER---");
                    show(orders);

                    Scanner scanner4 = new Scanner(System.in);
                    System.out.println("Select order to pass to the kitchen: ");
                    int num4 = scanner4.nextInt();
                    kitchen.passOrder(orders.get(num4));

                    System.out.println("Order passed");
                    break;

                case 5:
                    System.out.println("---SHOWING INCOMPLETE ORDERS---");
                    kitchen.showToDo();
                    break;

                case 6:
                    System.out.println("---SHOWING COMPLETE ORDERS---");
                    kitchen.showDone();
                    break;

                case 7:
                    menu.show();
                    break;

                case 8:
                    System.out.println("---REMOVING FROM MENU---");

                    Scanner scanner8 = new Scanner(System.in);
                    menu.show();

                    System.out.print("Select meal index to remove: ");
                    menu.remove(menu.getMeal(scanner8.nextInt()));

                    System.out.println("\n" + "Meal removed");
                    break;

                case 9:
                    System.out.println("---SAVING MENU---");
                    saveMenu(menu);
                    break;

                case 10:
                    System.out.println("---LOADING MENU---");
                    loadMenu(menu);
                    break;

                case 11:
                    System.out.println("---CREATING A MEAL---");

                    Scanner scanner11 = new Scanner(System.in);
                    System.out.print("Name of meal: ");
                    String name = scanner11.nextLine();

                    Scanner scanner111 = new Scanner(System.in);
                    System.out.print("Price: ");
                    double price = scanner111.nextDouble();
                    createMeal(price, name, menu);

                    System.out.println("Meal created");
                    break;

                case 12:
                    System.out.println("---MODIFYING A MEAL---");

                    Scanner scanner12 = new Scanner(System.in);
                    menu.show();

                    System.out.print("Select a meal to modify: ");
                    int modify = scanner12.nextInt();

                    modifyMeal(menu.getMeal(modify));
                    break;

                case 13:
                    System.out.println("---HIRING AN EMPLOYEE---");

                    Scanner scanner13 = new Scanner(System.in);
                    System.out.println("Who would you like to add?");
                    System.out.println("1. cook, 2. waiter, 3. driver");
                    int person = scanner13.nextInt();

                    addEmployee(person, kitchen, drivers, waiters);
                    break;

                case 14:
                    System.out.println("---FIRING AN EMPLOYEE---");

                    Scanner scanner14 = new Scanner(System.in);
                    System.out.println("Who would you like to fire?");
                    System.out.println("1. cook, 2. waiter, 3. driver");
                    int fired = scanner14.nextInt();

                    fireEmployee(fired, kitchen, drivers, waiters);
                    break;

                case 15:
                    System.out.println("---SHOW EMPLOYEE INFO---");

                    System.out.println("Cooks:");
                    kitchen.showCooks();

                    System.out.println("Waiters:");
                    for(int a = 0; a < waiters.size(); a++)
                        System.out.println(a + ". " + waiters.get(a));

                    System.out.println("Drivers:");
                    for(int a = 0; a < drivers.size(); a++)
                        System.out.println(a + ". " + drivers.get(a));

                    break;

                case 16:
                    kitchen.start();

                    for(int a = 0; a < drivers.size(); a++)
                        drivers.get(a).start();

                    for(int a = 0; a < waiters.size(); a++)
                        waiters.get(a).start();

                    break;

                case 17:
                    try {
                        kitchen.interrupt();

                        for (Driver driver : drivers) driver.interrupt();
                        for (Waiter waiter : waiters) waiter.interrupt();

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    break;

                case 18:
                    System.out.println("---TODAY'S EARNINGS: " + showEarnings(kitchen, drivers, waiters) + "---");
                    break;
                default:
                    System.out.println("invalid number");
                    break;
            }


        }

    }
}
