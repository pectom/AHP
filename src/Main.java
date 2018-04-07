import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner =  new Scanner(System.in);
        List<Representation> representations = new LinkedList<>();
        Representation test1 = RepresentationParser.parse("./out/daneauta.xml");
        Representation test2 = RepresentationParser.parse("./out/nowy.xml");
        representations.add(test1);
        representations.add(test2);
        Calculator.geometricMeanMethod(test2);
        boolean exit=false;
        while(!exit){
            String option;
            printMenu();
            switch (option = scanner.nextLine()){
                case "1":
                    representations.add(RepresentationCreator.create());
                    System.out.println("Do you want to create XML file from this representation? (y/n)");
                    if(scanner.nextLine().equals("y")){
                        representations.get(representations.size()).createXMLFile();
                    }
                    break;
                case  "2":
                    String path="";
                    System.out.println("Enter path of new hierarchy");
                    File f = new File(path);
                    while ( !f.exists() || f.isDirectory()){
                        path = scanner.nextLine();
                        f = new File(path);
                    }
                    break;
                case "3":
                    representations.forEach(value->System.out.println(value.getName()));
                    break;
                case "4":
                    exit=true;
                    break;
                default:
                    System.out.println("Wrong input");
            }
       }

    }
    public static void printMenu(){
        System.out.println("1.Create a new hierarchy via terminal");
        System.out.println("2.Load a hierarchy from XML file");
        System.out.println("3.Print list of available hierarchy");
        System.out.println("4.exit");
    }
}
