import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner =  new Scanner(System.in);
/*
    static {
        try {
            scanner = new Scanner(new FileInputStream(new File("./out/in")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
*/
    private static List<Representation> representations = new LinkedList<>();
    public static void main(String[] args) throws FileNotFoundException {

        Representation test1 = RepresentationParser.parse("./out/daneauta.xml");
        Representation test2 = RepresentationParser.parse("./out/nowy.xml");
        Representation test3 = RepresentationParser.parse("./out/mini.xml");
        representations.add(test1);
        representations.add(test2);
        representations.add(test3);
        boolean exit = false;
        System.out.println("Welcome in my AHP application.");
        System.out.println("Input a number to do selected action.\n");
        while (!exit) {
            String option;
            printMenu();
            switch (option = scanner.nextLine()) {
                case "1":
                    createRepresentation();
                    break;
                case "2":
                    parseRepresentaionFromXMLFile();
                    break;
                case "3":
                    computeHierachy();
                    break;
                case "4":
                    printHierarchyList();
                break;
                case "5":
                    printSelectedRepresentation();
                    break;
                case "6":
                    exit = true;
                    break;
                default:
                    System.out.println("Wrong input");
            }
        }

    }

    private static void printMenu() {
        System.out.println("1.Create a new hierarchy via terminal.");
        System.out.println("2.Load a hierarchy from XML file.");
        System.out.println("3.Find the best alternative.");
        System.out.println("4.Print list of available hierarchy.");
        System.out.println("5.Print hierarchy.");
        System.out.println("6.exit");
    }
    private static void printHierarchyList(){
        representations.forEach(value -> System.out.println(value.getName()));
    }
    private static void computeHierachy(){
        System.out.println("Which hierarchy do you want to compute?");

        Representation representationToCompute = selectRepresentation();

        System.out.println("Select method which do you want to use. (GMM/EVM)");
        String method = scanner.nextLine();
        while (!method.equalsIgnoreCase("gmm") && !method.equalsIgnoreCase("evm")){
            System.out.println("Print gmm or evm");
            method = scanner.nextLine();
        }
        Calculator.findAlternative(representationToCompute,method);

    }
    private static void createRepresentation(){
        representations.add(RepresentationCreator.create());
        System.out.println("Do you want to create XML file from this representation? (y/n)");
        if (scanner.nextLine().equals("y")) {
            representations.get(representations.size() - 1).createXMLFile();
        }
    }
    private static void parseRepresentaionFromXMLFile(){
        System.out.println("Enter path of new hierarchy");
        String path =scanner.nextLine();
        File f = new File(path);
        while (!f.exists() || f.isDirectory()) {
            System.out.println("I can't find this file.");
            System.out.println("Select another one");
            path = scanner.nextLine();
            f = new File(path);
        }
        representations.add(RepresentationParser.parse(path));
    }
    private static void printSelectedRepresentation(){
        System.out.println("Select hierarchy to print: ");
        Representation representation = selectRepresentation();
        representation.printRepresentation();
    }
    private static Representation selectRepresentation(){
        printHierarchyList();
        Representation representation = null;
        String name = scanner.nextLine();
        boolean goodNameInput = false;
        while (!goodNameInput){
            for (Representation rep: representations) {
                if (rep.getName().equals(name)){
                    goodNameInput = true;
                    representation = rep;
                    break;
                }
            }
            if(!goodNameInput){
                System.out.println("There isn't "+ name+" hierarchy");
                System.out.println("Select another one.");
                name = scanner.nextLine();
            }
        }
        return representation;
    }
}
