public class Car {
    private int x;
    private int y;
    private char symbol;
    private int previousX;
    private int previousY;
    boolean isMoveLeft;
    private int startingX;

    public Car(int x, int y, char symbol, boolean isMoveLeft) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.previousX = x;
        this.previousY = y;
        this.isMoveLeft=isMoveLeft;
        startingX=x;
        moveCar();

    }

    public void moveCar(){



        if(isMoveLeft)
            moveLeft();
        else
            moveRight();

        if(!isMoveLeft && x==60){
            previousX = x;
            previousY = y;
            x=startingX;
        }
        else if (isMoveLeft && x==0)
        {
            previousX = x;
            previousY = y;
            x=startingX;
        }


    }



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
/* Don't need move up and move down
    public void moveUp(){
        previousX = x;
        previousY = y;
        y--;
    }

    public void moveDown(){
        previousX = x;
        previousY = y;
        y++;
    }
*/
    public void moveLeft(){
        previousX = x;
        previousY = y;
        x--;
    }

    public void moveRight(){
        previousX = x;
        previousY = y;
        x++;
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