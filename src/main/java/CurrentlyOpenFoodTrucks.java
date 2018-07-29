import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CurrentlyOpenFoodTrucks {

    private static final int PAGE_SIZE = 10;
    private List<FoodTruck> paginatedList;
    private int currentPage = 0;
    private int totalPages = 0;

    public CurrentlyOpenFoodTrucks(List<FoodTruck> listToPaginate) {

        LocalDateTime now = LocalDateTime.now();

        paginatedList = listToPaginate.stream()
                .filter(foodTruck ->
                        foodTruck.getStartTime().isBefore(now.toLocalTime())
                                && foodTruck.getEndTime().isAfter(now.toLocalTime())
                                && foodTruck.getDayOfWeek().equalsIgnoreCase(now.getDayOfWeek().name())
                )
                .collect(Collectors.toList());

        totalPages = paginatedList.size() / PAGE_SIZE;
        if (totalPages % PAGE_SIZE > 0) {
            totalPages++;
        }
    }

    public List<FoodTruck> getCurrentPageContents() {

        int lastElementIndex = paginatedList.size() - 1;

        if (currentPage == 0) {

            if (lastElementIndex < PAGE_SIZE) {
                return paginatedList.subList(0, lastElementIndex);
            } else {
                return paginatedList.subList(0, PAGE_SIZE);
            }

        } else {
            if (lastElementIndex < currentPage * PAGE_SIZE + PAGE_SIZE) {
                return paginatedList.subList(currentPage * PAGE_SIZE, lastElementIndex);
            } else {
                return paginatedList.subList(currentPage * PAGE_SIZE, currentPage * PAGE_SIZE + PAGE_SIZE);
            }
        }
    }

    public List previousPage() {
        if (hasPreviousPage()) {
            currentPage--;
        }
        return getCurrentPageContents();
    }

    public List nextPage() {
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
