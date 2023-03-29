import java.util.ArrayList;
import java.util.Date;

public class Kitchen
    extends Thread {

    private ArrayList<Order> toDo;
    private final ArrayList<Order> done;
    private final ArrayList<Cook> cooks;
    private ArrayList<Order> toDrivers;
    private ArrayList<Order> toWaiters;
    private int dineInCount;
    private int time;

    public Kitchen(){
        this.toDo = new ArrayList<>();
        this.done = new ArrayList<>();
        this.cooks = new ArrayList<>();
        this.toDrivers = new ArrayList<>();
        this.toWaiters = new ArrayList<>();
        this.dineInCount = 0;
        this.time = 0;
    }

    public void passOrder(Order order){
        if(order instanceof DineIn){
            toDo.add(dineInCount, order);
            dineInCount++;
        } else {
            toDo.add(order);
        }
    }

    public void addCook(Cook cook){
        cooks.add(cook);
        time += cook.getTime();
    }

    public Cook getCook(int index) {
        return cooks.get(index);
    }

    public void removeCook(int index, Cook cook){
        time -= cook.getTime();
        cooks.remove(index);
    }

    public void showCooks(){
        for(int a = 0; a < cooks.size(); a++)
            System.out.println(a + ". " + cooks.get(a));
    }

    public double getEarnings(){
        double earnings = 0;

        for(int a = 0; a < done.size(); a++)
            earnings += done.get(a).getPrice();

        return earnings;
    }

    public void showToDo(){
        System.out.println("-----ORDERS WAITING-----");
        for(int a = 0; a < toDo.size(); a++) {
            System.out.println(a + ". " + toDo.get(a));
        }
    }

    public void showDone(){
        System.out.println("-----ORDERS DONE-----");
        for(int a = 0; a < done.size(); a++) {
            System.out.println(a + ". " + done.get(a));
        }
    }

    public ArrayList<Order> getToWaiters(){
        return toWaiters;
    }

    public ArrayList<Order> getToDrivers(){
        return toDrivers;
    }

    public void run(){
        while(!isInterrupted()) {

            boolean cont = true;

            if(!toDo.isEmpty()) {
                int meals = toDo.get(0).orderSize();

                Date date = new Date();
                if((toDo.get(0).getDate().getTime()-date.getTime()) > 900000) {
                    int cancel = (int) (Math.random() * 2);
                    if (cancel == 1) {
                        toDo.remove(0);
                        cont = false;
                    } else {
                        toDo.get(0).changePrice(toDo.get(0).getPrice()-((toDo.get(0).getPrice())*0.2));
                    }
                }

                if(cont) {
                    for (int a = 0; a < meals; a++) {

                        try {
                            sleep(3000 - time);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                    if (toDo.get(0) instanceof DineIn)
                        dineInCount--;

                    done.add(toDo.get(0));

                    if (toDo.get(0) instanceof DineIn)
                        toWaiters.add(toDo.get(0));
                    else if (toDo.get(0) instanceof Delivery)
                        toDrivers.add(toDo.get(0));

                    toDo.remove(0);
                }

            } else {
                try {
                    sleep(10000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
