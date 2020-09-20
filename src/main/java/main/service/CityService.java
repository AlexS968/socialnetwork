package main.service;

import main.data.request.ListCityRequest;
import main.data.response.ListCityResponse;

public interface CityService {
    ListCityResponse list(ListCityRequest request);
}
