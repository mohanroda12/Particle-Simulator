import javax.swing.*;
import java.awt.*;

public class ParticleBoxPanel extends JPanel {
    private Particle particle;

    public ParticleBoxPanel(Particle particle) {
        this.particle = particle;
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

        // Draw particle
        g2.setColor(Color.GREEN);
        double radius = particle.getRadius();
//        System.out.println((int)Math.round(getHeight() - ((particle.getYPosition() - radius) * scaleFactor)));

        g2.fillOval((int)Math.round((particle.getXPosition() - radius) * scaleFactor),
                (int)Math.round(getHeight() - ((particle.getYPosition() + radius) * scaleFactor)),
                (int)Math.round(radius * scaleFactor * 2),
                (int)Math.round(radius * scaleFactor * 2));
    }
}
