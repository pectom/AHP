import java.util.LinkedList;
import java.util.Scanner;

public class Matrix {
    private Double[][] matrix;
    private Integer size;



    public String toString(){
        String matrixString ="";

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if(j == (matrix.length - 1)){
                    matrixString+= matrix[i][j];
                } else {
                    matrixString+= matrix[i][j].toString() + " ";
                }
            }
            if(i != (matrix.length - 1)){
                matrixString+=";";
            }
        }

        return matrixString;
    }

    public void createMatrix(LinkedList<String> names, Scanner scanner) {
        Integer size = names.size();
        Double[][] tab = new Double[size][size];
        for (int i = 0;i <size;i++){
            tab[i][i] = 1.0;
            for (int j=i+1;j<size;j++){
                System.out.println("How many times "+ names.get(i) + " is more important/better than " + names.get(j));
                Double ratio = scanner.nextDouble();
                tab[i][j] = ratio;
                tab[j][i] = 1/ratio;
            }
        }
    }
}
