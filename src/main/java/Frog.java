public class Frog {
    private int x;
    private int y;
    private char symbol;
    private int previousX;
    private int previousY;
    private int points;
    public int level;

    public Frog(int x, int y, char symbol) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.previousX = x;
        this.previousY = y;
        points=0;
        level=0;
    }
    public int getPoints(){return points;}
    public int getLevel(){return level;}
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getPreviousX() {
        return previousX;
    }

    public int getPreviousY() {
        return previousY;
    }

    public void moveUp(){
      //  System.out.println("y: " + y);
        points++;
        if(y==1){
            level++;
            previousY=y;
            previousX=x;
            y=23;
            points+=10;
          //  System.out.println("level: " + level + " - Points: " + points );
        }
        else {

            previousX = x;
            previousY = y;
            y -= 2;
        }
    }

    public void moveDown(){

        if(y!=23) {
            previousX = x;
            previousY = y;
            y += 2;
        }
    }

    public void moveLeft(){

        if(x>0) {
            previousX = x;
            previousY = y;
            x--;
        }
    }

    public void moveRight(){
        if(x<60) {
            previousX = x;
            previousY = y;
            x++;
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "x=" + x +
                ", y=" + y +
                ", symbol=" + symbol +
                ", previousX=" + previousX +
                ", previousY=" + previousY +
                '}';
    }
}