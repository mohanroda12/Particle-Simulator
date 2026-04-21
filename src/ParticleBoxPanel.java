import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ParticleBoxPanel extends JPanel {
    private ArrayList<Particle> particles;

    public ParticleBoxPanel(ArrayList<Particle> particles) {
        this.particles = particles;
        this.setPreferredSize(new Dimension(750, 750));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Draw box
        g2.setColor(Color.WHITE);

        int scaleFactor = getWidth(); // Width = 1x1 m

        // Draw particles
        for (Particle particle : particles) {
            g2.setColor(particle.getColour());
            double radius = particle.getRadius();

            g2.fillOval((int) Math.round((particle.getXPosition() - radius) * scaleFactor),
                    (int) Math.round(getHeight() - ((particle.getYPosition() + radius) * scaleFactor)),
                    (int) Math.round(radius * scaleFactor * 2),
                    (int) Math.round(radius * scaleFactor * 2));

            g2.setColor(Color.BLACK);
            g2.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
            String xPos = String.format("%.2f", particles.getFirst().getXPosition());
            String yPos = String.format("%.2f", particles.getFirst().getYPosition());
            String xVelocity = String.format("%.2f", particles.getFirst().getVelocityX());
            String yVelocity = String.format("%.2f", particles.getFirst().getVelocityY());
            g2.drawString("X: " + xPos, 50, 50);
            g2.drawString("Y: " + yPos, 50, 80);

            g2.drawString("X velocity: " + xVelocity, 500, 50);
            g2.drawString("Y velocity: " + yVelocity, 500, 80);
        }
    }
}
