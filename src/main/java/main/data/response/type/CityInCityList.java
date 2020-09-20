package main.data.response.type;

import lombok.Data;
import main.model.City;

@Data
public class CityInCityList {
    private int id;
    private String title;

    public CityInCityList(City city) {
        id = city.getId();
        title = city.getTitle();
    }
}
