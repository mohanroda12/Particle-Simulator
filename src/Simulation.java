import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
        for (Particle particle : particles) {
            double forceY = particle.calculateForceY(planet);
            double forceX = particle.calculateForceX(planet);
            particle.updateParticle(forceX, forceY, DT * SPEED);
        }
        simUI.repaint();
    }

    public void simulate(ParticleBoxPanel simUI) {
        // Simulate every x ms
        Timer timer = new Timer((int)(1 / SPEED), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulateTick(simUI);
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        // Particles to simulate
        ArrayList<Particle> particles = new ArrayList<>();
        Particle ball = new Particle(0.5, 0.5, 5, 0.05, 0.5);
        particles.add(ball);
        Particle ball2 = new Particle(0.3, 0.6, 2, 0.08, 0.5);
        particles.add(ball2);
        Particle ball3 = new Particle(0.5, 0.5, 20, 0.02, 0.5);
        particles.add(ball3);


        Planet earth = new Planet("Earth", Planet.EARTH_RADIUS, Planet.EARTH_MASS);

        ParticleBoxPanel simUI = new ParticleBoxPanel(particles);
        ParticleBoxFrame frame = new ParticleBoxFrame(simUI);

        Simulation simulation = new Simulation(earth, particles);
        simulation.simulate(simUI);
    }
}
