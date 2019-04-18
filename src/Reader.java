import java.io.FileInputStream;
import java.lang.System;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;


public class Reader {

	private String HEADER = " ";

	/**
	 *
	 * @param fileName : it contains the string of the xml file to read
	 * @param strHeader: it contains the string of the header
	 *                 		codiciFiscali.txt: <codice>RRAMHL24M31L584H</codice>
	 *                 		strHeader: codice
	 *
	 *                 		comuni.txt :   <comune>
	 *    									 <nome>ABANO TERME</nome>
	 *    									 <codice>A001</codice>
	 *   									</comune>
	 *   					strHeader: comune
	 *
	 *   		       		inputPersone.txt: <persona id="0">
	 *         									<nome>GIUSEPPE</nome>
	 *        									<cognome>MUSSO</cognome>
	 *         									<sesso>M</sesso>
	 *         									<comune_nascita>ALCARA LI FUSI</comune_nascita>
	 *         									<data_nascita>1940-04-27</data_nascita>
	 *     										</persona>
	 *     					strHeader: persona
	 *
	 *
	 * @return : the element you are reading
	 */

	public Element read(String fileName, String strHeader) {


		HEADER = strHeader;
		boolean setup = true;
		boolean imAtHeader = false;
		Element root = null;
		Element eHeader = null;
		Element genericItem = null;
		Attribute attribute = null;
		boolean temp = false;

		try {
			XMLInputFactory xmlif=XMLInputFactory.newInstance();
			XMLStreamReader xmlr = xmlif.createXMLStreamReader(fileName, new FileInputStream(fileName));
			while(xmlr.hasNext()) {
				switch(xmlr.getEventType()) {
					case XMLStreamConstants.START_DOCUMENT:
						setup = true;
						imAtHeader = false;
						System.out.println("Start Read Doc " + fileName);
						break;
					case XMLStreamConstants.START_ELEMENT:
						String startTag = xmlr.getLocalName();
						if(setup) {
							root = new Element(startTag);
							for(int i = 0; i < xmlr.getAttributeCount(); i++) {
								attribute = new Attribute(xmlr.getAttributeLocalName(i));
								root.getAttributesRoot().add(attribute);
								root.getAttributesRoot().get(i).setValue(xmlr.getAttributeValue(i));
							}
							setup = false;
						}
						else {
							//Element temp = new Element(startTag);
							if(startTag.equals(HEADER)) {
								eHeader = new Element(startTag);
								for(int i = 0; i < xmlr.getAttributeCount(); i++) {
									attribute = new Attribute(xmlr.getAttributeLocalName(i));
									eHeader.getAttributesHeader().add(attribute);
									eHeader.getAttributesHeader().get(i).setValue(xmlr.getAttributeValue(i));
								}

								root.getElementsRoot().add(eHeader);
								imAtHeader = true;
							}
							else {
								imAtHeader = false;
									genericItem = new Element(startTag);
									for (int i = 0; i < xmlr.getAttributeCount(); i++) {
										attribute = new Attribute(xmlr.getAttributeLocalName(i));
										eHeader.getAttributesHeader().add(attribute);
										eHeader.getAttributesHeader().get(i).setValue(xmlr.getAttributeValue(i));

								}
								eHeader.getElementsHeader().add(genericItem);
							}
						}
						break;
					case XMLStreamConstants.END_ELEMENT:
						String endTag = xmlr.getLocalName();
						if(endTag.equals(root.getName())) {
							System.out.println("End Read Doc " + fileName);
						}
						break;
					case XMLStreamConstants.NOTATION_DECLARATION:
						System.out.println("Inside "+xmlr.getText());
						break;
					case XMLStreamConstants.CHARACTERS:
						String character = xmlr.getText();

						if(character.trim().length()>0)
							if(setup) {
								root.setCharacter(character);
							}
							else {
								if(imAtHeader) {
									eHeader.setCharacter(character);
								}
								else {
									genericItem.setCharacter(character);
								}
							}
						break;
					default:
						break;
				}
				xmlr.next();
			}
		}
		catch(Exception e){
			System.err.println("Error detected");
			System.err.println(e.getMessage());
		}
		return root;
	}

}
