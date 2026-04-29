public class Vector {
    private double[] elements;
    private int dimension;

    public Vector(double[] array) throws VectorException {
        if (array == null) {
            throw new VectorException("Vector elements cannot be null.");
        }

        if (array.length != 2 && array.length != 3) {
            throw new VectorException("Only 2D or 3D vectors are allowed.");
        }

        this.elements = array.clone();
        this.dimension = array.length;
    }

    public int getDimension(){
        return this.dimension;
    }

    private void checkDimensions(Vector other) throws VectorException {
        if (other == null) {
            throw new VectorException("Other vector cannot be null.");
        }

        if (this.dimension != other.dimension) {
            throw new VectorException("Vector dimensions must be the same.");
        }
    }

    public Vector add(Vector other) throws VectorException {
        checkDimensions(other);

        double[] result = new double[this.dimension];
        for (int i = 0; i < this.dimension; i++) {
            result[i] = this.elements[i] + other.elements[i];
        }
        return new Vector(result);
    }

    public Vector subtract(Vector other) throws VectorException {
        checkDimensions(other);

        double[] result = new double[this.dimension];
        for (int i = 0; i < this.dimension; i++) {
            result[i] = this.elements[i] - other.elements[i];
        }
        return new Vector(result);
    }

    public double dotProduct(Vector other) throws VectorException {
        checkDimensions(other);

        double result = 0.0;
        for (int i = 0; i < this.dimension; i++) {
            result += this.elements[i] * other.elements[i];
        }
        return result;
    }

    public void display() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < this.dimension; i++) {
            result.append(this.elements[i]);
            if (i < this.dimension - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        System.out.println(result.toString());
    }

    public static void main(String args[]) {

    }
}
