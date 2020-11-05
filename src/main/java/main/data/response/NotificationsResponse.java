package main.data.response;

import lombok.Data;

import java.util.Set;

@Data
public class NotificationsResponse {
    Set<NotificationSettingsResponse> notifications;
}
