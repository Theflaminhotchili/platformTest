import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel implements KeyListener {
    public BufferedImage cMap, background;
    double deltaTime = 0;
    double shift = 0;
    PlayerCharacter player;
    AirEnemy testEnemy;
    PatrolEnemy patrolEnemy;
    MovingPlatform platform;
    double horizInput;

    public GamePanel(String mapIndex){
        super();
        addKeyListener(this);
        try {
            cMap = ImageIO.read(new File("src/cmap4.png"));
            background = ImageIO.read(new File("src/bg4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        player = new PlayerCharacter(30,0,this);
        //testEnemy = new AirEnemy(600,1100,400,30,10,0.1);
        patrolEnemy = new PatrolEnemy(1100,1600,200,40,60,0.1,this,true);
        platform = new MovingPlatform(800,1400,400,70,30,0);
        setFocusable(true);
        requestFocus();
    }

    //==========================Accessors=======================

    public BufferedImage getcMap() {
        return cMap;
    }

    public double getDeltaTime() {
        return deltaTime;
    }

    public double getShift() {
        return shift;
    }

    public void setShift(double shift) {
        this.shift = shift;
    }

    //============================ Paint ========================================

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background,-(int)player.getxShift(),0,null);
        player.update(horizInput,g);
        //testEnemy.update(this,g,player);
        patrolEnemy.update(this,g,player);
        platform.update(this,g,player);
    }

    //============================== Input ==========================================

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_D) {
            horizInput = Math.min(horizInput+1,1);
        }

        if(e.getKeyCode() == KeyEvent.VK_A) {
            horizInput = Math.max(horizInput-1,-1);
        }

        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            player.setDoJump(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_D) {
            horizInput = Math.min(horizInput,0);
        }

        if(e.getKeyCode() == KeyEvent.VK_A) {
            horizInput = Math.max(horizInput,0);
        }
    }

    // ============================= animation stuff ======================================
    /**
     * if you wish to have animation, you need to call this method from the GridDemoFrame AFTER you set the window visibility.
     */
    public void initiateAnimationLoop()
    {
        Thread aniThread = new Thread( new AnimationThread(17)); // the number here is the number of milliseconds between steps.
        aniThread.start();
    }

    /**
     * Modify this method to do what you want to have happen periodically.
     * This method will be called on a regular basis, determined by the delay set in the thread.
     * Note: By default, this will NOT get called unless you uncomment the code in the GridDemoFrame's constructor
     * that creates a thread.
     *
     */
    public void animationStep(long millisecondsSinceLastStep)
    {
        deltaTime = (double)millisecondsSinceLastStep;

        repaint();
    }



    // ------------------------------- animation thread - internal class -------------------
    public class AnimationThread implements Runnable
    {
        long start;
        long timestep;
        public AnimationThread(long t)
        {
            timestep = t;
            start = System.currentTimeMillis();
        }
        @Override
        public void run()
        {
            long difference;
            while (true)
            {
                difference = System.currentTimeMillis() - start;
                if (difference >= timestep)
                {
                    animationStep(difference);
                    start = System.currentTimeMillis();
                }
                try
                {	Thread.sleep(1);
                }
                catch (InterruptedException iExp)
                {
                    System.out.println(iExp.getMessage());
                    break;
                }
            }
        }
    }
}
