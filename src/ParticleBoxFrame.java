import javax.swing.*;

public class ParticleBoxFrame extends JFrame{

    public ParticleBoxFrame(ParticleBoxPanel panel){
        setTitle("Particle simulation");
        add(panel);
        //maximize the JFrame to fit the entire screen.
        setExtendedState(MAXIMIZED_BOTH);
        // size frame to fit panel exactly
        pack();
        // center on screen
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
