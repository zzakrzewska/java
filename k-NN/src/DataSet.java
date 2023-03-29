import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataSet {

    ArrayList<Node> trainSet;
    ArrayList<Node> testSet;

    public DataSet(String trainSetPath) throws IOException {

        //read train-set
        BufferedReader trainSetIn = new BufferedReader(new FileReader(trainSetPath));
        String data = trainSetIn.readLine();

        this.trainSet = new ArrayList<>();

        while(data != null) {
            String[] dataTab = data.split(",");

            String className = dataTab[dataTab.length-1];
            ArrayList<String> coordinates = new ArrayList<>();

            for(int a = 0; a < dataTab.length-1; a++)
                coordinates.add(dataTab[a]);

            trainSet.add(new Node(className, coordinates));
            data = trainSetIn.readLine();
        }

        System.out.println("Wczytano dane treningowe");
        trainSetIn.close();

    }

    public int getMaxK() {
        return trainSet.size();
    }

    public int getColumnCount() {
        return trainSet.get(0).getColumnCount();
    }

    public void testArray(String testSetPath, int k, boolean longPrint) throws IOException {

        importTestSet(testSetPath);

        if(k > trainSet.size()) {
            System.out.println("Rozmiar danych treningowych za maly do podanego parametru");
            return;
        }

        if(testSet.isEmpty()) {
            System.out.println("Lista obiektow do testowania jest pusta");
            return;
        }

        int correct = 0;
        int incorrect = 0;

        int insertedCount = 0;
        boolean inserted = false;
        Node[] nearestNeighbors = new Node[k];

        for(Node testNode : testSet) {
            for(Node trainNode : trainSet) {

                double newDistance = distance(testNode.getCoordinates(), trainNode.getCoordinates());

                if(insertedCount < k) {
                    nearestNeighbors[insertedCount] = trainNode;
                    insertedCount++;

                    for(int a = 1; a < insertedCount; a++) {
                        double distance1 = distance(testNode.getCoordinates(), nearestNeighbors[a-1].getCoordinates());
                        double distance2 = distance(testNode.getCoordinates(), nearestNeighbors[a].getCoordinates());

                        if (distance1 > distance2) {
                            Node temp = nearestNeighbors[a];
                            nearestNeighbors[a] = nearestNeighbors[a-1];
                            nearestNeighbors[a-1] = temp;
                        }
                    }

                } else {
                    for(int a = 0; a < nearestNeighbors.length; a++) {
                        double oldDistance = distance(testNode.getCoordinates(), nearestNeighbors[a].getCoordinates());

                        if(oldDistance > newDistance && !inserted) {
                            nearestNeighbors[a] = trainNode;
                            inserted = true;
                        }
                    }
                }
                inserted = false;
            }

            HashMap<String, Integer> classMap = new HashMap<String, Integer>();
            for(Node n : nearestNeighbors) {
                if(classMap.containsKey(n.getClassName()))
                    classMap.put(n.getClassName(), (classMap.get(n.getClassName())+1));
                else
                    classMap.put(n.getClassName(), 1);
            }

            String classification = classMap.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getKey();

            if(longPrint)
                System.out.println("Zaklasyfikowano obiekt " + testNode + " jako " + classification);

            if(testNode.getClassName().equals(classification))
                correct++;
            else
                incorrect++;
        }

        double accuracy = (double)correct/(incorrect+correct);
        accuracy = (int)(accuracy*10000);
        accuracy = accuracy/100.0;

        System.out.println();
        System.out.println("Przetestowano " + (incorrect+correct) + " obiektow dla k = " + k);
        System.out.println("Zaklasyfikowano poprawnie: " + correct);
        System.out.println("Zaklasyfikowano niepoprawnie: " + incorrect);
        System.out.println("Procent skutecznosci: " + accuracy + "%");

    }

    public void testVector(ArrayList<String> vector, int k) {

        int insertedCount = 0;
        Node[] nearestNeighbors = new Node[k];
        boolean inserted = false;

        for(Node trainNode : trainSet) {

            double newDistance = distance(vector, trainNode.getCoordinates());

            if(insertedCount < k) {
                nearestNeighbors[insertedCount] = trainNode;
                insertedCount++;

                for(int a = 1; a < insertedCount; a++) {
                    double distance1 = distance(vector, nearestNeighbors[a-1].getCoordinates());
                    double distance2 = distance(vector, nearestNeighbors[a].getCoordinates());

                    if (distance1 > distance2) {
                        Node temp = nearestNeighbors[a];
                        nearestNeighbors[a] = nearestNeighbors[a-1];
                        nearestNeighbors[a-1] = temp;
                    }
                }

            } else {
                for(int a = 0; a < nearestNeighbors.length; a++) {
                    double oldDistance = distance(vector, nearestNeighbors[a].getCoordinates());

                    if(oldDistance > newDistance && !inserted) {
                        nearestNeighbors[a] = trainNode;
                        inserted = true;
                    }
                }
            }
            inserted = false;
        }

        HashMap<String, Integer> classMap = new HashMap<String, Integer>();
        for(Node n : nearestNeighbors) {
            if (classMap.containsKey(n.getClassName()))
                classMap.put(n.getClassName(), (classMap.get(n.getClassName()) + 1));
            else
                classMap.put(n.getClassName(), 1);
        }

        String classification = classMap.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
        System.out.println("Zaklasyfikowano wektor jako " + classification);
    }

    private void importTestSet(String testSetPath) throws IOException {

        //read test-set
        BufferedReader testSetIn = new BufferedReader(new FileReader(testSetPath));
        String dataTest = testSetIn.readLine();

        this.testSet = new ArrayList<>();

        while(dataTest != null) {
            String[] dataTab = dataTest.split(",");

            String className = dataTab[dataTab.length-1];
            ArrayList<String> coordinates = new ArrayList<>();

            for(int a = 0; a < dataTab.length-1; a++)
                coordinates.add(dataTab[a]);

            testSet.add(new Node(className, coordinates));
            dataTest = testSetIn.readLine();
        }

        testSetIn.close();
    }

    //distance between vectors x and y
    private double distance(ArrayList<String> x, ArrayList<String> y) {
        double dist = 0;

        for(int a = 0; a < x.size(); a++) {
            dist += Math.pow((Double.parseDouble(x.get(a)) - Double.parseDouble(y.get(a))), 2);
        }

        return Math.pow(dist, 0.5);
    }

}