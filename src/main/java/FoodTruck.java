import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "name", "address" })
public class FoodTruck implements Comparable<FoodTruck>{

    private String name;

    private String address;

    @JsonProperty("name")
    public String getName(){
        return name;
    }

    @JsonProperty("address")
    public String getAddress(){
        return address;
    }

    @JsonProperty("applicant")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("location")
    public void setAddress(String address) {
        this.address = address;
    }

    public int compareTo(FoodTruck foodTruck) {
        return name.compareTo(foodTruck.getName());
    }
}
