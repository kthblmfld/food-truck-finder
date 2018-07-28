import java.util.ArrayList;
import java.util.List;

public class FoodTruckSearchResults implements PaginatedList {

    private static final int PAGE_SIZE = 10;
    private ArrayList<FoodTruck> paginatedList;
    private int currentPage = 0;
    private int totalPages = 0;

    public FoodTruckSearchResults(ArrayList<FoodTruck> listToPaginate) {

        paginatedList = listToPaginate;
        totalPages = paginatedList.size() / PAGE_SIZE;
        if (totalPages % PAGE_SIZE > 0) {
            totalPages++;
        }
    }

    private boolean hasNextPage() {
        return currentPage < totalPages - 1;
    }

    private boolean hasPreviousPage() {
        return currentPage > 0;
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

    public List<FoodTruck> getCurrentPageContents() {
        if (currentPage == 0) {
            return paginatedList.subList(0, PAGE_SIZE);
        } else {
            return paginatedList.subList(currentPage * PAGE_SIZE, currentPage * PAGE_SIZE + PAGE_SIZE);
        }

    }
}
