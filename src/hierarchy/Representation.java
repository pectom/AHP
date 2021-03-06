package hierarchy;

import hierarchy.Criterion;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

public class Representation {
    private final Criterion root;
    private final LinkedList<String> alternatives;
    String name;

    public Representation(Criterion _root, LinkedList<String> _alternatives, String _name) {
        root = _root;
        alternatives = _alternatives;
        name = _name;
    }


    public void printAlternatives() {
        System.out.println("List of alternatives: ");
        for (String alternative : alternatives) {
            System.out.println(alternative);
        }
    }

    public void printRepresentation() {
        System.out.print("hierarchy.Representation: ");
        System.out.println(name + "\n");

        printAlternatives();
        LinkedList<Criterion> children = root.getChildren();


        System.out.println("\nexecution.Main criterions:");
        for (Criterion aChildren : children) {
            System.out.print(aChildren.getName() + " ");

        }
        System.out.print("\n");
        for (Criterion aChildren : children) {
            printChildren(aChildren);
        }
    }

    private void printChildren(Criterion criterion) {
        LinkedList<Criterion> children = criterion.getChildren();

        System.out.println(criterion.getName());
        for (Criterion aChildren : children) {
            System.out.print(aChildren.getName() + " ");
        }
        System.out.print("\n");

        for (Criterion aChildren : children) {
            printChildren(aChildren);
        }

    }

    public String createXMLFile() {
        String path = "";
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("ROOT");
            doc.appendChild(rootElement);

            addAlternatives(rootElement, doc);

            Element overall = doc.createElement("CRITERION");
            overall.setAttribute("m", root.getMatrix().toString());
            overall.setAttribute("name", root.getName());
            rootElement.appendChild(overall);

            addCriterions(root, overall, doc);
            path = saveXML(doc);


        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return path;
    }

    private String saveXML(Document doc) throws TransformerException {
        Scanner scanner = new Scanner(System.in);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);

        System.out.println("Give the name of XML file where you want to save your data");
        String filename = scanner.nextLine();
        String path = "./out/" + filename + ".xml";
        StreamResult result = new StreamResult(new File(path));


        transformer.transform(source, result);
        System.out.println("File saved!");
        return path;
    }

    private void addAlternatives(Element rootElement, Document doc) {
        for (String alternative1 : alternatives) {
            Element alternative = doc.createElement("CHOICE");
            alternative.appendChild(doc.createTextNode(alternative1));
            rootElement.appendChild(alternative);
        }
    }

    private void addCriterions(Criterion parent, Element parentNode, Document doc) {
        for (int i = 0; i < parent.getChildren().size(); i++) {
            Element subCriterion = doc.createElement("CRITERION");
            subCriterion.setAttribute("name", parent.getChildren().get(i).getName());
            subCriterion.setAttribute("m", parent.getChildren().get(i).getMatrix().toString());
            parentNode.appendChild(subCriterion);
            addCriterions(parent.getChildren().get(i), subCriterion, doc);
        }

    }


    public Criterion getRoot() {
        return root;
    }

    public LinkedList<String> getAlternatives() {
        return alternatives;
    }

    public String getName() {
        return name;
    }
}
