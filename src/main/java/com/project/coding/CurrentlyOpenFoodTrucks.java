package com.project.coding;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CurrentlyOpenFoodTrucks {

    private static final int PAGE_SIZE = 10;
    private final List<FoodTruck> foodTrucks;
    private int currentPage = 0;
    private int totalPages = 0;

    public CurrentlyOpenFoodTrucks(List<FoodTruck> listToPaginate) {

        LocalDateTime now = LocalDateTime.now();

        foodTrucks = listToPaginate.stream()
                .filter(foodTruck ->
                        (foodTruck.getStartTime().isBefore(now.toLocalTime())
                                || foodTruck.getStartTime().equals(now.toLocalTime()))
                                && foodTruck.getEndTime().isAfter(now.toLocalTime())
                                && foodTruck.getDayOfWeek().equalsIgnoreCase(now.getDayOfWeek().name())
                )
                .collect(Collectors.toList());

        totalPages = foodTrucks.size() / PAGE_SIZE;

        if (foodTrucks.size() % PAGE_SIZE > 0) {
            totalPages++;
        }
    }

    public List<FoodTruck> getCurrentPageContents() {

        int lastIndexOfResults = foodTrucks.size();
        int lastIndexOfPages = currentPage * PAGE_SIZE + PAGE_SIZE;

        if (currentPage == 0) {

            if (lastIndexOfResults < PAGE_SIZE) {
                return foodTrucks.subList(0, lastIndexOfResults);
            } else {
                return foodTrucks.subList(0, PAGE_SIZE);
            }

        } else {
            if (lastIndexOfResults < lastIndexOfPages) {
                return foodTrucks.subList(currentPage * PAGE_SIZE, lastIndexOfResults);
            } else {
                return foodTrucks.subList(currentPage * PAGE_SIZE, lastIndexOfPages);
            }
        }
    }

    public List<FoodTruck> previousPage() {
        if (hasPreviousPage()) {
            currentPage--;
        }
        return getCurrentPageContents();
    }

    public List<FoodTruck> nextPage() {
        if (hasNextPage()) {
            currentPage++;
        }
        return getCurrentPageContents();
    }

    private boolean hasNextPage() {

        return currentPage < totalPages - 1;
    }

    private boolean hasPreviousPage() {

        return currentPage > 0;
    }
}
