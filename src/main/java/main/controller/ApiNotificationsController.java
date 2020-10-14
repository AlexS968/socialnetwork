package main.controller;

import lombok.RequiredArgsConstructor;
import main.data.response.base.ListResponse;
import main.data.response.type.NotificationResponse;
import main.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class ApiNotificationsController {
    private final NotificationService notificationService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ListResponse<NotificationResponse>> getListOfNotifications(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "20") int itemPerPage) {
        return ResponseEntity.ok(notificationService.list(offset, itemPerPage,false));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<ListResponse<NotificationResponse>> readNotification(
            @RequestParam(required = false, defaultValue = "0") int id,
            @RequestParam boolean all) {
        return ResponseEntity.ok(notificationService.read(id, all));
    }
}
