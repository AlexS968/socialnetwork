package main.controller;

import lombok.AllArgsConstructor;
import main.data.request.ListDialogRequest;
import main.data.response.ListDialogResponse;
import main.data.response.ListLanguageResponse;
import main.service.CityServiceImpl;
import main.service.DialogServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/dialogs")
public class ApiDialogController {
    private final DialogServiceImpl dialogService;

    @GetMapping
    public ResponseEntity<ListDialogResponse> list(ListDialogRequest request) {
        return ResponseEntity.ok(dialogService.list(request));
    }
}
