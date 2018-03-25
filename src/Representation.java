import java.io.FileNotFoundException;
import java.util.LinkedList;

public class Representation {
    private Criterion root;
    private LinkedList<String> alternatives;

    public Representation() {
        root = new Criterion("Overall Satisfaction");
        alternatives = new LinkedList<>();
    }

    public void create() throws FileNotFoundException {
        RepresentationCreator creator = new RepresentationCreator(root, alternatives);
        creator.run();
    }

    public void printAlternatives() {
        for (String alternative : alternatives) {
            System.out.println(alternative);
        }
    }

    public void printRepresentation() {
        printAlternatives();
        LinkedList<Criterion> children = root.getChildren();
        System.out.print("Representation: ");
        System.out.println(root.getName());

        for (int i = 0; i < children.size(); i++){
            System.out.print(children.get(i).getName()+" ");
        }
        System.out.print("\n");
        for (int i = 0; i < children.size(); i++){
            printChildren(children.get(i));
        }
    }

    private void printChildren(Criterion criterion) {
        LinkedList<Criterion> children = criterion.getChildren();

        System.out.println(criterion.getName());
        for (int i = 0; i < children.size(); i++){
            System.out.print(children.get(i).getName()+" ");
        }
        System.out.print("\n");

        for (int i = 0; i < children.size(); i++){
            printChildren(children.get(i));
        }

    }

    public void createXMLFile() {

    }


}
