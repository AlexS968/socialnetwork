package main.service;

import lombok.RequiredArgsConstructor;
import main.core.OffsetPageRequest;
import main.data.request.NotificationSettingsRequest;
import main.data.response.base.ListResponse;
import main.data.response.base.Response;
import main.data.response.type.InfoInResponse;
import main.data.response.type.NotificationResponse;
import main.exception.BadRequestException;
import main.exception.apierror.ApiError;
import main.model.*;
import main.repository.NotificationRepository;
import main.repository.NotificationTypeRepository;
import main.repository.PostCommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationTypeRepository notificationTypeRepository;
    private final PersonService personService;
    private final PostService postService;
    private final PostCommentRepository postCommentRepository;

    @Override
    public ListResponse<NotificationResponse> list(int offset, int itemPerPage, boolean needToRead) {
        Person person = personService.getCurrentPerson();

        // получаем для текущего пользователя list с перечнем id типов уведомлений, которые он акцептовал
        List<Integer> types = person.getNotificationSettings().entrySet().stream()
                .filter(Map.Entry::getValue).map(Map.Entry::getKey).collect(Collectors.toList());

        List<NotificationResponse> notificationResponses = new ArrayList<>();

        Pageable pageable;
        Page<Notification> notifications;

        if (itemPerPage > 0) {
            pageable = new OffsetPageRequest(offset, itemPerPage, Sort.unsorted());
        } else {
            pageable = Pageable.unpaged();
        }

        try {
            // делаем выборку только акцептованных текущим пользователем уведомлений
            notifications = notificationRepository
                    .findByReceiverAndType(person.getId(), types, pageable);
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
            case COMMENT_COMMENT:
                PostComment postComment = postCommentRepository.findById(notification
                        .getEntityId()).orElseThrow(EntityNotFoundException::new);
                info = postComment.getCommentText();
                author = postComment.getAuthor();
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

    @Override
    public Response<InfoInResponse> set(NotificationSettingsRequest request) {

        Person receiver = personService.getCurrentPerson();

        boolean isEnabled = request.getEnable();
        int notificationTypeId = notificationTypeRepository
                .findByCode(request.getNotificationType())
                .orElseThrow(EntityNotFoundException::new).getId();

        Map<Integer, Boolean> settings = receiver.getNotificationSettings();
        settings.putIfAbsent(notificationTypeId, isEnabled);
        settings.replace(notificationTypeId, isEnabled);

        personService.save(receiver);

        InfoInResponse info = new InfoInResponse("ok");
        Response<InfoInResponse> response = new Response<InfoInResponse>();
        response.setData(info);
        return response;
    }

    public void setNotification(PostComment postComment) {
        Notification notification = new Notification();
        notification.setSentTime(postComment.getTime());
        notification.setReadStatus(NotificationReadStatusCode.SENT);
        if (postComment.getParent() != null) {
            //комментарий на комментарий
            notification.setReceiver(postComment.getParent().getAuthor());
            notification.setEntityId(postComment.getId());
            NotificationType notificationType = notificationTypeRepository
                    .findByCode(NotificationTypeCode.COMMENT_COMMENT.toString())
                    .orElseThrow(EntityNotFoundException::new);
            notification.setType(notificationType);
        } else {
            //комментарий на пост
            notification.setReceiver(postComment.getPost().getAuthor());
            notification.setEntityId(postComment.getId());
            NotificationType notificationType = notificationTypeRepository
                    .findByCode(NotificationTypeCode.POST_COMMENT.toString())
                    .orElseThrow(EntityNotFoundException::new);
            notification.setType(notificationType);
        }
        notificationRepository.save(notification);
    }
}
