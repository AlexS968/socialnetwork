package main.service;

import lombok.RequiredArgsConstructor;
import main.core.OffsetPageRequest;
import main.data.PersonPrincipal;
import main.data.response.base.ListResponse;
import main.data.response.type.NotificationResponse;
import main.exception.BadRequestException;
import main.exception.apierror.ApiError;
import main.model.Notification;
import main.model.NotificationReadStatusCode;
import main.model.Person;
import main.model.Post;
import main.repository.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final PostService postService;

    @Override
    public ListResponse<NotificationResponse> list(int offset, int itemPerPage, boolean needToRead) {
        Person person = ((PersonPrincipal) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal()).getPerson();

        List<NotificationResponse> notificationResponses = new ArrayList<>();

        Pageable pageable;
        Page<Notification> notifications;

        if (itemPerPage > 0) {
            pageable = new OffsetPageRequest(offset, itemPerPage, Sort.unsorted());
        } else {
            pageable = Pageable.unpaged();
        }

        try {
            notifications = notificationRepository.findByReceiver(person, pageable);
        } catch (BadRequestException ex) {
            throw new BadRequestException(new ApiError("invalid_request", "Bad request"));
        }

        if (needToRead) {
            notifications.forEach(notification ->
                notification.setReadStatus(NotificationReadStatusCode.READ)
            );
            notificationRepository.saveAll(notifications);
        }

        notifications.forEach(i -> notificationResponses.add(convertToDto(i)));

        return new ListResponse<>(
                notificationResponses,
                notifications.getTotalElements(),
                offset,
                itemPerPage
        );
    }

    @Override
    public ListResponse<NotificationResponse> read(int id, boolean all) {
        if (all) {
            return list(0, 20, true);
        } else {
            Notification notification = notificationRepository.findById(id).orElseThrow(
                    () -> new BadRequestException(new ApiError(
                            "invalid request",
                            "post is not found")));
            List<NotificationResponse> notificationResponses = new ArrayList<>();
            NotificationResponse notificationResponse = convertToDto(notification);
            notificationResponses.add(notificationResponse);
            return new ListResponse<>(notificationResponses, 1, 0, 20);
        }
    }

    private NotificationResponse convertToDto(Notification notification) {
        String info;
        Person author;
        switch (notification.getType().getCode()) {
            case POST_COMMENT:
                info = "POST_COMMENT";
                author = null;
                break;
            case COMMENT_COMMENT:
                info = "COMMENT_COMMENT";
                author = null;
                break;
            case MESSAGE:
                info = "MESSAGE";
                author = null;
                break;
            case POST:
                Post post = postService.findById(notification.getEntityId());
                info = post.getPostText();
                author = post.getAuthor();
                break;
            case FRIEND_REQUEST:
                info = "FRIEND_REQUEST";
                author = null;
                break;
            default:
                info = "";
                author = null;
        }
        return new NotificationResponse(notification, info, author);
    }
}
