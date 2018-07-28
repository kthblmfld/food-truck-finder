import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class FoodTruckGraphicsTerminal {

    private Terminal terminal;
    private TextGraphics textGraphics;

    public FoodTruckGraphicsTerminal() {

        try {
            terminal = new DefaultTerminalFactory().createTerminal();
            textGraphics = terminal.newTextGraphics();
        } catch (IOException e) {
            System.out.println("Error instantiating terminal");
        }
    }

    void displayFoodTruckResults(FoodTruckSearchResults foodTruckSearchResults) {
        try {
            terminal.clearScreen();
            displayHeader();

            int resultLine = 1;

            for (FoodTruck foodTruck : foodTruckSearchResults.getCurrentPageContents()) {
                String output = padName(foodTruck.getName(), 25) + foodTruck.getAddress();
                textGraphics.putString(0, resultLine++, output);
            }

            terminal.flush();
        } catch (IOException e) {
            System.out.println("Error displaying food truck results");
            System.out.println(e.getLocalizedMessage());
        }
    }

    private static String addSpaces(int count) {
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
        return name + addSpaces(padding - name.length());
    }

    public void handleUserInput(FoodTruckSearchResults foodTruckSearchResults) {

        try {

            KeyStroke keyStroke = terminal.readInput();

            while (keyStroke.getKeyType() != KeyType.Enter) {

                if (keyStroke.getKeyType() != KeyType.ArrowLeft) {
                    foodTruckSearchResults.nextPage();
                    displayFoodTruckResults(foodTruckSearchResults);

                } else if (keyStroke.getKeyType() != KeyType.ArrowRight) {
                    foodTruckSearchResults.previousPage();
                    displayFoodTruckResults(foodTruckSearchResults);

                }

                keyStroke = terminal.readInput();
            }
        } catch (IOException e) {
            System.out.println("Error responding to user input");
            System.out.println(e.getLocalizedMessage());
        }
    }
}
