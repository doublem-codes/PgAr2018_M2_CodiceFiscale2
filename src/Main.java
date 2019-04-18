import java.lang.System;
public class Main {

    private static final String xmlFileName="codePerson.xml";

    public static void main(String[] args) {
        Systemcode systemcode= new Systemcode();
        Reader lettore = new Reader();

        System.out.println("\n -----------------------READING FILE----------------------------------------------\n");

        Element rootCommon = lettore.read("comuni.txt", "comune");
        Element rootCodeFiscal = lettore.read("codiciFiscali.txt", "codice");
        Element rootPerson = lettore.read("inputPersone.txt", "persona");

        systemcode.setArrayListCommon(rootCommon.transferCommon());
        systemcode.setArrayFiscalCode(rootCodeFiscal.transferCode());
        systemcode.setArrayListPerson(rootPerson.transferPerson());

        systemcode.fiscalCodePerson();
        System.out.println("\n -----------------------SAVING FILE----------------------------------------------\n");
        if (systemcode.writeXmlOutput(xmlFileName,"utf-8")){
            System.out.println("File saved !" + "... as: "+ xmlFileName);
            int totalNumberOfFiscalCode = systemcode.getRightFiscalCode().size()+systemcode.getWrongFiscalCode().size();
            System.out.print("\n-\tNumber of right fiscal code: " + systemcode.getRightFiscalCode().size() + " / " + totalNumberOfFiscalCode +
                    "\n-\tNumber of wrong fiscal code: " + systemcode.getWrongFiscalCode().size() + " / " + totalNumberOfFiscalCode +
                    "\n-\tNumber of unpaired fiscal code: " + systemcode.getUnpairedFiscalCode().size() + " / " + totalNumberOfFiscalCode+  "\n\n-\tsee xml file output for more details");
        }else{
            System.out.println("File not saved !" + "Some error has occurred");
        }

    }
}