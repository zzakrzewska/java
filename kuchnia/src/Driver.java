import java.util.ArrayList;
import java.util.Date;

public class Driver
        extends Thread
        implements IPerson {

    private ArrayList<Order> toDeliver;
    private double earnings;
    private Kitchen kitchen;
    private String name;
    private String surname;
    private int contact;

    public Driver(Kitchen kitchen, String name, String surname, int contact){
        this.toDeliver = new ArrayList<>();
        this.earnings = 0;
        this.kitchen = kitchen;
        this.name = name;
        this.surname = surname;
        this.contact = contact;
    }

    public void toDeliver(Order order){
        toDeliver.add(order);
    }

    public double getEarnings(){
        return earnings;
    }

    public String toString(){
        return name + " " + surname + ", earnings: " + earnings;
    }

    public void run(){

        while(!isInterrupted()) {

            if(!toDeliver.isEmpty()) {
                Order current = toDeliver.get(0);
                Date date = new Date();

                long time = current.getDate().getTime() - date.getTime();

                if(time < 900000){
                    double tip = (time/60000)*toDeliver.get(0).getPrice();
                }

                try {
                    sleep(120000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }

                earnings += toDeliver.get(0).getPrice();
                toDeliver.remove(0);

            } else {
                try {
                    sleep(10000);
                    toDeliver = kitchen.getToDrivers();
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }

    }


}
