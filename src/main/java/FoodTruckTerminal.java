import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class FoodTruckTerminal {

    private Terminal terminal;
    private TextGraphics textGraphics;

    public FoodTruckTerminal() {

        try {
            terminal = new DefaultTerminalFactory().createTerminal();
            textGraphics = terminal.newTextGraphics();
        } catch (IOException e) {
            System.out.println("Error instantiating terminal");
        }
    }

    void displayFoodTruckResults(CurrentlyOpenFoodTrucks currentlyOpenFoodTrucks) {
        try {
            terminal.clearScreen();
            displayHeader();

            int resultLine = 1;

            for (FoodTruck foodTruck : currentlyOpenFoodTrucks.getCurrentPageContents()) {
                String output = padName(foodTruck.getName(), 25) + foodTruck.getAddress();
                textGraphics.putString(0, resultLine++, output);
            }

            terminal.flush();
        } catch (IOException e) {
            System.out.println("Error displaying food truck results");
            System.out.println(e.getLocalizedMessage());
        }
    }

    private static String buildSpaces(int count) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    private void displayHeader() {
        textGraphics.putString(0, 0, padName("NAME", 25) + "ADDRESS");
    }

    private static String padName(String name, int padding) {
        return name + buildSpaces(padding - name.length());
    }

    public void handleUserInput(CurrentlyOpenFoodTrucks currentlyOpenFoodTrucks) {

        try {

            KeyStroke keyStroke = terminal.readInput();

            while (keyStroke.getKeyType() != KeyType.Enter) {

                if (keyStroke.getKeyType() != KeyType.ArrowLeft) {
                    currentlyOpenFoodTrucks.nextPage();
                    displayFoodTruckResults(currentlyOpenFoodTrucks);

                } else if (keyStroke.getKeyType() != KeyType.ArrowRight) {
                    currentlyOpenFoodTrucks.previousPage();
                    displayFoodTruckResults(currentlyOpenFoodTrucks);

                }

                keyStroke = terminal.readInput();
            }
        } catch (IOException e) {
            System.out.println("Error responding to user input");
            System.out.println(e.getLocalizedMessage());
        }
    }
}
