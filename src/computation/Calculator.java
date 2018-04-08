package computation;

import hierarchy.Criterion;
import hierarchy.Matrix;
import hierarchy.Representation;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealVector;

import java.util.LinkedList;
import java.util.Queue;

public class Calculator {
    private static Representation representation;
    private static Queue<Criterion> queue = new LinkedList<>();

    private enum Method {GMM, EVM}

    private static Integer numeberOfAlternatives;

    public static void findAlternative(Representation rep, String method) {
        representation = rep;
        numeberOfAlternatives = rep.getAlternatives().size();
        Criterion root = rep.getRoot();
        if (method.equalsIgnoreCase("gmm")) {
            estimateWeightsVectors(Method.GMM);
        } else {
            estimateWeightsVectors(Method.EVM);
        }
        estimatePriorityVector(root);
        System.out.println("This is a priority vector for this hierarchy:");
        System.out.println(root.getPriorityVector().toString());
        int maxIndex = 0;
        for (int i = 0; i < root.getPriorityVector().size(); i++) {
            maxIndex = root.getPriorityVector().get(i) > root.getPriorityVector().get(maxIndex) ? i : maxIndex;
        }
        System.out.println("The best alternative is " + representation.getAlternatives().get(maxIndex) + ".\n");

    }

    private static void estimateWeightsVectors(Method method) {
        Criterion root = representation.getRoot();
        if (root == null) {
            return;
        }
        queue.clear();
        queue.add(root);
        while (!queue.isEmpty()) {
            Criterion criterion = queue.remove();
            if (method.equals(Method.GMM)) {
                estimateWeightsVectorWithGMM(criterion);
            } else {
                estimateWeightsVectorWithEVM(criterion);
            }
            queue.addAll(criterion.getChildren());
        }
    }

    private static void estimateWeightsVectorWithEVM(Criterion criterion) {
        Array2DRowRealMatrix matrix = new Array2DRowRealMatrix(criterion.getMatrix().getMatrix());
        EigenDecomposition decomposition = new EigenDecomposition(matrix);
        double[] eigenValues = decomposition.getRealEigenvalues();
        int maxIndex = 0;
        for (int i = 0; i < eigenValues.length; i++) {
            maxIndex = eigenValues[i] > eigenValues[maxIndex] ? i : maxIndex;
        }
        RealVector eigenVector = decomposition.getEigenvector(maxIndex);
        LinkedList<Double> weightsVector = new LinkedList<>();

        int size = eigenVector.getDimension();
        Double sum = 0.0;
        for (int i = 0; i < size; i++) {
            sum += eigenVector.getEntry(i);
        }
        Double multiplier = 1.0 / sum;
        eigenVector.mapMultiplyToSelf(multiplier);
        for (int i = 0; i < size; i++) {
            weightsVector.add(eigenVector.getEntry(i));
        }
        criterion.setWeightsVector(weightsVector);
    }

    private static void estimateWeightsVectorWithGMM(Criterion criterion) {
        LinkedList<Double> weightsVector = new LinkedList<>();
        Matrix matrix = criterion.getMatrix();
        double[][] matrixValues = matrix.getMatrix();
        Integer matrixSize = matrix.getSize();
        Double product = 1.0;
        Double vectorElement;
        Double vectorSum = 0.0;

        for (int i = 0; i < matrixSize; i++) {
            product = 1.0;
            for (int j = 0; j < matrixSize; j++) {
                product *= matrixValues[i][j];
            }
            vectorElement = Math.pow(product, 1.0 / matrixSize.doubleValue());
            weightsVector.add(vectorElement);
            vectorSum += vectorElement;

        }
        for (int i = 0; i < weightsVector.size(); i++) {
            Double value = weightsVector.get(i);
            weightsVector.set(i, value / vectorSum);
        }
        criterion.setWeightsVector(weightsVector);

    }

    private static void estimatePriorityVector(Criterion criterion) {
        if (criterion.getChildren().size() != 0) {
            for (Criterion crit : criterion.getChildren()) {
                estimatePriorityVector(crit);
            }
            LinkedList<Double> priorityVector = new LinkedList<>();

            for (int i = 0; i < numeberOfAlternatives; i++) {
                Double value = 0.0;
                for (int j = 0; j < criterion.getChildren().size(); j++) {
                    value += criterion.getWeightsVector().get(j) * criterion.getChildren().get(j).getPriorityVector().get(i);
                }
                priorityVector.add(value);
            }
            criterion.setPriorityVector(priorityVector);
        } else {
            criterion.setPriorityVector(criterion.getWeightsVector());
        }
    }


}
