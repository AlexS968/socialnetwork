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
import main.repository.FriendsRepository;
import main.repository.NotificationRepository;
import main.repository.NotificationTypeRepository;
import main.repository.PostCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final PostCommentRepository postCommentRepository;
    private final FriendsRepository friendsRepository;

    private final PersonService personService;
    private PostService postService;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

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
                info = post.getTitle();
                author = post.getAuthor();
                break;
            case FRIEND_REQUEST:
                Friendship friendship = friendsRepository.findById(notification.getEntityId())
                        .orElseThrow(EntityNotFoundException::new);
                info = notification.getContact();
                author = friendship.getSrc();
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

    @Override
    public void setNotification(PostComment postComment) {
        Notification notification = new Notification();
        if (postComment.getParent() != null) {
            //комментарий на комментарий
            notification.setReceiver(postComment.getParent().getAuthor());
            notification.setEntityId(postComment.getId());
            notification.setType(getNotificationType(NotificationTypeCode.COMMENT_COMMENT));
        } else {
            //комментарий на пост
            notification.setReceiver(postComment.getPost().getAuthor());
            notification.setEntityId(postComment.getId());
            notification.setType(getNotificationType(NotificationTypeCode.POST_COMMENT));
        }
        notificationRepository.save(notification);
    }

    @Override
    public void setNotification(Friendship friendship, String status) {
        Notification notification = new Notification();
        notification.setReceiver(friendship.getDst());
        notification.setEntityId(friendship.getId());
        notification.setType(getNotificationType(NotificationTypeCode.FRIEND_REQUEST));
        notification.setContact(status);
        notificationRepository.save(notification);
    }

    @Override
    public void setNotification(Post post) {
        int authorId = post.getAuthor().getId();
        List<Notification> notifications = new ArrayList<>();

        friendsRepository.findByDst_IdAndStatusId(authorId, 2)
                .forEach(friendship -> {
                    Notification notification = new Notification();
                    notification.setReceiver(friendship.getSrc());
                    notification.setEntityId(post.getId());
                    notification.setType(getNotificationType(NotificationTypeCode.POST));
                    notifications.add(notification);
                });
        notificationRepository.saveAll(notifications);
    }

    private NotificationType getNotificationType(NotificationTypeCode notificationTypeCode) {
        return notificationTypeRepository.findByCode(notificationTypeCode.toString())
                .orElseThrow(EntityNotFoundException::new);
    }
}
