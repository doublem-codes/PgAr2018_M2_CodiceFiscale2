
public class Main {

    public static void main(String[] args) {


        Reader lettore = new Reader();

        Element rootPerson = lettore.read("inputPersone.txt", "persona", true);
        Element rootComuni = lettore.read("comuni.txt", "comune", true);
        Element rootCodiciFiscali = lettore.read("codiciFiscali.txt", " ", false);


        System.out.println("");

    }

}