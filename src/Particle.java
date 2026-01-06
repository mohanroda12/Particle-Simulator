public class Particle {

    // Particle properties
    private double x,y; // Position in metres above ground/to the right of edge
    private double velocityX, velocityY; // In m/s
    private double accelerationX, accelerationY; // In m/s^2
    private double mass; // In kg
    private double radius;
    private double dampingFactor;

    public Particle (double x, double y, double mass, double radius, double dampingFactor) {
        this.x = x;
        this.y = y;
        this.mass = mass;
        this.radius = radius;
        this.dampingFactor = dampingFactor;
        this.velocityX = 5;
        this.velocityY = 10;
        this.accelerationX = 0;
        this.accelerationY = 0;
    }

    public void updateParticle(double forceX, double forceY, double time) {
        this.applyForce(forceX, forceY);
        this.calculateVelocity(time);
        this.updatePosition(time);
    }

    public double forceDueToGravity ( AstronomicalObject planet) {
        return mass * -planet.getSurfaceGravity(); // F = mg;
    }

    // Uses F = ma to calculate acceleration
    public void applyForce(double forceX, double forceY) {
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
        velocityX = velocityIfWallHit(velocityX, x, dt);
        velocityY = velocityIfWallHit(velocityY, y, dt);

//        if (y - radius < 0) {
//            y = radius;
//            velocityY = -velocityY * dampingFactor;
//        }
//        else if (y + radius > 1) {
//            y = 1 - radius;
//            velocityY = -velocityY * dampingFactor;
//        }
//        else {
//            y += velocityY * dt;
//        }
//
//        if (x - radius < 0) {
//            x = radius;
//            velocityX = -velocityX * dampingFactor;
//        }
//        else if (x + radius > 1) {
//            x = 1 - radius;
//            velocityX = -velocityX * dampingFactor;
//        }
//        else {
//            x += velocityX * dt;
//        }
    }

    private double calculate1DPosition(double pos, double velocity, double dt) {
        // If particles hit any walls (so they don't clip into walls)
        if (pos - radius < 0) {
            pos = radius;
        }
        else if (pos + radius > 1) {
            pos = 1 - radius;
        }
        // Update position
        else {
            pos += velocity * dt;
        }
        return pos;
    }

    private double velocityIfWallHit(double velocity, double pos, double dt) {
        if (pos - radius < 0 || pos + radius > 1) {
            return (-velocity * dampingFactor);
        }
        return velocity;
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
}
