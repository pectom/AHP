package hierarchy;

import java.util.LinkedList;

public class Criterion {
    private final String name;
    private Matrix matrix;
    private Criterion parent;
    private LinkedList<Criterion> children = new LinkedList<>();
    private LinkedList<Double> priorityVector = new LinkedList<>();
    private LinkedList<Double> weightsVector = new LinkedList<>();

    public String getName() {
        return name;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public Criterion getParent() {
        return parent;
    }

    public void setParent(Criterion parent) {
        this.parent = parent;
    }

    public LinkedList<Criterion> getChildren() {
        return children;
    }

    public void setChildren(LinkedList<Criterion> children) {
        this.children = children;
    }

    public Criterion(String name) {
        this.name = name;
    }

    public void setPriorityVector(LinkedList<Double> priorityVector) {
        this.priorityVector = priorityVector;
    }

    public void setWeightsVector(LinkedList<Double> weightsVector) {
        this.weightsVector = weightsVector;
    }

    public LinkedList<Double> getWeightsVector() {
        return weightsVector;
    }

    public LinkedList<Double> getPriorityVector() {
        return priorityVector;
    }
}
