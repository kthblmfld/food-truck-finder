package com.project.coding;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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

            List<FoodTruck> allFoodTrucks = fetchFoodTrucks();
            CurrentlyOpenFoodTrucks currentlyOpenFoodTrucks = new CurrentlyOpenFoodTrucks(allFoodTrucks);

            FoodTruckTerminal foodTruckTerminal = new FoodTruckTerminal();
            foodTruckTerminal.displayFoodTruckResults( currentlyOpenFoodTrucks);
            foodTruckTerminal.handleUserInput(currentlyOpenFoodTrucks);

            System.exit(0);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static List<FoodTruck> fetchFoodTrucks() {

        List<FoodTruck> foodTrucks;

        try {
            URL url = new URL(URL_FOOD_TRUCKS);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(HTTP_METHOD_GET);

            ObjectMapper objectMapper = getMapper();
            CollectionType foodTruckCollectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, FoodTruck.class);

            foodTrucks = objectMapper.readValue(httpURLConnection.getInputStream(), foodTruckCollectionType);
            foodTrucks.sort(FoodTruck::compareTo);

        } catch (IOException ioe) {

            System.out.println("Error attempting to fetch food truck data");
            System.out.println(ioe.getLocalizedMessage());
            foodTrucks = new ArrayList<>();
        }

        return foodTrucks;
    }

    // Construction is lightweight so we do it to avoid contention over a shared mapper
    private static ObjectMapper getMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.getTypeFactory().constructCollectionType(List.class, FoodTruck.class);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        return objectMapper;
    }
}