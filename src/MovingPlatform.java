import java.awt.*;

public class MovingPlatform extends Enemy {
    double x, y;
    double vel;

    public MovingPlatform(int rS, int rE, int y,int w,int h,double setVel){
        super(rS, rE, y,w, h);
        vel = setVel;
        x = getStartX();
        this.y = getStartY();
    }

    @Override
    public boolean checkPlayerCollision(PlayerCharacter player) {
        return x-getWidth()*0.5 < player.getX() + player.getWidth()*0.5 &&
                x + getWidth()*0.5 > player.getX()- player.getWidth()*0.5 &&
                y - getHeight()*0.5 < player.getY() + player.getHeight()*0.5 &&
                y + getHeight()*0.5 > player.getY() - player.getHeight()*0.5;

    }

    public void onPlayerCollision(PlayerCharacter player){
        double thresholdX = (player.getWidth()+getWidth())*0.5;
        double thresholdY = (player.getHeight()+getHeight())*0.5;

        double overlapX = (thresholdX - Math.abs(player.getX()-x));
        double overlapY = (thresholdY - Math.abs(player.getY()-y));

        if(overlapX>0&&overlapY>=0){
            if(overlapX<overlapY){
                player.setX(player.getX()+overlapX*Math.signum(player.getX()-x));
                player.setxVel(player.getxVel()*0.5);
            }else{
                if(player.getY()-y>0){
                    player.setY(player.getY()+overlapY);
                    player.setyVel(player.getyVel()*0.5);
                }else{
                    player.setY(player.getY()-overlapY);
                    player.setGrounded(true);
                    player.setyVel(0);
                }
            }
        }
    }

    public void updatePosition(GamePanel panel){
        x+=vel*panel.getDeltaTime();
        if(x<rangeStart){
            x = rangeStart;
            vel = Math.abs(vel);
        }if(x>rangeEnd){
            x = rangeEnd;
            vel = -Math.abs(vel);
        }
    }

    public void update(GamePanel panel, Graphics g, PlayerCharacter player){
        updatePosition(panel);
        if(x+width/2.0 > panel.getShift() && x-width/2.0 < panel.getShift()+1000){
            g.setColor(Color.red);
            g.fillRect((int)x- (int)panel.getShift()-width/2,(int)y-height/2,width,height);
            if(checkPlayerCollision(player)){
                onPlayerCollision(player);
            }
        }
    }
}
