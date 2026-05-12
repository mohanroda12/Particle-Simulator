public class Vector2D {

    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Computes the dot product between class vector and another
    public double dotProduct(Vector2D other) {
        return (this.x * other.x + this.y * other.y);
    }

    public static double dotProduct(Vector2D vector, Vector2D other) {
        return vector.x * other.x + vector.y * other.y;
    }

    public static Vector2D multiplyVector(Vector2D vector, double constant) {
        return new Vector2D(vector.x * constant, vector.y * constant);
    }

    public static Vector2D subtractVectors(Vector2D vector, Vector2D other) {
        return new Vector2D(vector.getX() - other.getX(), vector.getY() - other.getY());
    }

    public void addVector(Vector2D other) {
        x += other.x;
        y += other.y;
    }

    public double getMagnitude() {
        return Math.sqrt(Math.pow(this.getX(), 2) + Math.pow(this.getY(), 2));
    }

    public void normalise() {
        double magnitude = this.getMagnitude();
        x = this.getX() / magnitude;
        y = this.getY() / magnitude;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double newX) {
        this.x = newX;
    }

    public void setY(double newY) {
        this.y = newY;
    }
}
