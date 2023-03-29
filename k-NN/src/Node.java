import java.util.ArrayList;

public class Node {

    private final String className;
    private final ArrayList<String> coordinates;

    public Node(String className, ArrayList<String> coordinates) {
        this.className = className;
        this.coordinates = coordinates;
    }

    public String getClassName() {
        return className;
    }

    public int getColumnCount() {
        return coordinates.size();
    }

    public ArrayList<String> getCoordinates() {
        return coordinates;
    }

    public String toString() {
        return className + " | coordinates: " + coordinates;
    }

}
