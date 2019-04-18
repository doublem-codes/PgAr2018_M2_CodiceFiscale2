import java.util.ArrayList;
import java.util.Calendar;
/**
 * It contains the element of the reader
 *
 */

public class Element {


	private String name;
	private String character = null;


	private ArrayList<Attribute> attributesRoot = new ArrayList<Attribute>();
	private ArrayList<Element> elementsRoot = new ArrayList<Element>();

	private ArrayList<Attribute> attributesHeader = new ArrayList<Attribute>();
	private ArrayList<Element> elementsHeader = new ArrayList<Element>();

	public Element(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public ArrayList<Attribute> getAttributesRoot() {
		return attributesRoot;
	}

	public ArrayList<Element> getElementsRoot() {
		return elementsRoot;
	}

	public ArrayList<Attribute> getAttributesHeader() {
		return attributesHeader;
	}

	public ArrayList<Element> getElementsHeader() {
		return elementsHeader;
	}


	/**
	 *if the Person has some parameters wrong it puts a boolean variable called isWrong to true
	 * @return : returns an ArrayList of the Person read from the xml file
	 */

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
							if (!(year <= 2019 && year > 1900)) {
								isWrong = true;
							}
							month = Integer.parseInt(strTempDate.substring(5, 7));
							if (!(month >= 1 && month <= 12)) {
								isWrong = true;
							}
							day = Integer.parseInt(strTempDate.substring(8, 10));
							if (!(checkMonthDay(month,day,year))) {
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

	/**
	 *if the Common Code is wrong it puts a boolean variable called isWrong to true
	 * @return : returns an ArrayList of the City read from the xml file
	 */
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

	/**
	 * if the FiscalCode is wrong it puts a boolean variable called isWrong to true
	 * @return : returns an ArrayList of the codeFiscal read from the xml file
	 */
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

	/**
	 *
	 * @param month: month of the person
	 * @param day: day of the person
	 * @param year: of the person
	 * @return true: if the day related to the month is correct false: if the day is wrong
	 */
	private boolean checkMonthDay(int month, int day, int year){
		Calendar calendar = Calendar.getInstance();
		switch (month){
			case 1:
				month= Calendar.JANUARY;
				break;
			case 2:
				month= Calendar.FEBRUARY;
				break;
			case 3:
				month = Calendar.MARCH;
				break;
			case 4:
				month = Calendar.APRIL;
				break;
			case 5:
				month=Calendar.MAY;
				break;
			case 6:
				month=Calendar.JUNE;
				break;
			case 7:
				month=Calendar.JULY;
				break;
			case 8:
				month= Calendar.AUGUST;
				break;
			case 9:
				month= Calendar.SEPTEMBER;
				break;
			case 10:
				month=Calendar.OCTOBER;
				break;
			case 11:
				month=Calendar.NOVEMBER;
				break;
			case 12:
				month=Calendar.DECEMBER;
				break;

		}
		calendar.set(year, month, day);
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		return day <= maxDay;
	}
}


