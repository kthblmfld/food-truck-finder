import java.util.List;

public interface PaginatedList <T>{

    List previousPage();
    List nextPage();
    List<T> getCurrentPageContents();
}
