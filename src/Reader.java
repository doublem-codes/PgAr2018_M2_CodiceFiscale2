import java.io.FileInputStream;
import java.lang.System;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

/*
<persone numero="1000">										rootPerson.name="persone"
    <persona id="0">										rootPerson.attributes[0].name = "numero"
        <nome>GIUSEPPE</nome>								rootPerson.attributes.value[0]="1000"
        <cognome>MUSSO</cognome>							rootPerson.subElements[0].name = "persona"
        <sesso>M</sesso>									rootPerson.subElements[0].attributes[0].name = "id"
        <comune_nascita>ALCARA LI FUSI</comune_nascita>		rootPerson.subElements[0].attributes[0].value = "0"
        <data_nascita>1940-04-27</data_nascita>					rootPerson.subElements[0].subElements[0].name = "nome"
    </persona>													rootPerson.subElements[0].subElements[0].character="GIUSEPPE"
 </persone>														rootPerson.subElements[0].subElements[1].name = "cognome"

 */

public class Reader {

	private String HEADER = " ";

	public Element read(String fileName, String strHeader, boolean hasHeader) {

		//setup serve per controllare che il reader abbia visto il primo tag del file xml
		//imAtRow serve per controllare di essere al tag row
		//root viene inizializzato in cima e gli viene assegnato il valore del primo tag (ossia il root)

		HEADER = strHeader;
		boolean setup = true;
		boolean imAtHeader = false;
		Element root = null;
		Element eHeader = null;
		Element genericItem = null;
		Attribute attribute = null;

		try {
			XMLInputFactory xmlif=XMLInputFactory.newInstance();
			XMLStreamReader xmlr = xmlif.createXMLStreamReader(fileName, new FileInputStream(fileName));
			while(xmlr.hasNext()) {
				switch(xmlr.getEventType()) {
					case XMLStreamConstants.START_DOCUMENT:
						//mi assicuro che setup sia settata a true e imAtRow sia settata a false nel momento in cui inizio a leggere il documento
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


							if(startTag.equals(HEADER) && hasHeader) {
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
								for(int i = 0; i < xmlr.getAttributeCount(); i++) {
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
