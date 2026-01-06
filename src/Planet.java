public class Planet extends AstronomicalObject {

    public static final int EARTH_RADIUS = 6378137;
    public static final double EARTH_MASS = 5.9722e24;

    public Planet(String name, double radius, double mass) {
        super(name, radius, mass);
    }
}
