import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "name", "address" })
public class FoodTruck implements Comparable<FoodTruck>{

    private String name;
    private String address;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public String getName(){
        return name;
    }

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

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    @JsonProperty("dayofweekstr")
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    @JsonFormat(pattern = "HH:mm")
    @JsonProperty("start24")
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @JsonFormat(pattern = "HH:mm")
    @JsonProperty("end24")
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int compareTo(FoodTruck foodTruck) {
        return name.compareTo(foodTruck.getName());
    }
}
