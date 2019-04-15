import java.util.ArrayList;
import java.util.Arrays;
import java.lang.System;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.lang.System;

import java.io.FileOutputStream;

public class SystemCode {

    //definition of static arrays for fiscal code conversion
    private static final char[] convMonth ={'A','B','C','D','E','H','L','M','P','R','S','T'};
    private static final int[] alfaD = {1,0,5,7,9,13,15,17,19,21,1,0,5,7,9,13,15,17,19,21,2,4,18,20,11,3,6,8,12,14,16,10,22,25,24,23};
    private static final int[] alfaP = {0,1,2,3,4,5 ,6 ,7, 8, 9, 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25};
    private static final char[] omocodia ={ 'L','M','N','P','Q','R','S','T','U','V'};
    private static final char[] config = {'C','C','C','C','C','C','N','N','C','N','N','C','N','N','N','C'};

    private ArrayList<Person> arrayListPerson =new ArrayList<>();
    private ArrayList<Common> arrayListCommon =new ArrayList<>();
    private ArrayList<String> rightFiscalCode = new ArrayList<>();
    private ArrayList<String> wrongFiscalCode = new ArrayList<>();
    private ArrayList<String> unpairedFiscalCode = new ArrayList<>();

    public int findIndexPerson(Person person){
        int index = 0;
        while(arrayListPerson.get(index).getFiscalCode().equals(person.getFiscalCode())){
            index++;
        }
        return index;
    }

    private int checkCommon(String code){
        int commonIndex = 0;
        while(code.equals(arrayListCommon.get(commonIndex).name)){
            commonIndex++;
            if (commonIndex>=arrayListCommon.size())return -1;
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
                Integer.toString(person.getYear()).substring(2, 3) + convMonth[person.getMonth()] + a + b +
                arrayListCommon.get(checkCommon(person.getCommon()));

        String finalCode = partialCode + calcCheckControl(partialCode.toCharArray());

        do{
            switch (checkFiscalCode(finalCode)){
                case -1 :
                    unpairedFiscalCode.add(finalCode);
                case 0:
                    return finalCode;
                case 1:
                    // improve omocodia
                    /*
                    char[] repaire = finalCode.toCharArray();
                    int i = repaire.length-1;
                    while (i>=0) {
                        if(repaire[i] >= '0' && repaire[i] <= '9'){
                            repaire[i] = omocodia[repaire[i]-30];
                            break;
                        }
                        i--;
                    }
                    */
                    break;
            }
        }while(true);//checkin metpd
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
        return code.trim();
    }

    private char calcCheckControl(char[] charCode){
        char[] p = new char[8];
        int pi = 0;
        char[] d = new char[7];
        int di = 0;
        int sumP = 0 ,sumD = 0;
        for (int i = 0; i < 16; i++) {
            if ((i % 2) == 0) {
                p[pi] = charCode[i];
                pi++;
            } else {
                d[di] = charCode[i];
                di++;
            }
        }
        for (int i = 0; i < 8; i++) {
            if(p[i] <= '9' && p[i] >= '0'){
                sumP += alfaP[p[i] - 31];//hex
            }else{
                sumP += alfaP[p[i] - 30];//hex

            }
            if (i != 8){
                if(d[i] <= '9' && d[i] >= '0'){
                    sumD += alfaD[d[i] - 31];//hex
                }else{
                    sumD += alfaD[d[i] - 30];//hex
                }
            }
        }
        return (char)((sumD+sumP)%26 + 41);
    }

    private char calcCheckControl(String code){
        char[] charCode =code.toCharArray();
        char[] p = new char[8];
        int pi = 0;
        char[] d = new char[7];
        int di = 0;
        int sumP = 0 ,sumD = 0;
        for (int i = 0; i < 16; i++) {
            if ((i % 2) == 0) {
                p[pi] = charCode[i];
                pi++;
            } else {
                d[di] = charCode[i];
                di++;
            }
        }
        for (int i = 0; i < 8; i++) {
            if(p[i] <= '9' && p[i] >= '0'){
                sumP += alfaP[p[i] - 31];//hex
            }else{
                sumP += alfaP[p[i] - 30];//hex

            }
            if (i != 8){
                if(d[i] <= '9' && d[i] >= '0'){
                    sumD += alfaD[d[i] - 31];//hex
                }else{
                    sumD += alfaD[d[i] - 30];//hex
                }
            }
        }
        return (char)((sumD+sumP)%26 + 41);
    }

    public boolean checkWrightFiscalCode(String fiscalCode){
        if(fiscalCode.length() == 16){
            for(int i = 0 ; i<16 ;i++){
                if(config[i] == 'C'){
                    if(fiscalCode.charAt(i) > 'Z' && fiscalCode.charAt(i) < 'A' )return false;
                }else{//config[i] == 'N'
                    if(fiscalCode.charAt(i) > '9' && fiscalCode.charAt(i) < '0' )return false;
                }
            }
            boolean bool = false;
            for (int i = 0;i<convMonth.length;i++ ){
                if (fiscalCode.charAt(8) != convMonth[i]) {
                    bool = true;
                    break;
                }
            }
            if (bool)return false;
            bool = false;
            for (int i = 0 ; i < arrayListCommon.size() ;i++){
                if( fiscalCode.substring(11,14).equals(arrayListCommon.get(i).getId())){
                    bool = true;
                    break;
                }
            }
            if (bool)return false;
            if (calcCheckControl(fiscalCode) == (fiscalCode.charAt(15)))return true;
        }
        return false;
    }

    private XMLOutputFactory xmlof = null;
    private XMLStreamWriter xmlw = null;

    public void writeOutput() {
        try {
            xmlof = XMLOutputFactory.newInstance();
            xmlw = xmlof.createXMLStreamWriter(new FileOutputStream("codiciPersone.xml"), "utf-8");
            xmlw.writeStartDocument("utf-8", "1.0");
        } catch (Exception e) {
            System.out.println("Errore nell'inizializzazione del writer:");
            System.out.println(e.getMessage());
        }

        try {
            xmlw.writeStartElement("output"); // apertura tag radice
            xmlw.writeStartElement("persone");//apertura persone
            xmlw.writeAttribute("numero", Integer.toString(arrayListPerson.size()));
            for (int i = 0; i < arrayListPerson.size(); i++) {
                xmlw.writeStartElement("person");
                xmlw.writeAttribute("id", Integer.toString(i));
                xmlw.writeStartElement("first name");
                xmlw.writeCharacters(arrayListPerson.get(i).getFirstName());
                xmlw.writeEndElement();
                xmlw.writeStartElement("clast name");
                xmlw.writeCharacters(arrayListPerson.get(i).getLastName());
                xmlw.writeEndElement();
                xmlw.writeStartElement("sex");
                xmlw.writeCharacters(arrayListPerson.get(i).getSex());
                xmlw.writeEndElement();
                xmlw.writeStartElement("birthplace");
                xmlw.writeCharacters(arrayListPerson.get(i).getCommon());
                xmlw.writeEndElement();
                xmlw.writeStartElement("date of birth");
                xmlw.writeCharacters(arrayListPerson.get(i).getDay()+"-"+arrayListPerson.get(i)+"-"+arrayListPerson.get(i).getYear());
                xmlw.writeEndElement();
                xmlw.writeStartElement("fiscal Code");
                xmlw.writeCharacters(arrayListPerson.get(i).getFiscalCode());
                xmlw.writeEndElement();
                xmlw.writeEndElement();
            }
            xmlw.writeEndElement(); //close person

            xmlw.writeStartElement("fiscal Code");//open fiscal Code
            xmlw.writeStartElement("wring");//apro invalidi
            xmlw.writeAttribute("number", Integer.toString(wrongFiscalCode.size()));
            for (int i = 0; i < wrongFiscalCode.size(); i++) {
                xmlw.writeStartElement("code wrong");
                xmlw.writeCharacters(wrongFiscalCode.get(i));
                xmlw.writeEndElement();
            }
            xmlw.writeEndElement();//chiudo invalidi

            xmlw.writeStartElement("unpaired");//apro spaiati
            xmlw.writeAttribute("number", Integer.toString(unpairedFiscalCode.size()));
            for (int i = 0; i < unpairedFiscalCode.size(); i++) {
                xmlw.writeStartElement("code unpaired");
                xmlw.writeCharacters(unpairedFiscalCode.get(i));
                xmlw.writeEndElement();
            }
            xmlw.writeEndElement();//closed unpaired fiscal code
            xmlw.writeEndElement();//closed code

            xmlw.writeEndElement();//close output
            xmlw.writeEndDocument(); //close document
            xmlw.flush(); // empty buffer
            xmlw.close(); // close all
        } catch (Exception e) {

            System.out.println("Errore nella scrittura");
            System.out.println(e.getMessage());
        }
    }


}
