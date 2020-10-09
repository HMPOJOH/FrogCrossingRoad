import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import org.w3c.dom.Text;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;



public class FrogCrossingRoadGame {
    public static boolean isHitByCar=false;
    public static void main(String[] args) throws Exception {
        boolean playGame=false;
        int maxlevel = 0;
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Terminal terminal = terminalFactory.createTerminal();
        terminal.getTerminalSize();
        terminal.setCursorVisible(false);
        intro(terminal,maxlevel);
        //if(playGame)


    }
    private static void intro(Terminal terminal, int maxlevel) throws Exception {
        terminal.setBackgroundColor(TextColor.ANSI.BLACK);


        String[] printCarPicture = new String[22];
        printCarPicture[0] = "   █████  █████   ██   ███      █████   ██     ███    ████    ";
        printCarPicture[1] = "   █      █   █  █  █ █         █   █  █  █   █   █   █   █   ";
        printCarPicture[2] = "   ███    █ █    █  █ █  █      █ █    █  █   █████   █    █  ";
        printCarPicture[3] = "   █      █  █   █  █ █   █     █  █   █  █   █   █   █   █   ";
        printCarPicture[4] = "   █      █   █   ██   ███      █   █   ██    █   █   ████    ";
        printCarPicture[5] ="                                                             ";
        printCarPicture[6] = "                         THE GAME                             ";
        printCarPicture[7] = "   ---------------------------------------------------------  ";
        printCarPicture[8] = "   -                                                       -  ";
        printCarPicture[9] = "   -                 PRESS ENTER TO PLAY                   -  ";
        printCarPicture[10] = "   -                                                       -  ";
        printCarPicture[11] ="   -                 PRESS Q TO QUIT                       -  ";
        printCarPicture[12] ="   ---------------------------------------------------------  ";
        printCarPicture[13] ="    Current reached max level: " + maxlevel;
        printCarPicture[14] ="                                                             ";
        printCarPicture[15] ="      BY TEAM3:                                                    ";
        printCarPicture[16] ="      ███       █   █        ███             ███   ";
        printCarPicture[17] ="      █ █        █ █        █   █            █ █    ";
        printCarPicture[18] ="      ███         █         █                ███        ";
        printCarPicture[19] ="      █           █         █   █            █     ";
        printCarPicture[20] ="      █ ontus     █ usri     ███  hristian   █ ooja";
        printCarPicture[21] ="                                                         MI©E";


        for (int j = 0; j < printCarPicture.length; j++){
            for (int i = 0; i < printCarPicture[j].length(); i++) {

                terminal.setCursorPosition(i + 3, 2+j);
                if ((j>=0 && j<6) || (j>=16 && j<=19) )
                    terminal.setForegroundColor(TextColor.ANSI.GREEN);
                else
                    terminal.setForegroundColor(TextColor.ANSI.WHITE);
                terminal.setBackgroundColor(TextColor.ANSI.BLACK);

                terminal.putCharacter(printCarPicture[j].charAt(i));

            }
        }
        terminal.flush();
        KeyType type;
        Character c;
        KeyStroke keyStroke = null;
        boolean keepGoing=true;
       while (keepGoing) {
           keyStroke = null;
           do {
               Thread.sleep(5); // might throw InterruptedException
               keyStroke = terminal.pollInput();
           } while (keyStroke == null);
           type = keyStroke.getKeyType();
           c = keyStroke.getCharacter();
           if (c == Character.valueOf('q')) {

               System.out.println("quit");
               keepGoing=false;
               terminal.close();

           } if (keyStroke.getKeyType() == KeyType.Enter) {
               terminal.clearScreen();
               startGame(terminal, maxlevel);
               keepGoing=false;


           }
       }

    }

    private static void startGame(Terminal terminal, int maxlevel) throws Exception {




        Frog frog = new Frog(13,23, '\u263a');
        List<Car> cars = createCars();

        printBackground(terminal, frog);

        terminal.setCursorPosition(frog.getX(), frog.getY());
        terminal.setForegroundColor(TextColor.ANSI.WHITE);
        terminal.putCharacter(frog.getSymbol());

        // Exemple of playing background music in new thread, just use Music class and these 2 lines:
        Thread thread = new Thread(new Music());
        thread.start();


        KeyStroke latestKeyStroke = null;
        drawCars(terminal, cars);
        boolean continueReadingInput = true;
        while (continueReadingInput) {

            int index = 0;
            KeyStroke keyStroke = null;
            do {
                index+=frog.getLevel()+1;
                if (index % 20 == 0) {

                    moveCars(cars);
                    drawCars(terminal, cars);
                    terminal.flush();

                }

                Thread.sleep(5); // might throw InterruptedException
                keyStroke = terminal.pollInput();

                if(hitByCar(frog, cars))
                    break;


            } while (keyStroke == null);


            String gameOver = " GAME OVER " ;
            if (!hitByCar(frog, cars)) {
                latestKeyStroke = keyStroke;
                //if (latestKeyStroke != null)
                handlePlayer(frog, latestKeyStroke, terminal);
                moveCars(cars);
                drawCars(terminal, cars);
                terminal.flush();

                drawFrog(terminal, frog);
                printBackground(terminal, frog);
            }
            else {
                thread.stop(); //stop music
                isHitByCar=true;
                printBackground(terminal, frog);
                printDeadFrog(terminal, frog, maxlevel);
                break;
            }

        }
    }

    private static void printDeadFrog(Terminal terminal, Frog frog, int maxlevel) throws Exception {


        terminal.setForegroundColor(TextColor.ANSI.RED);

        terminal.setCursorPosition(frog.getX(), frog.getY());
        terminal.putCharacter('\u2588');

        terminal.setCursorPosition(frog.getX()-1, frog.getY());
        terminal.putCharacter('\u2588');
        terminal.setCursorPosition(frog.getX()+1, frog.getY());

        terminal.putCharacter('\u2588');
        terminal.setCursorPosition(frog.getX(), frog.getY()+1);


        terminal.putCharacter('\u2588');
        terminal.setCursorPosition(frog.getX(), frog.getY()-1);
        terminal.putCharacter('\u2588');

        terminal.flush();
        for (int i=0;i<15;i++) {
            terminal.bell();

            Thread.sleep(500);


        }
        terminal.clearScreen();
        Thread.sleep(500);
        intro(terminal, (frog.getLevel()>maxlevel? frog.getLevel() : maxlevel));

    }

    private static void handlePlayer (Frog frog, KeyStroke keyStroke, Terminal terminal) throws Exception {
        // Handle player

        switch (keyStroke.getKeyType()) {
            case ArrowDown:
                frog.moveDown();
                break;
            case ArrowUp:
                frog.moveUp();
                break;
            case ArrowRight:
                frog.moveRight();
                break;
            case ArrowLeft:
                frog.moveLeft();
                break;
        }
/*
        // Draw player
        terminal.setCursorPosition(frog.getPreviousX(), frog.getPreviousY());
        terminal.putCharacter(' ');

        terminal.setCursorPosition(frog.getX(), frog.getY());
        terminal.putCharacter('\u263a');

        terminal.flush();

        */

    }
    private static List<Car> createCars() {
        List<Car> cars = new ArrayList<>();
        final char carsymbol='\u2588';
        final char carFront = '\u2584';
        //Create all cars
        TextColor carColor = TextColor.ANSI.RED;

        //terminal.setForegroundColor(TextColor.ANSI.RED);

        //car1 = index 0-2
        int  randomCar = ThreadLocalRandom.current().nextInt(5,40);


        cars.add(new Car("car1", randomCar+1, 1, carFront,false, TextColor.ANSI.BLUE,3));
        cars.add(new Car("car1", randomCar, 1, carsymbol,false,TextColor.ANSI.RED, 3));
        cars.add(new Car("car1", randomCar-1, 1, carsymbol,false,TextColor.ANSI.RED, 3));


        randomCar = ThreadLocalRandom.current().nextInt(5,40);
        //car2 = index 3-5
        cars.add(new Car("car2", randomCar+1, 3, carFront,false,TextColor.ANSI.GREEN, 2));
        cars.add(new Car("car2", randomCar, 3, carsymbol,false,TextColor.ANSI.YELLOW, 2));
        cars.add(new Car("car2", randomCar-1, 3, carsymbol,false,TextColor.ANSI.YELLOW, 2));

        //car3 = index 6-8
        randomCar = ThreadLocalRandom.current().nextInt(5,40);
        cars.add(new Car("car3", randomCar-1, 5, carFront,true,TextColor.ANSI.YELLOW, 1));
        cars.add(new Car("car3", randomCar, 5, carsymbol,true,TextColor.ANSI.GREEN, 1));
        cars.add(new Car("car3", randomCar+1, 5, carsymbol,true,TextColor.ANSI.GREEN, 1));

        randomCar = ThreadLocalRandom.current().nextInt(5,40);
        cars.add(new Car("car4",54, 7, carFront,true,TextColor.ANSI.GREEN, 1));
        cars.add(new Car("car4",55, 7, carsymbol,true,TextColor.ANSI.BLUE, 1));
        cars.add(new Car("car4",56, 7, carsymbol,true,TextColor.ANSI.BLUE, 1));


        randomCar = ThreadLocalRandom.current().nextInt(5,40);
        cars.add(new Car("car5",3, 9, carFront,false,TextColor.ANSI.CYAN, 2));
        cars.add(new Car("car5",2, 9, carsymbol,false,TextColor.ANSI.MAGENTA, 2));
        cars.add(new Car("car5",1, 9, carsymbol,false,TextColor.ANSI.MAGENTA, 2));

        randomCar = ThreadLocalRandom.current().nextInt(5,40);
        cars.add(new Car("car6",50, 11, carFront,true,TextColor.ANSI.YELLOW, 1));
        cars.add(new Car("car6",51, 11, carsymbol,true,TextColor.ANSI.GREEN, 1));
        cars.add(new Car("car6",52, 11, carsymbol,true,TextColor.ANSI.GREEN, 1));

        randomCar = ThreadLocalRandom.current().nextInt(5,40);
        cars.add(new Car("car7",50, 13, carFront,true,TextColor.ANSI.GREEN, 1));
        cars.add(new Car("car7",51, 13, carsymbol,true,TextColor.ANSI.CYAN, 1));
        cars.add(new Car("car7",52, 13, carsymbol,true,TextColor.ANSI.CYAN, 1));

        randomCar = ThreadLocalRandom.current().nextInt(5,40);
        cars.add(new Car("car8",47, 15, carFront,true,TextColor.ANSI.GREEN, 1));
        cars.add(new Car("car8",48, 15, carsymbol,true,TextColor.ANSI.YELLOW, 1));
        cars.add(new Car("car8",49, 15, carsymbol,true,TextColor.ANSI.YELLOW, 1));

        randomCar = ThreadLocalRandom.current().nextInt(5,40);
        cars.add(new Car("car9",50, 17, carFront,true,TextColor.ANSI.MAGENTA, 2));
        cars.add(new Car("car9",51, 17, carsymbol,true,TextColor.ANSI.GREEN, 2));
        cars.add(new Car("car9",52, 17, carsymbol,true,TextColor.ANSI.GREEN, 2));

        randomCar = ThreadLocalRandom.current().nextInt(5,40);
        cars.add(new Car("car10",45, 19, carFront,false,TextColor.ANSI.WHITE, 1));
        cars.add(new Car("car10",44, 19, carsymbol,false,TextColor.ANSI.CYAN, 1));
        cars.add(new Car("car10",43, 19, carsymbol,false,TextColor.ANSI.CYAN, 1));


        randomCar = ThreadLocalRandom.current().nextInt(5,40);
        cars.add(new Car("car11",23, 21, carFront,false,TextColor.ANSI.YELLOW, 2));
        cars.add(new Car("car11",22, 21, carsymbol,false,TextColor.ANSI.BLUE, 2));
        cars.add(new Car("car11",21, 21, carsymbol,false,TextColor.ANSI.BLUE, 2));
       // for (Car car : cars)
        //    System.out.println(car.toString());

        return cars;
    }

    private static void moveCars(List<Car> cars) {
        for (Car car : cars) {
            car.moveCar();

        }
    }


    private static void drawCars(Terminal terminal, List<Car> cars) throws IOException {
        terminal.flush();
        for (Car car : cars) {
            //System.out.println(car + " " + car.getPreviousX());
            terminal.setCursorPosition(car.getPreviousX(), car.getPreviousY());
            terminal.putCharacter(' ');


            terminal.setCursorPosition(car.getX(), car.getY());
            terminal.setForegroundColor(car.getCarColor());
            terminal.putCharacter(car.getSymbol());

            terminal.flush();
        }
    }


    private static void drawFrog(Terminal terminal, Frog frog) throws IOException {


        terminal.flush();

        terminal.setCursorPosition(frog.getPreviousX(), frog.getPreviousY());
        terminal.putCharacter(' ');

        terminal.setCursorPosition(frog.getX(), frog.getY());
        terminal.setForegroundColor(TextColor.ANSI.WHITE);
        terminal.putCharacter(frog.getSymbol());

        terminal.flush();

    }

    private static void printBackground(Terminal t, Frog frog) throws IOException, InterruptedException {

        final char block = '\u2588';
        for (int i = 0; i < 30; i++) {
            t.setForegroundColor(TextColor.ANSI.CYAN);
            t.setCursorPosition(61, i);
            t.putCharacter(block);
            t.flush();
        }

        //Print roads
        final char road = '-';
        for (int j = 0; j < 24; j += 2) {
            for (int i = 0; i < 61; i++) {
                t.setForegroundColor(TextColor.ANSI.CYAN);
                t.setCursorPosition(i, j);
                t.putCharacter(road);
                t.flush();
            }
        }

        // Print out level info.
        String levelText = "LEVEL " ;
        for (int i = 0; i < levelText.length(); i++) {
            t.setCursorPosition(i+66, 2);
            t.setForegroundColor(TextColor.ANSI.CYAN);
            t.setBackgroundColor(TextColor.ANSI.BLACK);
            t.putCharacter(levelText.charAt(i));
        }
        //String level = "LEVEL" ;
        String lvl = Integer.toString(frog.getLevel());
        for (int i = 0; i < lvl.length(); i++) {
            t.setCursorPosition(73, 2);
            t.setBackgroundColor(TextColor.ANSI.BLACK);
            t.setForegroundColor(TextColor.ANSI.CYAN);
            t.putCharacter(lvl.charAt(i));
        }

         String[] printCarPicture = new String[11];
        printCarPicture[0] = "   FROG ROAD  ";
        printCarPicture[1] = "     ()-()    ";
        printCarPicture[2] = "   .-(___)-.  ";
        printCarPicture[3] = "    _<   >_    ";
        printCarPicture[4] = "    \\/   \\/    ";
        printCarPicture[5] = "              ";
        printCarPicture[6] = "   ______     ";
        printCarPicture[7] = " /|_||_\\`.__ ";
        printCarPicture[8] = "(   _    _ _\\";
        printCarPicture[9] = "=`-(_)--(_)-' ";
        printCarPicture[10] = "Don't get hit ";


        for (int j = 0; j < printCarPicture.length; j++){
            for (int i = 0; i < printCarPicture[j].length(); i++) {
                t.setCursorPosition(i + 63, 5+j);
                t.setForegroundColor(TextColor.ANSI.CYAN);
                t.setBackgroundColor(TextColor.ANSI.BLACK);

                t.putCharacter(printCarPicture[j].charAt(i));
            }
        }


        }




    private static boolean hitByCar(Frog frog, List<Car> cars){
        for (Car c:cars)
            if (c.getX() == frog.getX() && c.getY() == frog.getY())
                return true;



        return false;
    }
}


