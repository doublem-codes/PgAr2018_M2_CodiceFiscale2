
public class Main {

    public static void main(String[] args) {


        Reader lettore = new Reader();

        Element rootPerson = lettore.read("inputPersone.txt", "persona", true);
        Element rootComuni = lettore.read("comuni.txt", "comune", true);
        Element rootCodiciFiscali = lettore.read("codiciFiscali.txt", " ", false);
        //Element trips = lettore.read("trips.txt");

        //trips.printOnConsole();
        //rootPerson.printOnConsole();

        System.out.println("");

    }

}