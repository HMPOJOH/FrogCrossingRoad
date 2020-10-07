import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FrogCrossingRoadGame {
    public static void main(String[] args) throws Exception {
        startGame();

    }
    private static void startGame() throws Exception {



        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);
        Frog frog = new Frog(13,13, '\u263a');
        List<Car> cars = createCars();

        printBackground(terminal);



        terminal.setCursorPosition(frog.getX(), frog.getPreviousX());
        terminal.putCharacter('\u263a');

        // Exemple of playing background music in new thread, just use Music class and these 2 lines:
       // Thread thread = new Thread(new Music());
        //thread.start();

        KeyStroke latestKeyStroke = null;
        drawCars(terminal, cars);
        boolean continueReadingInput = true;
        while (continueReadingInput) {

            int index = 0;
            KeyStroke keyStroke = null;
            do {
                index++;
                if (index % 20 == 0) {

                    moveCars(cars);
                    drawCars(terminal, cars);
                    terminal.flush();

                }

                Thread.sleep(5); // might throw InterruptedException
                keyStroke = terminal.pollInput();


            } while (keyStroke == null);
            latestKeyStroke = keyStroke;
            //if (latestKeyStroke != null)
                handlePlayer(frog, latestKeyStroke, terminal);
            moveCars(cars);
            drawCars(terminal, cars);
            terminal.flush();


            drawFrog(terminal, frog);

        }
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


        cars.add(new Car(3, 3, 'U',false));
        cars.add(new Car(2, 3, 'U',false));
        cars.add(new Car(1, 3, 'U',false));


        cars.add(new Car(54, 6, 'X',true));
        cars.add(new Car(55, 6, 'X',true));
        cars.add(new Car(56, 6, 'X',true));



        cars.add(new Car(3, 9, 'Z',false));
        cars.add(new Car(2, 9, 'Z',false));



        cars.add(new Car(50, 10, 'H',true));
        cars.add(new Car(51, 10, 'H',true));

        for (Car car : cars)
            System.out.println(car.toString());

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
            System.out.println(car.getPreviousX());
            terminal.setCursorPosition(car.getPreviousX(), car.getPreviousY());
            terminal.putCharacter(' ');

            terminal.setCursorPosition(car.getX(), car.getY());
            terminal.putCharacter(car.getSymbol());
            terminal.flush();
        }
    }


    private static void drawFrog(Terminal terminal, Frog frog) throws IOException {


        terminal.flush();

        terminal.setCursorPosition(frog.getPreviousX(), frog.getPreviousY());
        terminal.putCharacter(' ');

        terminal.setCursorPosition(frog.getX(), frog.getY());
        terminal.putCharacter(frog.getSymbol());

        terminal.flush();

    }

    private static void printBackground(Terminal t) throws IOException {

        final char block = '\u2588';
        for (int i = 0; i < 30; i++){
            t.setForegroundColor(TextColor.ANSI.CYAN);
            t.setCursorPosition(61, i);
            t.putCharacter(block);
            t.flush();
        }

        //Print roads
        final char road = '-';
        for (int i = 0; i < 60; i++){
            t.setForegroundColor(TextColor.ANSI.CYAN);
            t.setCursorPosition(i, 11);
            t.putCharacter(road);
            t.flush();
        }

    }


}


