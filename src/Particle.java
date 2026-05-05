import java.awt.*;

public class Particle {

    // Particle properties
    private double x,y; // Position in metres above ground/to the right of edge
    private Vector2D velocity; // In m/s
    private double accelerationX, accelerationY; // In m/s^2
    private double mass; // In kg
    private double radius;
    private double dampingFactor;
    private double forceX, forceY; // In Newtons
    private Color colour;

    public Particle (double x, double y, double mass, double radius, double dampingFactor, Color colour, Vector2D initialVelocity) {
        this.x = x;
        this.y = y;
        this.mass = mass;
        this.radius = radius;
        this.dampingFactor = dampingFactor;
        this.velocity = initialVelocity;
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
            if(Math.abs(velocity.getX()) < 0.01) {
                velocity.setX(0);
                return 0;
            }
            // Apply friction opposing motion
            else if (velocity.getX() > 0) {
                return -forceDueToGravity(planet) * frictionCoefficient * -1;
            }
            else if (velocity.getX() < 0) {
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
        velocity.setX(velocity.getX() + (accelerationX * dt));
        velocity.setY(velocity.getY() + (accelerationY * dt));
    }

    // Use speed = distance / time to calculate new position
    public void updatePosition(double dt) {
        // speed * dt = distance travelled
        y = calculate1DPosition(y, velocity.getY(), dt);
        x = calculate1DPosition(x, velocity.getX(), dt);
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
        velocity.setX(velocityIfWallHit(velocity.getX(), x, dt));
        velocity.setY(velocityIfWallHit(velocity.getY(), y, dt));
    }

    private double velocityIfWallHit(double velocity, double pos, double dt) {
        // Reverse velocity if a wall is hit
        if (pos - radius < 0 || pos + radius > 1) {
            return (-velocity * dampingFactor);
        }
        return velocity;
    }

    // Distance from two particles centres
    public double particleDistance(Particle otherParticle) {
        double distanceX = this.getXPosition() - otherParticle.getXPosition();
        double distanceY = this.getYPosition() - otherParticle.getYPosition();
        return Math.pow(distanceX, 2) + Math.pow(distanceY, 2);
    }

    public double velocityDifference(Particle otherParticle) {
        double dvX = this.getVelocity().getX() - otherParticle.getVelocity().getX();
        double dvY = this.getVelocity().getY() - otherParticle.getVelocity().getY();
        return Math.pow(dvX, 2) + Math.pow(dvY, 2);
    }

    public boolean checkCollision(Particle otherParticle) {
        double distance = this.particleDistance(otherParticle);

        // Return true if the particles are overlapping
        return distance <= this.getRadius() + otherParticle.getRadius();
    }

    public void handleParticleCollision(Particle otherParticle) {
        // If a collision occurred
        if(this.checkCollision(otherParticle)) {
            // Change particle velocities
            double impulseScalar = (dampingFactor + 1) * otherParticle.getMass() / (this.getMass() + otherParticle.getMass());
            double distance = this.particleDistance(otherParticle);
            double velocityDiff = this.velocityDifference(otherParticle);
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

//    public double getVelocityX() {
//        return velocity.getX();
//    }
//
//    public double getVelocityY() {
//        return velocity.getY();
//    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public double getDampingFactor() {
        return dampingFactor;
    }

    public Color getColour () {
        return colour;
    }
}
