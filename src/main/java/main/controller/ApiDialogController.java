package main.controller;

import lombok.AllArgsConstructor;
import main.data.request.DialogAddRequest;
import main.data.request.DialogMessageRequest;
import main.data.request.ListRequest;
import main.data.response.base.ListResponse;
import main.data.response.base.Response;
import main.data.response.type.*;
import main.service.DialogServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/dialogs")
public class ApiDialogController {
    private final DialogServiceImpl dialogService;

    @GetMapping
    public ResponseEntity<ListResponse<DialogList>> list(ListRequest request) {
        return ResponseEntity.ok(dialogService.list(request));
    }

    @PostMapping
    public ResponseEntity<Response<DialogNew>> add(@RequestBody DialogAddRequest request) {
        return ResponseEntity.ok(dialogService.add(request));
    }

    @PostMapping("/{dialogId}/messages")
    public ResponseEntity<Response<DialogMessage>> addMessage(
            @PathVariable int dialogId,
            @RequestBody DialogMessageRequest request
    ) {
        return ResponseEntity.ok(dialogService.addMessage(dialogId, request));
    }

    @GetMapping("/{dialogId}/messages")
    public ResponseEntity<ListResponse<DialogMessage>> listMessage(
            @PathVariable int dialogId,
            ListRequest request
    ) {
        return ResponseEntity.ok(dialogService.listMessage(dialogId, request));
    }

    @GetMapping("/unreaded")
    public ResponseEntity<Response<ResponseCount>> countUnreaded() {
        return ResponseEntity.ok(dialogService.countUnreadedMessage());
    }

    @PutMapping("/{dialogId}/messages/{messageId}/read")
    public ResponseEntity<Response<ResponseMessage>> setReadMessage(
            @PathVariable int dialogId,
            @PathVariable int messageId
    ) {
        return ResponseEntity.ok(dialogService.setReadMessage(messageId));
    }
}
