package main.data.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import main.data.response.base.ListResponse;
import main.data.response.type.CityInCityList;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ListCityResponse extends ListResponse {
    private List<CityInCityList> data;

    public ListCityResponse(List<CityInCityList> cities) {
        data = cities;
    }

    public void add(List<CityInCityList> cities) {
        data.addAll(cities);
    }
}
