import java.io.FileInputStream;
import java.lang.System;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;


public class Reader {

	private String HEADER = " ";

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
