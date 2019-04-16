import java.lang.System;
public class Main {

    public static void main(String[] args) {

        Reader lettore = new Reader();
        Systemcode systemcode= new Systemcode();
        Element rootCommon = lettore.read("comuni.txt", "comune", true);
        Element rootCodeFiscal = lettore.read("codiciFiscali.txt", "codice", false);
        Element rootPerson = lettore.read("inputPersone.txt", "persona", true);


        System.out.println();

    }
}