import java.awt.*;

public class PatrolEnemy extends Enemy{
    double x, y;
    double vel;
    boolean chasePlayer;
    GamePanel myPanel;

    public PatrolEnemy(int rS, int rE, int y,int w,int h,double vel,GamePanel setMyPanel,boolean chasesPlayer){
        super(rS, rE, y,w,h);
        this.vel = vel;
        myPanel = setMyPanel;
        chasePlayer = chasesPlayer;
    }

    @Override
    public boolean checkPlayerCollision(PlayerCharacter player) {
        return x < player.getX() + player.getWidth() &&
                x + getWidth() > player.getX() &&
                y < player.getY() + player.getHeight() &&
                y + getHeight() > player.getY();

    }

    public void groundCollision(){
        y+=2;
        int n = 2;
        if(y>=700 - getHeight()/2.0){
            y = 699 - getHeight()/2.0;
        }else {
            while (n <= 2) {//FLOOR
                if (myPanel.getcMap().getRGB((int) x + (n * width / 4), (int) y + (height / 2)) == PlayerCharacter.MAGENTARGB) {
                    n++;
                } else {
                    this.y -= 1;
                    //n = -2;
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
        if(getRangeStart()+1000>panel.getShift() && panel.getShift()<getRangeEnd()) {
            if(chasePlayer){
                if(rangeStart-100 < player.getX() && rangeEnd+100 > player.getX()){
                    vel = Math.abs(vel) * Math.signum(player.getX()-x);
                }
            }
            updatePosition(panel);
            groundCollision();
            if (x + width / 2.0 > panel.getShift() && x - width / 2.0 < panel.getShift() + 1000) {
                g.setColor(Color.red);
                g.fillRect((int) x - (int) panel.getShift() - width / 2, (int) y - height / 2, width, height);
                if (checkPlayerCollision(player)) {
                    player.damage(this);
                }
            }
        }
    }
}
