import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        RepresentationCreator.setIn("./out/input");
        RepresentationCreator.setOut("./out/output");
        Representation test = RepresentationCreator.create();
        Representation test2 = RepresentationParser.parse("./out/dane.xml");
        test.createXMLFile();
        test2.createXMLFile();

    }
}
