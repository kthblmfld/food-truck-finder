package com.project.coding;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class FoodTruckTerminal {

    private static final int MAX_LENGTH_NAME = 40;
    private Terminal terminal;
    private TextGraphics textGraphics;

    public FoodTruckTerminal() {

        try {
            terminal = new DefaultTerminalFactory().createTerminal();
            textGraphics = terminal.newTextGraphics();
            terminal.resetColorAndSGR();
        } catch (IOException e) {
            System.out.println("Error instantiating terminal");
        }
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

    public void displayFoodTruckResults(CurrentlyOpenFoodTrucks currentlyOpenFoodTrucks) {
        try {
            terminal.clearScreen();
            displayHeader();

            int resultLine = 1;

            for (FoodTruck foodTruck : currentlyOpenFoodTrucks.getCurrentPageContents()) {

                String output = formatName(foodTruck.getName()) + foodTruck.getAddress();

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

    private String formatName(String name) {

        if (name.length() > MAX_LENGTH_NAME - 2) {
            return name.substring(0, MAX_LENGTH_NAME - 3) + ".. ";
        } else {
            return padString(name, MAX_LENGTH_NAME);
        }
    }

    private void displayHeader() {
        textGraphics.putString(0, 0, padString("NAME", MAX_LENGTH_NAME) + "ADDRESS");
    }

    private static String padString(String string, int padding) {
        return string + buildSpaces(padding - string.length());
    }

    public void close() throws IOException {
        terminal.clearScreen();
        terminal.close();
        System.exit(0);
    }
}
