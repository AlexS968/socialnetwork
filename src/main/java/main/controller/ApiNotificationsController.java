package main.controller;

import lombok.RequiredArgsConstructor;
import main.data.request.NotificationSettingsRequest;
import main.data.response.base.ListResponse;
import main.data.response.base.Response;
import main.data.response.type.InfoInResponse;
import main.data.response.type.NotificationResponse;
import main.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ApiNotificationsController {

  private final NotificationService notificationService;

  @GetMapping("/notifications")
  public ResponseEntity<ListResponse<NotificationResponse>> getListOfNotifications(
      @RequestParam(required = false, defaultValue = "0") int offset,
      @RequestParam(required = false, defaultValue = "20") int itemPerPage) {
    return ResponseEntity.ok(notificationService.list(offset, itemPerPage, false));
  }

  @PutMapping("/notifications")
  public ResponseEntity<ListResponse<NotificationResponse>> readNotification(
      @RequestParam(required = false, defaultValue = "0") int id,
      @RequestParam boolean all) {
    return ResponseEntity.ok(notificationService.read(id, all));
  }

  @PutMapping("/account/notifications")
  public ResponseEntity<Response<InfoInResponse>> setNotifications(
      @RequestBody NotificationSettingsRequest request) {

    return ResponseEntity.ok(notificationService.set(request));
  }


}
