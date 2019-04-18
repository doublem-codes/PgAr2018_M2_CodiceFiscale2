import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
public class Systemcode {

    // variable for xml_out
    private XMLOutputFactory xmlOut = null;
    private XMLStreamWriter xmlWrite = null;

    //array list of all system
    private ArrayList<Person> arrayListPerson =new ArrayList<>();
    private ArrayList<Person> wrongListPerson =new ArrayList<>();
    private ArrayList<Common> arrayListCommon =new ArrayList<>();
    private ArrayList<String> rightFiscalCode = new ArrayList<>();
    private ArrayList<String> wrongFiscalCode = new ArrayList<>();
    private ArrayList<String> unpairedFiscalCode = new ArrayList<>();

    //definition of static arrays for fiscal code conversion
    private static final int[] alfaD = {1,0,5,7,9,13,15,17,19,21,1,0,5,7,9,13,15,17,19,21,2,4,18,20,11,3,6,8,12,14,16,10,22,25,24,23};//conversion
    private static final int[] alfaP = {0,1,2,3,4,5 ,6 ,7, 8, 9, 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25};//conversion
    private static char[] config = {'C','C','C','C','C','C','N','N','C','N','N','C','N','N','N','C'}; // used to check the right composition of code
    private static final char[] convMonth ={'A','B','C','D','E','H','L','M','P','R','S','T'};// used to convertion month
    private static final char[] omocodia ={ 'L','M','N','P','Q','R','S','T','U','V'};//used for canhe fiscal code in casse of omocodia

    public void setArrayListPerson(ArrayList<Person> arrayListPerson) {
        this.arrayListPerson = arrayListPerson;
    }

    public void setArrayListCommon(ArrayList<Common> arrayListCommon) {
        this.arrayListCommon = arrayListCommon;
    }

    public void setArrayFiscalCode(ArrayList<String> rightFiscalCode) {
        this.rightFiscalCode = rightFiscalCode;
    }

    public void fiscalCodePerson(){

        //checking fiscalcode compositi
        for (int i=0 ; i < rightFiscalCode.size();i++){
            if(!checkWrightFiscalCode(rightFiscalCode.get(i))){
                wrongFiscalCode.add(rightFiscalCode.get(i));
                rightFiscalCode.remove(i);
            }
        }
        //checking arraylist person
        for(int index = 0; index < arrayListPerson.size();index++){
            if(arrayListPerson.get(index).getIsWrong()){
                wrongListPerson.add(arrayListPerson.get(index));
                arrayListPerson.remove(index);
            }
        }
        //checking right common of person
        for (int index = 0;index < arrayListPerson.size(); index ++){
            boolean check = false;
            for (Common common : arrayListCommon){
                if (arrayListPerson.get(index).getCommon().equals(common.getName())){
                    check = true ;
                    break;
                }
            }
            if (!check){
                wrongListPerson.add(arrayListPerson.get(index));
                arrayListPerson.remove(index);
            }else{
                // if all right calculate fiscalcode
                String checking = calcFiscalCode(arrayListPerson.get(index));
                if (checking.equals("ERROR")){
                    wrongListPerson.add(arrayListPerson.get(index));
                    arrayListPerson.remove(index);
                }else{
                    arrayListPerson.get(index).setFiscalCode(checking);
                }
            }
        }
    }

    //used for generate the fiscal code
    private String calcFiscalCode(Person person) {

        String a , b ;
        int day = person.getDay();
        if (person.getSex().equals("F")) {
            day += 40;
            a = Integer.toString(day / 10);
            b = Integer.toString(day % 10);
        } else {
            if (day < 10) {
                a = "0";
                b = Integer.toString(day);
            }else{
                a = Integer.toString(day / 10);
                b = Integer.toString(day % 10);
            }
        }

        String y = a + b;

        String partialCode = cutVocal(person.getLastName())+cutVocal(person.getFirstName())+Integer.toString(person.getYear()).substring(2, 3)+
                             Character.toString(convMonth[person.getMonth() - 1])+y;
        int index = findCommon(person.getCommon());
        if (index !=-1){
            partialCode += arrayListCommon.get(index).getId();
        }else{
            return "ERROR ";
        }
        String prova = calcCheckControl(partialCode.toCharArray());
        String finalCode = partialCode + prova;

        int cycle = 0;
        do{
            if(cycle == 7){//number max of int in fiscalcode
                wrongFiscalCode.add(finalCode);
                return "ERROR";
            }
            switch (checkFiscalCode(finalCode)){
                case -1 ://fiscal code unpaired
                    unpairedFiscalCode.add(finalCode);
                case 0://return fiscal Code
                    return finalCode;
                case 1://fiscal code omocodia
                    char[] improveCode = finalCode.toCharArray();
                    int h;
                    for (h = 15 ;h < 0 ; h-- ){
                        if(improveCode[h] <= '9' && improveCode[h] >= '0'){
                            improveCode[h] = omocodia[improveCode[h]];
                            break;
                        }
                    }
                    finalCode = new String(improveCode);
                    cycle++;
            }
        }while(true);
    }

    //used for cut the vocal
    private String cutVocal(String string) {
        char[] aux = string.toCharArray();
        char[] code = new char[3];
        char[] vocal = new char[3];
        char[] consonant = new char[3];

        int a = 0, c = 0;
        int i;
        int lung = aux.length;

        for (i = 0; i < lung; i++) {
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
                code[0] = vocal[0];
                code[1] = vocal[1];
                code[2] = vocal[2];
                break;
            case 0:
                code[0] = consonant[0];
                code[1] = vocal[0];
                code[2] = vocal[1];
                break;
            case 1:
                code[0] = consonant[0];
                code[1] = consonant[1];
                code[2] = vocal[0];
                break;
            case 2:
                code = consonant;
                break;
        }
        for(int m = 0; m<3;m++)
        if (code[m] >'Z'||code[m] <'A'){
            code[m] = 'z';
        }
        return new String(code);
    }

    //used for find id common
    private int findCommon(String common){
        int commonIndex = 0;
        while(!common.equals(arrayListCommon.get(commonIndex).getName())){
            commonIndex++;
            if (commonIndex > arrayListCommon.size()) return -1;
        }
        return commonIndex;
    }

    //used for finf fiscal code in arraylist fiscal code
    private int checkFiscalCode(String code){
        int check = 0;
        for (String fiscalCode:rightFiscalCode){
            if(fiscalCode.equals(code)){
                check++;
            }
        }
        if (check == 0 ){return -1;}
        else if(check == 1) {return 0;}
        else {return 1;}
    }

    private static int j;
    //used for generate the last char of fiscalcode
    private String calcCheckControl(char[] charCode) {
        j++;
        if(j==68){
            System.out.println();
        }
        int sumP = 0, sumD = 0;

        for (int i = 0; i < 14; i++) {

            if ((i % 2) == 0) {
                if (charCode[i] <= 57 && charCode[i] >= 48) {
                    sumP += alfaP[charCode[i] - 48];//hex
                } else {
                    sumP += alfaP[charCode[i] - 55];//hex
                }
            } else {
                if (charCode[i] <= 57 && charCode[i] >= 48) {
                    sumD += alfaD[charCode[i] - 48];//hex
                } else {
                    sumD += alfaD[charCode[i] - 55];//hex
                }
            }
        }
        return Character.toString((sumD + sumP) % 26 + 65);
    }
    //metod use for check the composition of fiscal code in arraylist
    private boolean checkWrightFiscalCode(String pass){

        String fiscalCode = pass.toUpperCase().trim();

        if(fiscalCode.length() != 16) return false;

        for(int i = 0 ; i<16 ;i++) {
            if (config[i] == 'C') {
                if (fiscalCode.charAt(i) > 'Z' && fiscalCode.charAt(i) < 'A') return false;
            } else {//config[i] == 'N'
                if (fiscalCode.charAt(i) > '9' && fiscalCode.charAt(i) < '0') return false;
            }
        }

        boolean bool = false;
        for(char month : convMonth){
            if (fiscalCode.charAt(8) == month) {
                bool = true;
                break;
            }
        }
        if (!bool)return false;

        bool = false;
        for (Common common : arrayListCommon){
            if(fiscalCode.substring(11,14).equals(common.getId())){
                bool = true;
                break;
            }
        }
        if (!bool) return false;

        if (fiscalCode.charAt(15) >= 'A' && 'Z' >= fiscalCode.charAt(15)) return false;

        return true;
    }

    //metod to generate xml file out
    public void writeXmlOutput(String nameFile, String encoding) {
        /*
        try {
            xmlOut = XMLOutputFactory.newInstance();
            xmlWrite = xmlOut.createXMLStreamWriter(new FileOutputStream(nameFile), encoding);
            xmlWrite.writeStartDocument("utf-8", "1.0");
        } catch (Exception e) {
            System.out.println("Error writer:");
            System.out.println(e.getMessage());
        }

         */
        try {
            xmlWrite.writeStartElement("output"); // open tag xml
            xmlWrite.writeStartElement("person right");//open right person
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
            xmlWrite.writeEndElement(); //close right person

            xmlWrite.writeStartElement("person wrong");//open wrong person
            xmlWrite.writeAttribute("number", Integer.toString(wrongListPerson.size()));
            for (int i = 0; i < wrongListPerson.size(); i++) {//print all person
                xmlWrite.writeStartElement("person");
                xmlWrite.writeAttribute("id", Integer.toString(i));//print id of person
                xmlWrite.writeStartElement("first name");
                xmlWrite.writeCharacters(wrongListPerson.get(i).getFirstName());//print firstname of person
                xmlWrite.writeEndElement();
                xmlWrite.writeStartElement("last name");
                xmlWrite.writeCharacters(wrongListPerson.get(i).getLastName());//print lastname of person
                xmlWrite.writeEndElement();
                xmlWrite.writeStartElement("sex");
                xmlWrite.writeCharacters(wrongListPerson.get(i).getSex());//print sex of person
                xmlWrite.writeEndElement();
                xmlWrite.writeStartElement("birthplace");
                xmlWrite.writeCharacters(wrongListPerson.get(i).getCommon());//print birthplace of person
                xmlWrite.writeEndElement();
                xmlWrite.writeStartElement("date of birth");
                xmlWrite.writeCharacters(wrongListPerson.get(i).getDay()+"-"+wrongListPerson.get(i)+
                        "-"+wrongListPerson.get(i).getYear());//print date of birth of person
                xmlWrite.writeEndElement();
                xmlWrite.writeStartElement("fiscal Code");
                xmlWrite.writeCharacters(wrongListPerson.get(i).getFiscalCode());//print fiscalcode of person
                xmlWrite.writeEndElement();
                xmlWrite.writeEndElement();//close person
            }
            xmlWrite.writeEndElement(); //close person

            xmlWrite.writeStartElement("fiscal Code");//open fiscal Code
            xmlWrite.writeStartElement("wrong");// open wrong fiscal code
            xmlWrite.writeAttribute("number", Integer.toString(wrongFiscalCode.size()));
            for (String print : wrongFiscalCode) {//print all fiscalCode wrong
                xmlWrite.writeStartElement("code wrong");
                xmlWrite.writeCharacters(print);
                xmlWrite.writeEndElement();
            }
            xmlWrite.writeEndElement();//close invalid
            xmlWrite.writeStartElement("unpaired");//open unpaired
            xmlWrite.writeAttribute("number", Integer.toString(unpairedFiscalCode.size()));
            for (String print : unpairedFiscalCode) {//print all fiscalCode unpaired
                xmlWrite.writeStartElement("code unpaired");
                xmlWrite.writeCharacters(print);
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


