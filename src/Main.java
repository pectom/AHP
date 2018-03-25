import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Representation test = new Representation();
        Representation test2 = RepresentationParser.parse("/home/tomasz/Pulpit/Studia/semestr 4/boitzo/AHP2/out/test2.xml");
        test.create();
        test2.createXMLFile();
        /*String path;
        test.create();
        test.printRepresentation();
        path = test.createXMLFile();
        test.createRepresentationFromXMLFile(path);
        test.printAlternatives();*/
    }
}
