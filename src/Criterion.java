import java.util.LinkedList;

public class Criterion{
    private String name;
    private Matrix matrix;
    private Criterion parent;
    private LinkedList<Criterion> children = new LinkedList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Criterion(String name, Matrix matrix, Criterion parent, LinkedList<Criterion> children) {
        this.name = name;

        this.matrix = matrix;
        this.parent = parent;
        this.children = children;
    }

    public Criterion(String name, Matrix matrix, LinkedList<Criterion> children) {
        this.name = name;
        this.matrix = matrix;
        this.children = children;
    }

    public Criterion(String name) {
        this.name = name;
    }
}
