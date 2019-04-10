import java.util.ArrayList;

public class Element{

	private String name;
	private String character = null;

	private ArrayList<Attribute> attributesRoot = new ArrayList();
	private ArrayList<Element> elementsRoot = new ArrayList();

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

	/*
	public ArrayList<Element> getSubElements() {
		return subElements;
	}

	public ArrayList<Attribute> getAttributes() {
		return attributes;
	}

*/

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

}

