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


	public ArrayList<Person> transferPerson() {

		ArrayList<Person> personArrayList = new ArrayList<Person>();
		String firstName = "";
		String lastName = "";
		String sex = "";
		String common = "";
		Date date = null;
		boolean isWrong = false;

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
								isWrong = true;
								break;

						}
						break;
					case "comune_nascita":
						common = elementsHeader.getCharacter();
						break;
					case "data_nascita":
						String strTempDate = elementsHeader.getCharacter();
						int day = 0, month = 0, year = 0;
						try {
							year = Integer.parseInt(strTempDate.substring(0, 4));
							if (!(year < 2019 && year > 1900)) {
								isWrong = true;
							}
							month = Integer.parseInt(strTempDate.substring(5, 7));
							if (!(month >= 1 && month <= 12)) {
								isWrong = true;
							}
							day = Integer.parseInt(strTempDate.substring(8, 10));
							if (!(day >= 1 && day <= 31)) {
								isWrong = true;
							}
						} catch (Exception e) {
							isWrong = true;
						}
						Date dateTemp = new Date();
						dateTemp.setDate(year, month, day);
						date = dateTemp;
						break;

					default:
						isWrong=true;
				}
			}
			Person person = new Person();
			person.setPerson(firstName, lastName, sex, common, date, isWrong);
			personArrayList.add(person);
			isWrong=false;
		}
		return personArrayList;
	}

	public ArrayList<Common> transferCommon() {
		ArrayList<Common> commonArrayList = new ArrayList<>();

		String id = "";
		String idTemp ="";
		String name = "";
		boolean isWrong = false;

		for (Element elementsRoot : elementsRoot) {
			for (Element elementsHeader : elementsRoot.getElementsHeader()) {
				String str = elementsHeader.getName();
				switch (str) {
					case "codice":
						id = elementsHeader.getCharacter();
						idTemp = id;
						try {
							idTemp.substring(0, 3);
						} catch (Exception e) {
							isWrong = true;
						}
						if (! (idTemp.charAt(0) >= 'A' && idTemp.charAt(0) <= 'Z')){
							isWrong=true;
						}else{
							for(int i=1; i<4;i++){
								if(!(idTemp.charAt(i) >= '0' && idTemp.charAt(i) <= '9')){
									isWrong=true;
								}
							}

						}
						break;
					case "nome":
						name = elementsHeader.getCharacter();
						break;
					default:
						isWrong = true;
						break;

				}
			}
			Common common = new Common();
			common.setCommon(id,name,isWrong);
			commonArrayList.add(common);
			isWrong=false;
		}
		return commonArrayList;
	}

	public ArrayList<String> transferCode () {
		ArrayList<String> codeArrayList = new ArrayList<>();
		String fiscalCode = "";
		boolean isWrong = false;

		for (Element elementsRoot : elementsRoot) {

				String str = elementsRoot.getName();
				switch (str) {
					case "codice":
						fiscalCode= elementsRoot.getCharacter();
						break;
					default:
						isWrong=true;
						break;

				}
				if(!isWrong){
					codeArrayList.add(fiscalCode);
					isWrong=false;
				}


			}
		return codeArrayList;
		}


}



