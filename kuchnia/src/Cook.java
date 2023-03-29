public class Cook {

    private String name;
    private String surname;
    private int contact;
    private int time;

    public Cook(String name, String surname, int contact, int time){
        this.name = name;
        this.surname = surname;
        this.contact = contact;
        this.time = time;
    }

    public int getTime(){
        return time;
    }

    public String toString(){
        return name + " " + surname + ", contact: " + contact + "\n" + "no. of orders completed: ";
    }

}
