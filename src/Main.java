import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Representation test = new Representation();
        test.create();
        test.printRepresentation();
        test.createXMLFile();
    }
}
