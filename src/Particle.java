import java.awt.*;

public class Particle {

    // Particle properties
    private Vector2D position; // Position in metres above ground/to the right of edge
    private Vector2D velocity; // In m/s
    private double accelerationX, accelerationY; // In m/s^2
    private double mass; // In kg
    private double radius;
    private double dampingFactor;
    private double forceX, forceY; // In Newtons
    private Color colour;

    public Particle (Vector2D position, double mass, double radius, double dampingFactor, Color colour, Vector2D initialVelocity) {
        this.position = position;
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
        if (position.getY() - radius < 0) {
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
        position.setY(calculate1DPosition(position.getY(), velocity.getY(), dt));
        position.setX(calculate1DPosition(position.getX(), velocity.getX(), dt));
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
        velocity.setX(velocityIfWallHit(velocity.getX(), position.getX(), dt));
        velocity.setY(velocityIfWallHit(velocity.getY(), position.getY(), dt));
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
        /*
        FIX, IT IS CAUSING LAG OR GLITCH OR SOMETHING
         */
        return Vector2D.subtractVectors(getPosition(), otherParticle.getPosition()).getMagnitude();
    }

//    public double particleDistance(Particle otherParticle) {
//        /*
//        FIX, IT IS CAUSING LAG OR GLITCH OR SOMETHING
//         */
//        double xDisance  = getPosition().getX() - otherParticle.getPosition().getX();
//        double yDisance  = getPosition().getY() - otherParticle.getPosition().getY();
//        return Math.sqrt(Math.pow(xDisance, 2) + Math.pow(yDisance, 2));
//    }

    public boolean checkCollision(Particle otherParticle) {
        long start = System.nanoTime();
        double distance = this.particleDistance(otherParticle);
        System.out.println(System.nanoTime() - start);

        // Return true if the particles are overlapping
        return distance <= this.getRadius() + otherParticle.getRadius();
    }

    public void handleParticleCollision(Particle otherParticle) {
        // If a collision occurred
        if(this.checkCollision(otherParticle)) {
            // Change particle velocities
            Vector2D collisionNormal = Vector2D.subtractVectors(getPosition(), otherParticle.getPosition());
            Vector2D relativeVelocity = Vector2D.subtractVectors(getVelocity(), otherParticle.getVelocity());

            double averageDF = ((1 - dampingFactor) + (1 - otherParticle.dampingFactor)) * 0.5;

            // Create unit vector
            collisionNormal.normalise();
            double relativeNormalVel = Vector2D.dotProduct(relativeVelocity, collisionNormal);

            if(relativeNormalVel <= 0) { // If particles are moving towards each other

                // Project velocities on collision normal
                double velocity1 = Vector2D.dotProduct(getVelocity(), collisionNormal);
                double velocity2 = Vector2D.dotProduct(otherParticle.getVelocity(), collisionNormal);

                // Calculate collision projection
                double massSum = mass + otherParticle.mass;
                double numerator1 = ((mass - averageDF * otherParticle.mass) * velocity1) + ((averageDF + 1) * otherParticle.mass * velocity2);
                double finalVel1 = numerator1 / massSum;

                double numerator2 = ((otherParticle.mass - averageDF * mass) * velocity2) + ((averageDF + 1) * mass * velocity1);
                double finalVel2 = numerator2 / massSum;

                // Project to 2D
                Vector2D impulseThis = Vector2D.multiplyVector(collisionNormal, finalVel1 - velocity1);
                velocity.addVector(impulseThis);

                Vector2D impulseOther = Vector2D.multiplyVector(collisionNormal, finalVel2 - velocity2);
                otherParticle.velocity.addVector(impulseOther);
            }
        }
    }

    public double getMass() {
        return mass;
    }

    public Vector2D getPosition() {
        return position;
    }

    public double getRadius() {
        return radius;
    }

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
