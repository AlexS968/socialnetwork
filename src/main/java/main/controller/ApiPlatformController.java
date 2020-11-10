package main.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import main.data.request.ListCityRequest;
import main.data.request.ListCountryRequest;
import main.data.request.ListLanguageRequest;
import main.data.response.base.ListResponse;
import main.data.response.type.CityList;
import main.data.response.type.CountryList;
import main.data.response.type.LanguageList;
import main.service.CityServiceImpl;
import main.service.CountryServiceImpl;
import main.service.LanguageServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Api
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/platform")
public class ApiPlatformController {
    private final LanguageServiceImpl languageService;
    private final CountryServiceImpl countryService;
    private final CityServiceImpl cityService;

    @GetMapping("/languages")
    public ResponseEntity<ListResponse<LanguageList>> list(ListLanguageRequest request) {
        return ResponseEntity.ok(languageService.list(request));
    }

    @GetMapping("/countries")
    public ResponseEntity<ListResponse<CountryList>> list(ListCountryRequest request) {
        return ResponseEntity.ok(countryService.list(request));
    }

    @GetMapping("/cities")
    public ResponseEntity<ListResponse<CityList>> list(ListCityRequest request) {
        return ResponseEntity.ok(cityService.list(request));
    }
}

