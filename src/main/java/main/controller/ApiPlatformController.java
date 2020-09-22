package main.controller;

import lombok.AllArgsConstructor;
import main.data.request.ListCityRequest;
import main.data.request.ListCountryRequest;
import main.data.request.ListLanguageRequest;
import main.data.response.ListCityResponse;
import main.data.response.ListCountryResponse;
import main.data.response.ListLanguageResponse;
import main.service.CityServiceImpl;
import main.service.CountryServiceImpl;
import main.service.LanguageServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/platform")
public class ApiPlatformController {
    private final LanguageServiceImpl languageService;
    private final CountryServiceImpl countryService;
    private final CityServiceImpl cityService;

    @GetMapping("/languages")
    public ResponseEntity<ListLanguageResponse> list(ListLanguageRequest request) {
        return ResponseEntity.ok(languageService.list(request));
    }

    @GetMapping("/countries")
    public ResponseEntity<ListCountryResponse> list(ListCountryRequest request) {
        return ResponseEntity.ok(countryService.list(request));
    }

    @GetMapping("/cities")
    public ResponseEntity<ListCityResponse> list(ListCityRequest request) {
        return ResponseEntity.ok(cityService.list(request));
    }
}

