import javax.swing.*;

public class ParticleBoxFrame extends JFrame{

    public ParticleBoxFrame(ParticleBoxPanel panel){
        setTitle("Particle simulation");
        add(panel);
        // size frame to fit panel exactly
        pack();
        // center on screen
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
