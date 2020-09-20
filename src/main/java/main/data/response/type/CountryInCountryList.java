package main.data.response.type;

import lombok.Data;
import main.model.Country;

@Data
public class CountryInCountryList {
    private int id;
    private String title;

    public CountryInCountryList(Country country) {
        id = country.getId();
        title = country.getTitle();
    }
}
