public abstract class Enemy {
    int rangeStart, rangeEnd;
    int startX,startY;
    int width;
    int height;

    public Enemy(){}

    public Enemy(int rS, int rE, int y,int w,int h){
        rangeStart=rS;
        rangeEnd = rE;
        startX = rS;
        startY = y;
        width = w;
        height = h;
    }

    public int getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(int rangeStart) {
        this.rangeStart = rangeStart;
    }

    public int getRangeEnd() {
        return rangeEnd;
    }

    public void setRangeEnd(int rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public abstract boolean checkPlayerCollision(PlayerCharacter player);
}
