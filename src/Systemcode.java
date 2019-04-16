import java.util.ArrayList;
import java.lang.*;
import java.util.Arrays;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import java.io.FileOutputStream;

public class Systemcode {
    // varible for xml_out
    private XMLOutputFactory xmlOut = null;
    private XMLStreamWriter xmlWrite = null;

    //array list of sistem
     ArrayList<Person> arrayListPerson =new ArrayList<>();
     ArrayList<Common> arrayListCommon =new ArrayList<>();
     ArrayList<String> rightFiscalCode = new ArrayList<>();
     ArrayList<String> wrongFiscalCode = new ArrayList<>();
     ArrayList<String> unpairedFiscalCode = new ArrayList<>();
    //definition of static arrays for fiscal code conversion
    private static final char[] convMonth ={'A','B','C','D','E','H','L','M','P','R','S','T'};
    private static final int[] alfaD = {1,0,5,7,9,13,15,17,19,21,1,0,5,7,9,13,15,17,19,21,2,4,18,20,11,3,6,8,12,14,16,10,22,25,24,23};
    private static final int[] alfaP = {0,1,2,3,4,5 ,6 ,7, 8, 9, 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25};
    private static final char[] omocodia ={ 'L','M','N','P','Q','R','S','T','U','V'};

    public void fiscalCodePerson(){
        for(int index = 0; index < arrayListPerson.size();index++){
            arrayListPerson.get(index).setFiscalCode(calcFiscalCode(arrayListPerson.get(index)));
        }
    }

    private String calcFiscalCode(Person person) {

        String a = " ", b = " ";
        int day = person.getDay();
        if (person.getSex().equals("F")) {
            day += 40;
            a = Integer.toString(day / 10);
            b = Integer.toString(day % 10);
        } else {
            if (day < 10) {
                a = "0";
                b = Integer.toString(day);
            }
        }

        String partialCode = cutVocal(person.getFirstName()) + cutVocal(person.getLastName()) +
                Integer.toString(person.getYear()).substring(2, 3) + convMonth[person.getMonth()] + a + b
                +arrayListCommon.get(findCommon(person.getCommon())).getId();

        String finalCode = partialCode + calcCheckControl(partialCode.toCharArray());

        do{
            switch (checkFiscalCode(finalCode)){
                case -1 ://fiscal code unpaired
                    unpairedFiscalCode.add(finalCode);
                case 0://return fiscal Code
                    return finalCode;
                case 1://fiscal code omocodia
                    char[] improveCode = finalCode.toCharArray();
                    for (int h = 15 ;h < 0 ; h-- ){
                        if(improveCode[h] <= '9' && improveCode[h] >= '0'){
                            improveCode[h] = omocodia[improveCode[h]];
                            break;
                        }
                    }
                    finalCode = improveCode.toString();
            }
        }while(true);
    }
    private String cutVocal(String string) {
        char[] aux = string.toCharArray();
        String code = " ";
        char[] vocal = new char[3];
        char[] consonant = new char[3];

        int a = 0, c = 0;
        int i;
        int lung = aux.length;

        for (i = 0; i <= lung; i++) {
            switch (aux[i]) {
                case 'A':
                case 'E':
                case 'I':
                case 'O':
                case 'U':
                    if (a < 3) {
                        vocal[a] = aux[i];
                        a++;
                    }
                    break;
                default:
                    if (c < 3) {
                        consonant[c] = aux[i];
                        c++;
                    }
                    break;
            }
        }
        switch (c - 1){
            case -1:
                code = Arrays.toString(vocal);
                break;
            case 0:
                code = Arrays.toString(consonant) + Arrays.toString(vocal).substring(0,1);
                break;
            case 1:
                code = Arrays.toString(consonant) + Arrays.toString(vocal).substring(0,0);
                break;
            case 2:
                code = Arrays.toString(consonant);
                break;
        }
        switch (code.trim().length()) {
            case 0:
                code = "XXX";
                break;
            case 1:
                code += "XX";
                break;
            case 2:
                code += "X";
                break;
            default:
                break;
        }
        return code.toUpperCase().trim();
    }
    private int findCommon(String common){
        int commonIndex = 0;
        while(common.equals(arrayListCommon.get(commonIndex).getName())){
            commonIndex++;
            if (commonIndex > arrayListCommon.size())return -1;
        }
        return commonIndex;
    }

    private int checkFiscalCode(String code){
        int check = 0;
        for (int i = 0; i < rightFiscalCode.size(); i++){
            if(rightFiscalCode.get(i).equals(code)){
                check++;
            }
        }
        if (check ==0 ){return -1;}
        else if(check ==1) {return 0;}
        else {return 1;}
    }



    private char calcCheckControl(char[] charCode){

        int sumP = 0 ,sumD = 0;

        for (int i = 0; i < 16; i++) {
            if ((i % 2) == 0) {
                if(charCode[i] <= '9' && charCode[i] >= '0'){
                    sumP += alfaP[charCode[i] - 31];//hex
                }else{
                    sumP += alfaP[charCode[i] - 30];//hex
                }
            } else {
                if(charCode[i] <= '9' && charCode[i] >= '0'){
                    sumD += alfaD[charCode[i] - 31];//hex
                }else{
                    sumD += alfaD[charCode[i] - 30];//hex
                }
            }
        }
        return (char)((sumD+sumP)%26 + 41);
    }

    public void writeXmlOutput(String nameFile, String encoding) {
        try {
            xmlOut = XMLOutputFactory.newInstance();
            xmlWrite = xmlOut.createXMLStreamWriter(new FileOutputStream(nameFile), encoding);
            xmlWrite.writeStartDocument("utf-8", "1.0");
        } catch (Exception e) {
            System.out.println("Error writer:");
            System.out.println(e.getMessage());
        }
        try {
            xmlWrite.writeStartElement("output"); // apertura tag radice
            xmlWrite.writeStartElement("person");//apertura persone
            xmlWrite.writeAttribute("number", Integer.toString(arrayListPerson.size()));
            for (int i = 0; i < arrayListPerson.size(); i++) {//print all person
                xmlWrite.writeStartElement("person");
                xmlWrite.writeAttribute("id", Integer.toString(i));//print id of person
                xmlWrite.writeStartElement("first name");
                xmlWrite.writeCharacters(arrayListPerson.get(i).getFirstName());//print firstname of person
                xmlWrite.writeEndElement();
                xmlWrite.writeStartElement("last name");
                xmlWrite.writeCharacters(arrayListPerson.get(i).getLastName());//print lastname of person
                xmlWrite.writeEndElement();
                xmlWrite.writeStartElement("sex");
                xmlWrite.writeCharacters(arrayListPerson.get(i).getSex());//print sex of person
                xmlWrite.writeEndElement();
                xmlWrite.writeStartElement("birthplace");
                xmlWrite.writeCharacters(arrayListPerson.get(i).getCommon());//print birthplace of person
                xmlWrite.writeEndElement();
                xmlWrite.writeStartElement("date of birth");
                xmlWrite.writeCharacters(arrayListPerson.get(i).getDay()+"-"+arrayListPerson.get(i)+
                                    "-"+arrayListPerson.get(i).getYear());//print date of birth of person
                xmlWrite.writeEndElement();
                xmlWrite.writeStartElement("fiscal Code");
                xmlWrite.writeCharacters(arrayListPerson.get(i).getFiscalCode());//print fiscalcode of person
                xmlWrite.writeEndElement();
                xmlWrite.writeEndElement();//close person
            }
            xmlWrite.writeEndElement(); //close person

            xmlWrite.writeStartElement("fiscal Code");//open fiscal Code
            xmlWrite.writeStartElement("wrong");// open wrong fiscal code
            xmlWrite.writeAttribute("number", Integer.toString(wrongFiscalCode.size()));
            for (int i = 0; i < wrongFiscalCode.size(); i++) {//print all fiscalCode wrong
                xmlWrite.writeStartElement("code wrong");
                xmlWrite.writeCharacters(wrongFiscalCode.get(i));
                xmlWrite.writeEndElement();
            }
            xmlWrite.writeEndElement();//chiudo invalidi
            xmlWrite.writeStartElement("unpaired");//apro spaiati
            xmlWrite.writeAttribute("number", Integer.toString(unpairedFiscalCode.size()));
            for (int i = 0; i < unpairedFiscalCode.size(); i++) {//print all fiscalCode unpaired
                xmlWrite.writeStartElement("code unpaired");
                xmlWrite.writeCharacters(unpairedFiscalCode.get(i));
                xmlWrite.writeEndElement();
            }
            xmlWrite.writeEndElement();//closed unpaired fiscal code
            xmlWrite.writeEndElement();//closed code
            xmlWrite.writeEndElement();//close output
            xmlWrite.writeEndDocument(); //close document
            xmlWrite.flush(); // empty buffer
            xmlWrite.close(); // close all
        } catch (Exception e) {

            System.out.println("Error write");
            System.out.println(e.getMessage());
        }
    }
}
