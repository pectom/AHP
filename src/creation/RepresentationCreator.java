package creation;

import hierarchy.Criterion;
import hierarchy.Matrix;
import hierarchy.Representation;

import java.io.*;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class RepresentationCreator {
    private static Scanner scanner;
    private static Criterion root;
    private static LinkedList<String> alternatives;
    private static PrintStream out = System.out;
    private static InputStream in = System.in;

    public static Representation create() {
        scanner = new Scanner(in);
        System.setOut(out);

        System.out.println("Enter the name of the representation");
        String name;
        name = scanner.nextLine();
        root = new Criterion("Overall Satisfaction");
        alternatives = new LinkedList<>();

        readAlternatives();
        readCriterions();

        return new Representation(root, alternatives, name);
    }

    private static void readCriterions() {
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


        for (Criterion aChildren : children) {
            addCriterion(root, aChildren);
        }
    }

    private static void addCriterion(Criterion parent, Criterion criterion) {
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

            for (Criterion aChildren : children) {
                addCriterion(criterion, aChildren);
            }
        }

    }

    private static void readSubcriterions(Integer amountOfSubcriterions, LinkedList<Criterion> children, Matrix matrix) {
        LinkedList<String> subcriterionsNames = new LinkedList<>();

        System.out.println("Now enter their names: ");
        for (int i = 0; i < amountOfSubcriterions; i++) {
            String subName = scanner.nextLine();
            Criterion child = new Criterion(subName);

            children.add(child);
            subcriterionsNames.add(subName);
        }
        matrix.createMatrixFromUserInput(subcriterionsNames, scanner);
    }

    private static void leaf(Criterion criterion, Matrix matrix) {
        System.out.println(criterion.getName() + " have no more subcriterions. Now in view of this criterion compare alternatives.");
        matrix.createMatrixFromUserInput(alternatives, scanner);

    }

    private static void readAlternatives() {
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

    private static Integer positiveIntegerInput() {
        Integer value = null;
        boolean goodInput = false;
        while (!goodInput) {
            try {
                value = scanner.nextInt();
                scanner.nextLine();
                if (value < 0) {
                    goodInput = false;
                    System.out.println("Wrong input. You have to enter positive integer.");
                } else {
                    goodInput = true;
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Wrong input. You have to enter positive integer.");
            }
        }
        return value;
    }

    private static void printBranch(Criterion criterion) {
        Criterion parent = criterion.getParent();
        String criterionName = criterion.getName();
        StringBuilder ancestorsString = new StringBuilder();
        String[] ancestorsTable;

        System.out.println("\nThis is a branch connected with " + criterion.getName());

        while (parent != null) {
            ancestorsString.append(parent.getName()).append(":");
            criterion = criterion.getParent();
            parent = criterion.getParent();
        }
        ancestorsTable = ancestorsString.toString().split(":");

        for (int i = ancestorsTable.length - 1; i >= 0; i--) {
            System.out.print(ancestorsTable[i] + " -> ");
        }
        System.out.print(criterionName + "\n");
    }
}
