public class AstronomicalObject {

    public final double G = 6.6743e-11; // Gravitational constant
    private String name;
    private double radius,mass,surfaceGravity;

    public AstronomicalObject(String name, double radius, double mass) {
        this.name = name;
        this.radius = radius;
        this.mass = mass;
        this.surfaceGravity = calcGravFieldStrength(this.radius);
    }

    public double calcGravFieldStrength(double distanceFromCentre) {
        return (G * mass) / (distanceFromCentre * distanceFromCentre);  // g = Gm / r^2
    }

    public double calcGravitationalForce(double gravity) {
        return mass * gravity ; // F = mg
    }

    public double getSurfaceGravity() {
        return surfaceGravity;
    }
}
