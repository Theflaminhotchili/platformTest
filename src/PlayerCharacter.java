import java.awt.*;

public class PlayerCharacter {
    double x,y,xVel,yVel,xShift,screenX;
    boolean isGrounded,isAccelerating,canJump;
    int width = 40;
    int height = 60;
    int health = 3;
    GamePanel myPanel;
    static int MAGENTARGB = Color.MAGENTA.getRGB();
    static double gravConstant = 0.4;
    boolean doJump;
    static double MAX_SPEED = 10;


    public PlayerCharacter(double setX, double setY, GamePanel setMyPanel){
        x = setX;
        y = setY;
        screenX = setX;
        xShift = 0;
        myPanel = setMyPanel;
        xVel = 0;
        yVel = 0;
        isGrounded = false;
        doJump = false;
        canJump = false;
    }

    //====================ACCESSORS====================================
    public double getX() {return x;}
    public double getY() {return y;}
    public double getxVel() {return xVel;}
    public double getyVel() {return yVel;}
    public double getDeltaX(){return xVel;}
    public double getDeltaY(){return yVel* myPanel.getDeltaTime()*0.01;}
    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public double getxShift() {return xShift;}
    //====================ACCESSORS====================================


    //====================MUTATORS=====================================
    public void setX(double x) {this.x = x;scrollScreen();}
    public void setY(double y) {this.y = y;}
    public void setxVel(double xVel) {this.xVel = xVel;}
    public void setyVel(double yVel) {this.yVel = yVel;}
    public void setDoJump(boolean doJump) {this.doJump = doJump;}
    public void setGrounded(boolean grounded) {isGrounded = grounded;}
    //====================MUTATORS=====================================


    //====================COLLISION AND INTERACTION====================
    //Handles Collision with Static world objects using the level's collision map
    public void environmentCollision(){
        int n = -2;
        Color pointColor; //Color of the collision map at the coordinates checked for collision
        while (n<=2){//RIGHT
            if (myPanel.getcMap().getRGB((int)x+width/2,(int)y+n*(height/6))==MAGENTARGB){
                n++;
            }else{
                x-=Math.max(xVel/4.0,1);
                xVel *= 15.0/16.0;
                //n = -2;
            }
        }
        n = -2;
        while (n<=2){//LEFT
            if (myPanel.getcMap().getRGB((int)x-width/2,(int)y+n*(height/6))==MAGENTARGB){
                n++;
            }else{
                x+=Math.max(xVel/4.0,1);
                xVel *= 15.0/16.0;
                //n = -2;
            }
        }
        n = -2;
        while (n<=2){//ROOF
            if (myPanel.getcMap().getRGB((int)x+(n*width/4),(int)y-(height/2))==MAGENTARGB){
                n++;
            }else{
                y+=Math.max(myPanel.getDeltaTime()/30.0,1);
                yVel *= 15.0/16.0;
                //n = -2;
            }
        }
        n = -2;
        isGrounded = false;
        if(y>=669){
            y = 669;
            isGrounded = true;
            yVel = 0;
        }else {
            while (n <= 2) {//FLOOR
                if (myPanel.getcMap().getRGB((int) x + (n * width / 4), (int) y + (height / 2)) == MAGENTARGB) {
                    n++;
                } else {
                    yVel = Math.max(0, yVel);
                    isGrounded = true;
                    canJump = true;
                    y -= Math.max(myPanel.getDeltaTime()/30.0, 1);
                    //n = -2;
                }
            }
        }
    }

    //When damaged, lower health, and knockback
    public void damage(Enemy enemy){
        health -= 1;
        xVel = -xVel;
        x += enemy.getWidth()/2 * Math.signum(xVel);
        yVel = -yVel;
    }
    //====================COLLISION AND INTERACTION====================


    //====================MOVEMENT AND INPUT===========================
    public void jump(){
        if (isGrounded||Math.abs(yVel)<4*gravConstant){//||canJump){//Jump Script
            yVel+=33;
            isGrounded=false;
            y-=1; //makes sure collision does not ground b4 jump starts
            canJump = false;
        }
        doJump = false;

    }


    public void move(double input){
        double speedMultiplier = 1;
        if(!isGrounded){speedMultiplier=0.75;}
        if(Math.abs(xVel)>0.01&&xVel*input>=0){
            xVel += input* myPanel.deltaTime*(1.0/90.0)*speedMultiplier;
        }
        else
            xVel = input*myPanel.deltaTime*0.1*speedMultiplier;
        if(Math.abs(xVel)> myPanel.getDeltaTime()*0.33){xVel = myPanel.getDeltaTime()*0.33*Math.signum(xVel);}
        isAccelerating = input!=0;

    }

    public void scrollScreen(){
        screenX = x -xShift;
        if(xShift<0){xShift=0;}
        if(myPanel.getcMap().getWidth()+x-700>300&&x-xShift>700){
            if(myPanel.getcMap().getWidth()-xShift>1000){
                xShift = x-700;
                screenX = 700;
            }
        }else if(x-xShift<300&&x>300){//myPanel.getcMap().getWidth() + x > width){
            xShift = x-300;
            screenX = 300;
        }
    }

    public void updatePosition(){//Actually Runs Calculations for Movement
        double dv= myPanel.getDeltaTime();
        if((1000/dv)<=50){dv=18;}

        this.x += xVel;//X movement calculation

        if (!isGrounded){
            y -= yVel * dv*0.01;
            yVel-=gravConstant;
        }else if(!isAccelerating){
            xVel*=7/8.0;
        }

        if (y<height/2.0)
            y=height/2.0;
        if(y > 699-height/2.0) {
            y = 699 - height/2.0;
            yVel = 0;
            isGrounded = true;
            canJump = true;
        }
    }
    //====================MOVEMENT AND INPUT===========================


    //====================DRAWING AND ANIMATION========================
    //====================DRAWING AND ANIMATION========================


    //====================UPDATE=======================================
    public void update(double horizinput, Graphics g){
        if(doJump)
            jump();
        move(horizinput);

        updatePosition();
        environmentCollision();
        scrollScreen();

        myPanel.setShift(xShift);

        g.setColor(Color.green);
        g.fillRect((int)screenX-width/2,(int)y-height/2,width,height);
    }
    //====================UPDATE=======================================
}
