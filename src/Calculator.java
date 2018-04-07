import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Calculator {
    private static Representation representation;
    private static Queue<Criterion> queue = new LinkedList<>();

    private enum Method {GMM, MM}

    private static Integer numeberOfAlternatives;

    public static void geometricMeanMethod(Representation rep) {
        representation = rep;
        numeberOfAlternatives = rep.getAlternatives().size();
        Criterion root = rep.getRoot();

        estimateWeightsVectors(Method.GMM);
        estimatePriorityVector(root);
        System.out.println(root.getPriorityVector().toString());
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
                estimateWeightsVectorWithMM(criterion);
            }
            for (int i = 0; i < criterion.getChildren().size(); i++) {
                queue.add(criterion.getChildren().get(i));
            }
        }
    }

    private static void estimateWeightsVectorWithMM(Criterion criterion) {
    }

    private static void estimateWeightsVectorWithGMM(Criterion criterion) {
        LinkedList<Double> weightsVector = new LinkedList<>();
        Matrix matrix = criterion.getMatrix();
        Double[][] matrixValues = matrix.getMatirx();
        Integer matrixSize = matrix.getSize();
        Double product = 1.0;
        Double vectorElement;
        Double vectorSum  = 0.0;

        for (int i = 0; i < matrixSize; i++) {
            product = 1.0;
            for (int j = 0; j < matrixSize; j++) {
                product *= matrixValues[i][j];
            }
            vectorElement = Math.pow(product, 1.0 / matrixSize.doubleValue());
            weightsVector.add(vectorElement);
            vectorSum+=vectorElement;

        }
        for (int i = 0;i<weightsVector.size();i++) {
            Double value = weightsVector.get(i);
            weightsVector.set(i,value/vectorSum);
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
