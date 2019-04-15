public class Main {

    public static void main(String[] args) {

        Reader lettore = new Reader();
        Systemcode systemcode= new Systemcode();
        Element rootCommon = lettore.read("comuni.txt", "comune", true);
        Element rootCodeFiscal = lettore.read("codiciFiscali.txt", " ", false);
        Element rootPerson = lettore.read("inputPersone.txt", "persona", true);

        systemcode.arrayListCommon = rootCommon.transferCommon();
        systemcode.rightFiscalCode = rootCodeFiscal.transferCode(systemcode.arrayListCommon);
        systemcode.arrayListPerson = rootPerson.transferPerson();


        systemcode.fiscalCodePerson();
        systemcode.writeXmlOutput("codePerson.xml","utf-8");

    }
}