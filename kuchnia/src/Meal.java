import java.io.Serializable;

public class Meal
    implements Serializable {

    private double price;
    private String name;
    private String description;
    private boolean available;

    public Meal(double price, String name) {
        this.price = price;
        this.name = name;
        this.available = true;
    }

    public Meal(double price, String name, String description) {
        this.price = price;
        this.name = name;
        this.description = description;
        this.available = true;
    }

    public void changePrice(double price){
        this.price = price;
    }

    public void changeName(String name){
        this.name = name;
    }

    public void changeDescription(String description){
        this.description = description;
    }

    public void changeAvailability(boolean available){
        this.available = available;
    }

    public String getName(){
        return this.name;
    }

    public double getPrice(){
        return this.price;
    }

    public String getDescription(){
        if(description != null)
            return description;
        else
            return "No description";
    }

    public String rawString(){
        int av;
        av = (available)? 1 : 0;

        return name + "/" + price + "/" + description + "/" + av;
    }


    public String toString(){
        if(description != null) {
            if (available)
                return name + " | price: " + price + "\n" + "\t" + description;
            else
                return "[UNAVAILABLE] " + name + " | price: " + price + "\n" + "\t" + description;
        } else {
            if (available)
                return name + " | price: " + price;
            else
                return "[UNAVAILABLE] " + name + " | price: " + price;
        }
    }

}
