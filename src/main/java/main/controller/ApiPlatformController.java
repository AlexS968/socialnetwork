package main.controller;

import lombok.AllArgsConstructor;
import main.data.request.ListLanguageRequest;
import main.data.response.ListLanguageResponse;
import main.service.LanguageServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/platform")
public class ApiPlatformController {
    private final LanguageServiceImpl languageService;

    @GetMapping("/languages")
    public ResponseEntity<ListLanguageResponse> create(ListLanguageRequest request) {
        return ResponseEntity.ok(languageService.list(request));
    }
}

