import java.util.ArrayList;
import java.util.Date;

public class Waiter
        extends Thread
        implements IPerson {

    private double earnings;
    private ArrayList<Order> toDeliver;
    private Kitchen kitchen;
    private String name;
    private String surname;
    private int contact;

    public Waiter(Kitchen kitchen, String name, String surname, int contact){
        this.earnings = 0;
        this.toDeliver = new ArrayList<>();
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

                if(time < 900000) {
                    double tip = (time / 60000) * toDeliver.get(0).getPrice();
                }

                try {
                    sleep(10000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    sleep(10000);
                    toDeliver = kitchen.getToWaiters();
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }



        }

    }


}
