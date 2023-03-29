import java.io.*;
import java.util.ArrayList;

public class Menu {

    private ArrayList<Meal> menu;

    public Menu(){
        this.menu = new ArrayList<>();
    }

    public void add(Meal meal){
        menu.add(menu.size(), meal);
    }

    public void remove(Meal meal){
        menu.remove(meal);
    }

    public Meal getMeal(int index) {
        return menu.get(index);
    }

    public int getSize(){
        return menu.size();
    }

    public void show(){
        System.out.println("-----MENU-----");
        for(int a = 0; a < menu.size(); a++) {
            System.out.println(a + ". " + menu.get(a));
        }
    }

    public void save() throws IOException, ClassNotFoundException {

        try {

            FileOutputStream fos = new FileOutputStream("menu.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(menu);
            oos.close();

        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    public void load() throws IOException, ClassNotFoundException {

        try {

            FileInputStream fis = new FileInputStream("menu.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);

            menu = (ArrayList<Meal>)ois.readObject();
            ois.close();

        } catch(IOException e) {
            e.printStackTrace();
        }

    }

}
