import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FoodTruckFinder {

    private static final String URL_FOOD_TRUCKS = "https://data.sfgov.org/resource/bbb8-hzi6.json";
    private static final String HTTP_METHOD_GET = "GET";

    public static void main(String[] args) {

        try {

            ArrayList<FoodTruck> foodTrucks = fetchFoodTrucks();
            FoodTruckSearchResults foodTruckSearchResults = new FoodTruckSearchResults(foodTrucks);

            Terminal terminal = new DefaultTerminalFactory().createTerminal();

            displayHeader(terminal);
            displayFoodTruckResults(terminal, foodTruckSearchResults);


            KeyStroke keyStroke = terminal.readInput();

            while (keyStroke.getKeyType() != KeyType.Enter) {

                if (keyStroke.getKeyType() != KeyType.ArrowLeft) {
                    foodTruckSearchResults.nextPage();
                    displayFoodTruckResults(terminal, foodTruckSearchResults);

                } else if (keyStroke.getKeyType() != KeyType.ArrowRight) {
                    foodTruckSearchResults.previousPage();
                    displayFoodTruckResults(terminal, foodTruckSearchResults);

                }

                keyStroke = terminal.readInput();
            }

            System.exit(0);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static ArrayList<FoodTruck> fetchFoodTrucks() {

        ArrayList<FoodTruck> foodTrucks;

        try {
            URL url = new URL(URL_FOOD_TRUCKS);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(HTTP_METHOD_GET);

            ObjectMapper objectMapper = getMapper();
            CollectionType foodTruckCollectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, FoodTruck.class);

            foodTrucks = objectMapper.readValue(httpURLConnection.getInputStream(), foodTruckCollectionType);
            foodTrucks.sort(FoodTruck::compareTo);

        } catch (IOException ioe) {

            System.out.println("Encountered an error attempting to fetch food truck data");
            System.out.println(ioe.getLocalizedMessage());
            foodTrucks = new ArrayList<>();
        }

        return foodTrucks;
    }

    private static void printLineToTerminal(Terminal terminal, String output) {
        try {
            for (int i = 0; i < output.length(); i++) {

                terminal.putCharacter(output.charAt(i));
            }
            terminal.putCharacter('\n');
            terminal.flush();

        } catch (IOException e) {
            System.out.println("Encountered an error attempting to print to terminal");
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

    private static void displayFoodTruckResults(Terminal terminal, FoodTruckSearchResults foodTruckSearchResults) {

        try {
            terminal.clearScreen();
        } catch (IOException e) {
            System.out.println("Encountered an error attempting to clear the terminal screen");
        }

        displayHeader(terminal);
        for (FoodTruck foodTruck : foodTruckSearchResults.getCurrentPageContents()) {
            String output = padName(foodTruck.getName(), 25) + foodTruck.getAddress();
            printLineToTerminal(terminal, output);
        }
    }

    // Construction is lightweight so we do it to avoid contention over a shared mapper
    private static ObjectMapper getMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.getTypeFactory().constructCollectionType(List.class, FoodTruck.class);
        return objectMapper;
    }

    private static String padName(String name, int padding) {
        return name + addSpaces(padding - name.length());
    }

    private static void displayHeader(Terminal terminal) {
        printLineToTerminal(terminal, padName("NAME", 25) + "ADDRESS");
    }
}


// to run:
// $ javac FoodTruckFinder.java && java FoodTruckFinder