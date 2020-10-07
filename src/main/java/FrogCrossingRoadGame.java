import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;


public class FrogCrossingRoadGame {
    public static void main(String[] args) throws Exception {
        startGame();

    }
    private static void startGame() throws Exception {



        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);
        Frog frog = new Frog(13,13, 'o');

        terminal.setCursorPosition(frog.getX(), frog.getPreviousX());
        terminal.putCharacter('\u263a');

        // Exemple of playing background music in new thread, just use Music class and these 2 lines:
       // Thread thread = new Thread(new Music());
        //thread.start();

        KeyStroke latestKeyStroke = null;

        boolean continueReadingInput = true;
        while (continueReadingInput) {

            int index = 0;
            KeyStroke keyStroke = null;
            do {
                index++;
                if (index % 100 == 0) {
                    if (latestKeyStroke != null) {
                        handlePlayer(frog, latestKeyStroke, terminal);

                    }
                }

                Thread.sleep(5); // might throw InterruptedException
                keyStroke = terminal.pollInput();


            } while (keyStroke == null);
            latestKeyStroke = keyStroke;


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

        // Draw player
        terminal.setCursorPosition(frog.getPreviousX(), frog.getPreviousY());
        terminal.putCharacter(' ');

        terminal.setCursorPosition(frog.getX(), frog.getY());
        terminal.putCharacter('\u263a');

        terminal.flush();
    }
}


