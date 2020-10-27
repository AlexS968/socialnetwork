package main.service;

import main.data.request.NotificationSettingsRequest;
import main.data.response.base.ListResponse;
import main.data.response.base.Response;
import main.data.response.type.InfoInResponse;
import main.data.response.type.NotificationResponse;
import main.model.*;

public interface NotificationService {

    ListResponse<NotificationResponse> list(int offset, int itemPerPage, boolean needToRead);

    ListResponse<NotificationResponse> read(int id, boolean all);

    Response<InfoInResponse> set(NotificationSettingsRequest request);

    void setNotification(PostComment postComment);

    void setNotification(Friendship friendship);

    void setNotification(Post post);

    void setNotification(Message message);

    void setNotification(Like like);
}
