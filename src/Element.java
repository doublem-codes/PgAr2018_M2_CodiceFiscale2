import java.util.ArrayList;
import java.lang.System;

public class Element {

	private String name;
	private String character = null;


	private ArrayList<Attribute> attributesRoot = new ArrayList<Attribute>();
	private ArrayList<Element> elementsRoot = new ArrayList<Element>();

	private ArrayList<Attribute> attributesHeader = new ArrayList();
	private ArrayList<Element> elementsHeader = new ArrayList();


	public Element(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public void getRootname() {

	}


	public ArrayList<Element> getSubElementsRoot() {
		return elementsRoot;
	}

	public ArrayList<Attribute> getAttributesRoot() {
		return attributesRoot;
	}

	public void setAttributesRoot(ArrayList<Attribute> attributesRoot) {
		this.attributesRoot = attributesRoot;
	}

	public ArrayList<Element> getElementsRoot() {
		return elementsRoot;
	}

	public void setElementsRoot(ArrayList<Element> elementsRoot) {
		this.elementsRoot = elementsRoot;
	}

	public ArrayList<Attribute> getAttributesHeader() {
		return attributesHeader;
	}

	public void setAttributesHeader(ArrayList<Attribute> attributesHeader) {
		this.attributesHeader = attributesHeader;
	}

	public ArrayList<Element> getElementsHeader() {
		return elementsHeader;
	}

	public void setElementsHeader(ArrayList<Element> elementsHeader) {
		this.elementsHeader = elementsHeader;
	}



/*
	public void setSubElements(ArrayList<Element> subElements) {
		this.subElements = subElements;
	}
	 */

	/*
	public void setAttributes(ArrayList<Attribute> attributes) {
		this.attributes = attributes;
	}
	 */

	/*
	public void printOnConsole() {

		System.out.println("\n\n----------------------XML---------------------------");
    	System.out.print("<" + name);
    	for(Attribute att : attributes ) {
			System.out.print(" " + att.getName() + "=" + "\"" + att.getValue() + "\"");
		}
		System.out.println(">");
    	for(Element _row : subElements) {
    		System.out.print("\t<" + _row.getName());
    		for(Attribute att : _row.getAttributes()) {
    			System.out.print(" " + att.getName() + "=" + "\"" + att.getValue() + "\"");
    		}
    		System.out.println(">");
    		for (Element _genericItem : _row.getSubElements()) {
    			System.out.print("\t\t<" + _genericItem.getName());
    			for(Attribute att : _genericItem.getAttributes()) {
        			System.out.print(" " + att.getName() + "=" + "\"" + att.getValue() + "\"");
        		}
        		System.out.println(">" + _genericItem.getCharacter() + "</" + _genericItem.getName() + ">");
    		}
    		System.out.println("\t</" + _row.getName() + ">");
    	}
    	System.out.println("</" + name + ">");
	}


	 */

	public ArrayList<Person> transferPerson() {

		ArrayList<Person> personTransferCheck = new ArrayList<Person>();
		String firstName = "";
		String lastName = "";
		String sex = "";
		String common = "";
		Date date = null;
		boolean notAdd = false;

		for (Element elementsRoot : elementsRoot) {
			for (Element elementsHeader : elementsRoot.getElementsHeader()) {
				String str = elementsHeader.getName();
				switch (str) {
					case "nome":
						firstName = elementsHeader.getCharacter();
						break;
					case "cognome":
						lastName = elementsHeader.getCharacter();
						break;
					case "sesso":
						String strTempSex = elementsHeader.getCharacter();
						switch (strTempSex) {
							case "M":
								sex = "M";
								break;
							case "F":
								sex = "F";
								break;
							default:
								notAdd = true;
								break;

						}
						break;
					case "comune_nascita":
						common = elementsHeader.getCharacter();
						break;
					case "data_nascita":
						String strTempDate = elementsHeader.getCharacter();
						int day, month, year = 0;
						try {
							year = Integer.parseInt(strTempDate.substring(0, 4));
							if (!(year < 2019 && year > 1900)) {
								year = -1;
							}
							month = Integer.parseInt(strTempDate.substring(5, 7));
							if (!(month >= 1 && month <= 12)) {
								month = -1;
							}
							day = Integer.parseInt(strTempDate.substring(8, 10));
							if (!(day >= 1 && day <= 31)) {
								day = -1;
							}
						} catch (Exception e) {
							year = -1;
							month = -1;
							day = -1;
						}
						Date dateTemp = new Date();
						dateTemp.setDate(year, month, day);
						date = dateTemp;
						break;

					default:
				}
			}
			Person person = new Person();
			person.setPerson(firstName, lastName, sex, common, date);
			personTransferCheck.add(person);
		}


		return personTransferCheck;
	}

	public ArrayList<Common> transferCommon(){
		ArrayList<Common> common = new ArrayList<>();

		return common;
	}

	public ArrayList<String> transferCode(ArrayList<Common> arrayListCommon ){
		ArrayList<String> code = new ArrayList<>();

		return code;
	}

	public  boolean chechWrightCommon(Common common){
		if (common.getId().length()==4)return true;
		return false;
	}

	public boolean checkWrightFiscalCode(String pass ,String idCommon){
		final char[] config = {'C','C','C','C','C','C','N','N','C','N','N','C','N','N','N','C'};
		final char[] convMonth ={'A','B','C','D','E','H','L','M','P','R','S','T'};
		String fiscalCode = pass.toUpperCase();
		if(fiscalCode.length() != 16) return false;
		for(int i = 0 ; i<16 ;i++) {
			if (config[i] == 'C') {
				if (fiscalCode.charAt(i) > 'Z' && fiscalCode.charAt(i) < 'A') return false;
			} else {//config[i] == 'N'
				if (fiscalCode.charAt(i) > '9' && fiscalCode.charAt(i) < '0') return false;
			}
		}
		boolean bool = false;
		for(int i = 0;i < convMonth.length;i++ ){
			if (fiscalCode.charAt(8) != convMonth[i]) {
				bool = true;
				break;
			}
		}
		if (bool)return false;
		if( !fiscalCode.substring(11,14).equals(idCommon))return false;
		if (fiscalCode.charAt(15) < 'A' || fiscalCode.charAt(15) > 'Z') return false;
		return true;
	}

}


