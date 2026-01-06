import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Simulation {
    public static void main(String[] args) {
        AstronomicalObject earth = new Planet("Earth", Planet.EARTH_RADIUS, Planet.EARTH_MASS);
        Particle ball = new Particle(0.5, 0.5, 5, 0.05, 0.5);

        ParticleBoxPanel sim = new ParticleBoxPanel(ball);
        ParticleBoxFrame frame = new ParticleBoxFrame(sim);

        double dt = 0.001; //fps = (1 / dt)

        Timer timer = new Timer((int)(dt * 1000), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double forceY = ball.forceDueToGravity(earth);
                ball.updateParticle(0, forceY, dt);
                //System.out.println(ball.getYPosition());
                sim.repaint();
            }
        });
        timer.start();
    }
}
