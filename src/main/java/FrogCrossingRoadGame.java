import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FrogCrossingRoadGame {
    public static void main(String[] args) throws Exception {
        startGame();

    }
    public static void startGame() throws Exception {

        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);
        Frog frog = new Frog(13,23, '\u263a');
        List<Car> cars = createCars();

        printBackground(terminal, frog);

        terminal.setCursorPosition(frog.getX(), frog.getY());
        terminal.putCharacter(frog.getSymbol());

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
                terminal.setCursorPosition(frog.getX(), frog.getY());
                terminal.putCharacter('\u2588');

                for (int i = 0; i < gameOver.length(); i++) {
                    terminal.setCursorPosition(i+65, 4);
                    terminal.setForegroundColor(TextColor.ANSI.RED);
                    terminal.setBackgroundColor(TextColor.ANSI.BLACK);
                    terminal.putCharacter(gameOver.charAt(i));
                }
                terminal.flush();
                break;
            }
            terminal.setForegroundColor(TextColor.ANSI.CYAN);
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

        //Create all cars


        cars.add(new Car(3, 1, 'C',false));
        cars.add(new Car(2, 1, 'C',false));
        cars.add(new Car(1, 1, 'C',false));


        cars.add(new Car(3, 3, 'U',false));
        cars.add(new Car(2, 3, 'U',false));
        cars.add(new Car(1, 3, 'U',false));


        cars.add(new Car(54, 5, 'X',true));
        cars.add(new Car(55, 5, 'X',true));
        cars.add(new Car(56, 5, 'X',true));

        cars.add(new Car(54, 7, 'X',true));
        cars.add(new Car(55, 7, 'X',true));
        cars.add(new Car(56, 7, 'X',true));



        cars.add(new Car(3, 9, 'Z',false));
        cars.add(new Car(2, 9, 'Z',false));



        cars.add(new Car(50, 11, 'H',true));
        cars.add(new Car(51, 11, 'H',true));

        cars.add(new Car(50, 13, 'H',true));
        cars.add(new Car(51, 13, 'H',true));

        cars.add(new Car(50, 15, 'H',true));
        cars.add(new Car(51, 15, 'H',true));

        cars.add(new Car(50, 17, 'H',true));
        cars.add(new Car(51, 17, 'H',true));

        cars.add(new Car(45, 19, 'H',false));
        cars.add(new Car(44, 19, 'H',false));

        cars.add(new Car(23, 21, 'H',false));
        cars.add(new Car(22, 21, 'H',false));
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
            //System.out.println(car.getPreviousX());
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

    private static void printBackground(Terminal t, Frog frog) throws IOException, InterruptedException {

        final char block = '\u2588';
        for (int i = 0; i < 30; i++){
            t.setForegroundColor(TextColor.ANSI.CYAN);
            t.setCursorPosition(61, i);
            t.putCharacter(block);
            t.flush();
        }

        //Print roads
        final char road = '-';
        for (int j=0;j<24;j+=2) {
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
            t.setForegroundColor(TextColor.ANSI.RED);
            t.setBackgroundColor(TextColor.ANSI.BLACK);
            t.putCharacter(levelText.charAt(i));
        }
        //String level = "LEVEL" ;
        String lvl = Integer.toString(frog.getLevel());
        for (int i = 0; i < lvl.length(); i++) {
            t.setCursorPosition(73, 2);
            t.setBackgroundColor(TextColor.ANSI.BLACK);
            t.setForegroundColor(TextColor.ANSI.RED);
            t.putCharacter(lvl.charAt(i));
        }
        t.setForegroundColor(TextColor.ANSI.CYAN);

    }

    private static boolean hitByCar(Frog frog, List<Car> cars){
        for (Car c:cars)
            if (c.getX() == frog.getX() && c.getY() == frog.getY()) {
                return true;
            }
        return false;
    }
}


