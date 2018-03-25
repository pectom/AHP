import java.io.*;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class RepresentationCreator {
    private Scanner scanner;
    Criterion root;
    LinkedList<String> alternatives;
    PrintStream out;
    InputStream in;

    public RepresentationCreator() throws FileNotFoundException {
        root = new Criterion("Overall Satisfaction");
        alternatives = new LinkedList<>();
        in = new FileInputStream(new File("/home/tomasz/Pulpit/Studia/semestr 4/boitzo/AHP2/out/input"));
        scanner = new Scanner(in);
        out = new PrintStream(new FileOutputStream("/home/tomasz/Pulpit/Studia/semestr 4/boitzo/AHP2/out/output"));
        System.setOut(out);
    }

    public Representation createRepresentation() {
        System.out.println("Hello in AHP-representation document creator.\n ");
        readAlternatives();
        readCriterions();

        return new Representation(root,alternatives);
    }

    private void readCriterions() {
        LinkedList<Criterion> children = new LinkedList<>();
        Matrix matrix = new Matrix();
        Integer amountOfCriterions;

        root = new Criterion("Overall Satisfaction");
        root.setParent(null);

        System.out.println("How many main criterions do you have?");
        amountOfCriterions = positiveIntegerInput();

        readSubcriterions(amountOfCriterions, children, matrix);
        root.setChildren(children);
        root.setMatrix(matrix);


        for (int i = 0; i < children.size(); i++) {
            addCriterion(root, children.get(i));
        }
    }


    private void addCriterion(Criterion parent, Criterion criterion) {
        LinkedList<Criterion> children = new LinkedList<>();
        Matrix matrix = new Matrix();

        criterion.setParent(parent);

        printBranch(criterion);

        System.out.println("How many subcriterions " + criterion.getName() + " have?");
        Integer amountOfSubcriterions = positiveIntegerInput();

        if (amountOfSubcriterions.equals(0)) {
            leaf(criterion, matrix);
            criterion.setChildren(children);
            criterion.setMatrix(matrix);

        } else {
            readSubcriterions(amountOfSubcriterions, children, matrix);
            criterion.setChildren(children);
            criterion.setMatrix(matrix);
            System.out.println(matrix.toString());

            for (int i = 0; i < children.size(); i++) {
                addCriterion(criterion, children.get(i));
            }
        }

    }

    private void readSubcriterions(Integer amountOfSubcriterions, LinkedList<Criterion> children, Matrix matrix) {
        LinkedList<String> subcriterionsNames = new LinkedList<>();

        System.out.println("Now enter their names: ");
        for (int i = 0; i < amountOfSubcriterions; i++) {
            String subName = scanner.nextLine();
            Criterion child = new Criterion(subName);

            children.add(child);
            subcriterionsNames.add(subName);
        }
        matrix.createMatrix(subcriterionsNames, scanner);
    }

    private void leaf(Criterion criterion, Matrix matrix) {
        System.out.println(criterion.getName() + " have no more subcriterions. Now in view of this criterion compare alternatives.");
        matrix.createMatrix(alternatives, scanner);

    }


    private void readAlternatives() {
        alternatives = new LinkedList<>();
        Integer amountOfAlternatives;

        System.out.println("How many alternatives do you have?");
        amountOfAlternatives = positiveIntegerInput();
        for (int i = 0; i < amountOfAlternatives; i++) {
            System.out.println("Give me the name of alternative number " + (i + 1) + " :");
            String name = scanner.nextLine();
            alternatives.add(name);
        }
    }

    private Integer positiveIntegerInput() {
        Integer value = null;
        boolean goodInput = false;
        while (!goodInput) {
            try {
                value = scanner.nextInt();
                scanner.nextLine();
                if (value < 0) {
                    goodInput = false;
                    System.out.println("Wrong input. You have to enter postitive integer.");
                } else {
                    goodInput = true;
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Wrong input. You have to enter postitive integer.");
            }
        }
        return value;
    }

    private void printBranch(Criterion criterion) {
        Criterion parent = criterion.getParent();
        String criterionName = criterion.getName();
        String ancestorsString = "";
        String[] ancestorsTabel;

        System.out.println("\nThis is a branch connected with " + criterion.getName());

        while (parent != null) {
            ancestorsString += parent.getName() + ":";
            criterion = criterion.getParent();
            parent = criterion.getParent();
        }
        ancestorsTabel = ancestorsString.split(":");

        for (int i = ancestorsTabel.length - 1; i >= 0; i--) {
            System.out.print(ancestorsTabel[i] + " -> ");
        }
        System.out.print(criterionName + "\n");
    }
}
