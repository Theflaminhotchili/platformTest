import java.awt.*;

public class AirEnemy extends Enemy{

    double x, y;
    double vel;
    boolean chasePlayer;

    public AirEnemy(int rS, int rE, int y,int w,int h,double vel){
        super(rS, rE, y,w,h);
        this.y = y;
        this.vel = vel;
    }

    @Override
    public boolean checkPlayerCollision(PlayerCharacter player) {
        return x < player.getX() + player.getWidth() &&
                x + getWidth() > player.getX() &&
                y < player.getY() + player.getHeight() &&
                y + getHeight() > player.getY();

    }

    public void updatePosition(GamePanel panel){
        if(x<rangeStart){
            x = rangeStart;
            vel = Math.abs(vel);
        }if(x>rangeEnd){
            x = rangeEnd;
            vel = -Math.abs(vel);
        }
        x+=vel*panel.getDeltaTime();
    }

    public void update(GamePanel panel, Graphics g,PlayerCharacter player){
        updatePosition(panel);
        if(x+width/2.0 > panel.getShift() && x-width/2.0 < panel.getShift()+1000){
            g.setColor(Color.red);
            g.fillRect((int)x- (int)panel.getShift()-width/2,(int)y-height/2,width,height);
            if(checkPlayerCollision(player)){player.damage(this);}
        }
    }
}
