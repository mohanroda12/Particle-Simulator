import java.awt.*;

public class Particle {

    // Particle properties
    private double x,y; // Position in metres above ground/to the right of edge
    private double velocityX, velocityY; // In m/s
    private double accelerationX, accelerationY; // In m/s^2
    private double mass; // In kg
    private double radius;
    private double dampingFactor;
    private double forceX, forceY; // In Newtons
    private Color colour;

    public Particle (double x, double y, double mass, double radius, double dampingFactor, Color colour, double initialVelocityX, double initialVelocityY) {
        this.x = x;
        this.y = y;
        this.mass = mass;
        this.radius = radius;
        this.dampingFactor = dampingFactor;
        this.velocityX = initialVelocityX;
        this.velocityY = initialVelocityY;
        this.accelerationX = 0;
        this.accelerationY = 0;
        this.forceX = 0;
        this.forceY = 0;
        this.colour = colour;
    }

    public void updateParticle(double forceX, double forceY, double time) {
        this.forceY = forceY;
        this.forceX = forceX;
        this.applyForce();
        this.calculateVelocity(time);
        this.updatePosition(time);
    }

    public double calculateForceX(Planet planet) {
        return this.frictionalForce(this.getDampingFactor() / 4, planet);
    }

    public double calculateForceY(Planet planet) {
        return forceDueToGravity(planet);
    }

    // Returns the gravitational force that is negative (downwards)
    public double forceDueToGravity(AstronomicalObject planet) {
        return mass * -planet.getSurfaceGravity(); // F = mg;
    }

    public double frictionalForce(double frictionCoefficient, AstronomicalObject planet) {
        // If the particle is on the floor, apply friction
        if (y - radius < 0) {
            if(Math.abs(velocityX) < 0.01) {
                velocityX = 0;
                return 0;
            }
            // Apply friction opposing motion
            else if (velocityX > 0) {
                return -forceDueToGravity(planet) * frictionCoefficient * -1;
            }
            else if (velocityX < 0) {
                return -forceDueToGravity(planet) * frictionCoefficient;
            }
        }
        return 0;
    }

    // Uses F = ma to calculate acceleration
    public void applyForce() {
        this.accelerationX = forceX / mass;
        this.accelerationY = forceY / mass;
    }

    // Use acceleration = change in velocity / time to calculate new velocity
    public void calculateVelocity(double dt) {
        // acc * dt = change in velocity
        velocityX += accelerationX * dt;
        velocityY += accelerationY * dt;
    }

    // Use speed = distance / time to calculate new position
    public void updatePosition(double dt) {
        // speed * dt = distance travelled
        y = calculate1DPosition(y, velocityY, dt);
        x = calculate1DPosition(x, velocityX, dt);
        // Change direction if wall is hit
        calculateCollision(dt);
    }

    private double calculate1DPosition(double position, double velocity, double dt) {
        // If particles hit any walls (so they don't clip into walls)
        if (position - radius < 0) {
            position = radius;
        }
        else if (position + radius > 1) {
            position = 1 - radius;
        }
        // Update position
        else {
            position += velocity * dt;
        }
        return position;
    }

    private void calculateCollision(double dt) {
        velocityX = velocityIfWallHit(velocityX, x, dt);
        velocityY = velocityIfWallHit(velocityY, y, dt);
    }

    private double velocityIfWallHit(double velocity, double pos, double dt) {
        // Reverse velocity if a wall is hit
        if (pos - radius < 0 || pos + radius > 1) {
            return (-velocity * dampingFactor);
        }
        return velocity;
    }

    public boolean checkCollision(Particle other) {
        double distanceX = this.getXPosition() - other.getXPosition();
        double distanceY = this.getYPosition() - other.getYPosition();

        double distance = Math.pow(distanceX, 2) + Math.pow(distanceY, 2);

        // Return true if the particles are overlapping
        return distance <= this.getRadius() + other.getRadius();
    }

    public void handleParticleCollision(Particle otherParticle) {
        if(this.checkCollision(otherParticle)) {
            // Change particle velocities
        }
    }

    public double getMass() {
        return mass;
    }

    public double getXPosition() {
        return x;
    }

    public double getYPosition() {
        return y;
    }

    public double getRadius() {
        return radius;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public double getDampingFactor() {
        return dampingFactor;
    }

    public Color getColour () {
        return colour;
    }
}
