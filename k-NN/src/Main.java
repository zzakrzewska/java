import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    static DataSet dataSet;

    public static void main(String[] args) throws IOException {
        launchInterface();
    }

    static void launchInterface() {
        boolean next = true;
        boolean correctInput = false;

        while(next) {

            while(!correctInput){
                Scanner in = new Scanner(System.in);
                System.out.print("Wpisz sciezke do train-set: ");

                String trainPath = in.nextLine();
                try {
                    dataSet = new DataSet(trainPath);
                    correctInput = true;
                } catch (IOException e) {
                    System.out.println("Podano nieprawidlowa sciezke do pliku");
                }
            }

            correctInput = false;
            int k = 0;

            while(!correctInput){
                Scanner in = new Scanner(System.in);
                System.out.print("Wpisz k (maksymalna wartosc dla wczytanych danych treningowych - " + dataSet.getMaxK() + "): ");

                try {
                    k = in.nextInt();

                    if(k <= dataSet.getMaxK() && k > 0)
                        correctInput = true;
                    else
                        System.out.println("Podano za duze lub za male k");

                } catch (InputMismatchException e) {
                    System.out.println("Wpisano nieprawidlowa wartosc");
                }

            }

            correctInput = false;

            System.out.println("Co chcesz przetestowac?");
            System.out.println("\t 1. dane z pliku");
            System.out.println("\t 2. dane z wpisanego wektora");

            int answer = 0;

            while (!correctInput) {
                Scanner in = new Scanner(System.in);
                System.out.print("Wybor: ");

                try {
                    answer = in.nextInt();

                    if(answer == 1 || answer == 2)
                        correctInput = true;
                    else
                        System.out.println("Podano nieprawidlowa liczbe");

                } catch (InputMismatchException e) {
                    System.out.println("Wpisano nieprawidlowa wartosc");
                }
            }

            correctInput = false;

            if(answer == 1) {

                int variant = 0;

                System.out.println("W jakiej wersji chcesz zobaczyc wyniki?");
                System.out.println("\t 1. dlugiej - kazdy test bedzie widoczny");
                System.out.println("\t 2. krotkiej - widoczna bedzie tylko dokladnosc testow");

                while(!correctInput) {
                    Scanner in = new Scanner(System.in);
                    System.out.print("Wybor: ");

                    try {
                        variant = in.nextInt();

                        if(variant == 1 || variant == 2)
                            correctInput = true;
                        else
                            System.out.println("Podano nieprawidlowa liczbe");

                    } catch (InputMismatchException e) {
                        System.out.println("Wpisano nieprawidlowa wartosc");
                    }
                }

                correctInput = false;

                while(!correctInput){
                    Scanner in = new Scanner(System.in);
                    System.out.print("Wpisz sciezke do test-set: ");

                    String testPath = in.nextLine();

                    try {
                        dataSet.testArray(testPath, k, variant == 1);
                        correctInput = true;
                    } catch (IOException e) {
                        System.out.println("Podano nieprawidlowa sciezke do pliku");
                    }
                }
            } else if(answer == 2) {

                int columnCount = dataSet.getColumnCount();
                Scanner in = new Scanner(System.in);
                ArrayList<String> vector = new ArrayList<>();

                System.out.println("Dane treningowe sa " + columnCount + " - wymiarowe:");

                for(int a = 0; a <= columnCount-1; a++) {
                    System.out.print("Wpisz " + (a+1) + " liczbe: ");
                    vector.add(in.nextLine());
                }

                dataSet.testVector(vector, k);
            }

            correctInput = false;
            int cont = 0;

            System.out.println("Czy chcesz przetestowac nastepne dane?");
            System.out.println("\t 1. tak");
            System.out.println("\t 2. nie");

            while(!correctInput) {
                Scanner in = new Scanner(System.in);
                System.out.print("Wybor: ");

                try {
                    cont = in.nextInt();

                    if(cont == 1 || cont == 2)
                        correctInput = true;
                    else
                        System.out.println("Podano nieprawidlowa liczbe");

                } catch (InputMismatchException e) {
                    System.out.println("Wpisano nieprawidlowa wartosc");
                }
            }

            next = (cont == 1);
        }
    }

}