import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class Matrix {
    private Double[][] matrix;
    private Integer size;

    public Matrix createMatrixFromString(String matrixString) {
        String[] rows = matrixString.split("; ");
        size = rows.length;
        matrix = new Double[size][size];
        for (int i = 0; i < size; i++) {
            String[] fields = rows[i].split(" ");
            for (int j = 0; j < size; j++) {
                System.out.println(fields[j] + " " + i + " " + j);
                matrix[i][j] = Double.valueOf(fields[j]);
            }
        }
        return this;
    }


    public String toString() {
        StringBuilder matrixString = new StringBuilder();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (j == (matrix.length - 1)) {
                    matrixString.append(matrix[i][j]);
                } else {
                    matrixString.append(matrix[i][j].toString()).append(" ");
                }
            }
            if (i != (matrix.length - 1)) {
                matrixString.append("; ");
            }
        }

        return matrixString.toString();
    }

    public Matrix createMatrixFromUserInput(LinkedList<String> names, Scanner scanner) {
        Integer size = names.size();
        this.size = size;
        matrix = new Double[size][size];
        for (int i = 0; i < size; i++) {
            matrix[i][i] = 1.0;
            for (int j = i + 1; j < size; j++) {
                System.out.println("How many times " + names.get(i) + " is more important/better than " + names.get(j));
                Double ratio = positiveDoubleInput(scanner);

                BigDecimal bd = new BigDecimal(ratio);
                bd = bd.setScale(12, RoundingMode.HALF_UP);
                matrix[i][j] = bd.doubleValue();

                bd = new BigDecimal(1 / ratio);
                bd = bd.setScale(12, RoundingMode.HALF_UP);

                matrix[j][i] = bd.doubleValue();

            }
        }
        return this;
    }

    private Double positiveDoubleInput(Scanner scanner) {

        Double value = null;
        boolean goodInput = false;
        while (!goodInput) {
            try {
                value = scanner.nextDouble();
                scanner.nextLine();
                if (value < 0) {
                    goodInput = false;
                    System.out.println("Wrong input. You have to enter positive Double.");
                } else {
                    goodInput = true;
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Wrong input. You have to enter positive Double.");
            }
        }
        return value;
    }

}
