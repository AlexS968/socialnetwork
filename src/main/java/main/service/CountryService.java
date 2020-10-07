package main.service;

import main.data.request.ListCountryRequest;
import main.data.response.ListCountryResponse;

public interface CountryService {
    ListCountryResponse list(ListCountryRequest request);
}
