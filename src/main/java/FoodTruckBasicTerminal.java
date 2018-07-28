import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class FoodTruckBasicTerminal {

    private Terminal terminal;

    public FoodTruckBasicTerminal() {

        try {
            terminal = new DefaultTerminalFactory().createTerminal();

        } catch (IOException e) {
            System.out.println("Error instantiating terminal");
        }
    }

    void displayFoodTruckResults(FoodTruckSearchResults foodTruckSearchResults) {

        try {
            terminal.clearScreen();
        } catch (IOException e) {
            System.out.println("Error attempting to clear the terminal screen");
        }

        displayHeader(terminal);
        for (FoodTruck foodTruck : foodTruckSearchResults.getCurrentPageContents()) {
            String output = padName(foodTruck.getName(), 25) + foodTruck.getAddress();
            printLineToTerminal(terminal, output);
        }
    }

    private static void printLineToTerminal(Terminal terminal, String output) {
        try {
            for (int i = 0; i < output.length(); i++) {

                terminal.putCharacter(output.charAt(i));
            }
            terminal.putCharacter('\n');
            terminal.flush();

        } catch (IOException e) {
            System.out.println("Error attempting to print to terminal");
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

    private static void displayHeader(Terminal terminal) {
        printLineToTerminal(terminal, padName("NAME", 25) + "ADDRESS");
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
