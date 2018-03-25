import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Representation test = new Representation();
        Representation test2 = RepresentationParser.parse("./out/dane.xml");
        test2.createXMLFile();

    }
}
