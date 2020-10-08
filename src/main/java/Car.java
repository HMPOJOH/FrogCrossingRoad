import com.googlecode.lanterna.TextColor;

public class Car {
    private int x;
    private int y;
    private char symbol;
    private int previousX;
    private int previousY;
    boolean isMoveLeft;
    private int startingX;
    private int carSpeed;

    public TextColor getCarColor() {
        return carColor;
    }

    private TextColor carColor;

    public Car(int x, int y, char symbol, boolean isMoveLeft, TextColor carColor, int carSpeed) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.previousX = x;
        this.previousY = y;
        this.isMoveLeft=isMoveLeft;
        startingX=x;
        this.carColor=carColor;
        this.carSpeed = carSpeed;
        moveCar();


    }

    public void addCarSpeed(){carSpeed++;}
    public void addCarSpeed(int add){carSpeed+=add;}

    public void moveCar(){



        if(isMoveLeft)
            moveLeft();
        else
            moveRight();

        //carspeed = 1 ok att träffa på 60 för
        //carspeed = 2 och ismoveleft false ok att träffa på 60 för vissa och 59 om startingx = jämt dvs %2 = 0
        //carspeed = 3


        if(!isMoveLeft && x>=60){
            System.out.println("x%carspeed:" + x%carSpeed);
            previousX = x-carSpeed;
            previousY = y;
            x=0+x%carSpeed;
        }
        else if (isMoveLeft && x<=carSpeed-1 && x%3==0)
        {
            previousX = x+carSpeed;
            previousY = y;
            x=60-x%carSpeed;
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
        x-=carSpeed;
    }

    public void moveRight(){
        previousX = x;
        previousY = y;
        x+=carSpeed;
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