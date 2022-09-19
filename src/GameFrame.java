import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameFrame extends JFrame {
    static GamePanel myPanel;

    public GameFrame(){
        super("platformTest");
        setSize(1000,700);
        myPanel = new GamePanel("1");
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(myPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        myPanel.initiateAnimationLoop();

    }


}
