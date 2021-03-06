package creation;

import hierarchy.Criterion;
import hierarchy.Matrix;
import hierarchy.Representation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class RepresentationParser {
    private static Criterion rootCriterion;
    private static LinkedList<String> alternatives;

    public static Representation parse(String path) {
        File file = new File(path);
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);

            Element root = doc.getDocumentElement();

            parseRoot(root, doc);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        String[] pathTable = path.split("/");
        String fileName = pathTable[pathTable.length - 1];
        String name = fileName.substring(0, fileName.length() - 4);

        return new Representation(rootCriterion, alternatives, name);
    }

    private static void parseRoot(Element root, Document doc) {
        NodeList rootChildNodeList = root.getChildNodes();
        NodeList alternativeNodeList = doc.getElementsByTagName("CHOICE");


        for (int i = 0; i < rootChildNodeList.getLength(); i++) {
            if (rootChildNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element criterionNode = (Element) rootChildNodeList.item(i);
                if (criterionNode.getTagName().equals("CRITERION")) {
                    rootCriterion = new Criterion("Overall Satisfaction");
                    parseCriterion(criterionNode, rootCriterion);
                }
            }
        }
        parseAlternatives(alternativeNodeList);
    }

    private static void parseCriterion(Element criterionElement, Criterion criterion) {
        Matrix matrix = new Matrix();
        LinkedList<Criterion> children = new LinkedList<>();

        matrix = matrix.createMatrixFromString(criterionElement.getAttribute("m"));

        criterion.setMatrix(matrix);
        criterion.setChildren(children);

        if (criterionElement.getChildNodes().getLength() != 0) {


            NodeList criterionChildNodeList = criterionElement.getChildNodes();
            for (int i = 0; i < criterionChildNodeList.getLength(); i++) {
                if (criterionChildNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element subcriterionElement = (Element) criterionChildNodeList.item(i);

                    Criterion subcriterion = new Criterion(subcriterionElement.getAttribute("name"));
                    subcriterion.setParent(criterion);
                    children.add(subcriterion);

                    parseCriterion(subcriterionElement, subcriterion);
                }
            }
        }
    }

    private static void parseAlternatives(NodeList alternativesNodeList) {
        alternatives = new LinkedList<>();
        for (int i = 0; i < alternativesNodeList.getLength(); i++) {
            if (alternativesNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element alternativeElement = (Element) alternativesNodeList.item(i);
                alternatives.add(alternativeElement.getTextContent());
            }
        }
    }


}
