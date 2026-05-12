import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Simulation {

    private final double SPEED = 1; // How fast the program runs
    private final double DT = 0.001; // Time between physics calculations

    ArrayList<Particle> particles; //List of all particles to be shown in simulation
    Planet planet; //Planet that the particles will simulate on

    public Simulation(Planet planet, ArrayList<Particle> particles) {
        this.planet = planet;
        this.particles = particles;
    }

    public void simulateTick(ParticleBoxPanel simUI) {
        // Go through to simulate physics of each particle
        for(int i = 0; i < particles.size(); i++) {
            Particle particle1 = particles.get(i);
            double forceY = particle1.calculateForceY(planet);
            double forceX = particle1.calculateForceX(planet);
            particle1.updateParticle(forceX, forceY, DT * SPEED);
            // Prevent calculating between two particles twice
            for(int j = i; j < particles.size(); j++) {
                Particle particle2 = particles.get(j);
                particle1.handleParticleCollision(particle2);
            }
        }
//        for (Particle particle1 : particles) {
//            double forceY = particle1.calculateForceY(planet);
//            double forceX = particle1.calculateForceX(planet);
//            particle1.updateParticle(forceX, forceY, DT * SPEED);
//            for (Particle particle2 : particles) {
//                particle1.handleParticleCollision(particle2);
//            }
//
//        }
        simUI.repaint();
    }

    public void simulate(ParticleBoxPanel simUI) {
        // Simulate every x ms
        Timer timer = new Timer((int)(1 / SPEED), e -> simulateTick(simUI));
        timer.start();
    }

    public static void main(String[] args) {
        // Particles to simulate
        ArrayList<Particle> particles = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 2; i++) {
            particles.add(new Particle(
                    new Vector2D(Math.random(), Math.random()),
                    5,
                    0.08,
                    Math.random(),
                    new Color(55, 255, 0),
                    new Vector2D(rand.nextInt(21) - 10, rand.nextInt(21) - 10)
            ));
        }
        Planet earth = new Planet("Earth", Planet.EARTH_RADIUS, Planet.EARTH_MASS);

        ParticleBoxPanel simUI = new ParticleBoxPanel(particles);
        ParticleBoxFrame frame = new ParticleBoxFrame(simUI);

        Simulation simulation = new Simulation(earth, particles);
        simulation.simulate(simUI);
    }
}
