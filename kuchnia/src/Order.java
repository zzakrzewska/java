import java.util.*;

public class Order {

    private static int count = 0;
    private final int number;
    private final Date date;
    private ArrayList<Meal> order;
    private double price;

    public Order(){
        this.number = count;
        count++;
        this.date = new Date();
        this.order = new ArrayList<>();
    }

    public int orderSize(){
        return order.size();
    }

    public void addMeal(Meal meal){
        order.add(order.size(), meal);
        price += meal.getPrice();
    }

    public Date getDate(){
        return date;
    }

    public double getPrice(){
        return price;
    }

    public void changePrice(double Price){
        this.price = price;
    }

    public String toString(){
        String names = "Order no. " + number + ": ";

        for(int a = 0; a < order.size(); a++) {
            names += order.get(a).getName();
            names += ", ";
        }

        return names;
    }

}
