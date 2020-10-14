package main.service;

import main.data.response.base.ListResponse;
import main.data.response.type.NotificationResponse;

public interface NotificationService {

    ListResponse<NotificationResponse> list(int offset, int itemPerPage, boolean needToRead);

    ListResponse<NotificationResponse> read(int id, boolean all);
}
