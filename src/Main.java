import java.lang.System;
public class Main {

    public static void main(String[] args) {
        Systemcode systemcode= new Systemcode();
        Reader lettore = new Reader();

        Element rootCommon = lettore.read("comuni.txt", "comune", true);
        Element rootCodeFiscal = lettore.read("codiciFiscali.txt", "codice", false);
        Element rootPerson = lettore.read("inputPersone.txt", "persona", true);

        systemcode.setArrayListCommon(rootCommon.transferCommon());
        systemcode.setArrayFiscalCode(rootCodeFiscal.transferCode());
        systemcode.setArrayListPerson(rootPerson.transferPerson());

        systemcode.fiscalCodePerson();
        systemcode.writeXmlOutput("codePerson.xml","utf-8");
        System.out.println();

    }
}