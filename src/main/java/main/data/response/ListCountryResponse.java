package main.data.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import main.data.response.base.ListResponse;
import main.data.response.type.CountryInCountryList;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ListCountryResponse extends ListResponse {
    private List<CountryInCountryList> data;

    public ListCountryResponse(List<CountryInCountryList> countries) {
        data = countries;
    }

    public void add(List<CountryInCountryList> countries) {
        data.addAll(countries);
    }
}
