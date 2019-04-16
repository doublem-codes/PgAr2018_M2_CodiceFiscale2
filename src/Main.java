import java.lang.System;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Reader lettore = new Reader();
        Systemcode systemcode= new Systemcode();
        Element rootCommon = lettore.read("comuni.txt", "comune", true);
        Element rootCodeFiscal = lettore.read("codiciFiscali.txt", "codice", false);
        Element rootPerson = lettore.read("inputPersone.txt", "persona", true);


        rootCodeFiscal.transferCode();
        ArrayList<Common> commonArrayList = new ArrayList<>() ;
        commonArrayList = rootCommon.transferCommon();
        for (int i =0; i<commonArrayList.size();i++){
            if(commonArrayList.get(i).isWrong()){
                System.out.println(commonArrayList.get(i).getName());
            }
        }
        System.out.println();

    }
}