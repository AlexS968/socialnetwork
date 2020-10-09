package main.controller;

import lombok.AllArgsConstructor;
import main.data.request.DialogAddRequest;
import main.data.request.ListDialogRequest;
import main.data.response.ListDialogResponse;
import main.data.response.ListLanguageResponse;
import main.data.response.base.Response;
import main.data.response.type.DialogNew;
import main.service.CityServiceImpl;
import main.service.DialogServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/dialogs")
public class ApiDialogController {
    private final DialogServiceImpl dialogService;

    @GetMapping
    public ResponseEntity<ListDialogResponse> list(ListDialogRequest request) {
        return ResponseEntity.ok(dialogService.list(request));
    }

    @PostMapping
    public ResponseEntity<Response<DialogNew>> add(@RequestBody DialogAddRequest request) {
        return ResponseEntity.ok(dialogService.add(request));
    }
}
