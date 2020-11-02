package main.service;

import lombok.AllArgsConstructor;
import main.data.PersonPrincipal;
import main.data.response.FriendsResponse;
import main.data.response.type.DataMessage;
import main.model.BlocksBetweenUsers;
import main.model.Friendship;
import main.model.FriendshipStatus;
import main.model.Person;
import main.repository.BlocksBetweenUsersRepository;
import main.repository.FriendsRepository;
import main.repository.FriendshipStatusRepository;
import main.repository.PersonRepository;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class FriendsServiceImpl implements FriendsService {

    private final PersonRepository personRepository;
    private final FriendsRepository friendsRepository;
    private final FriendshipStatusRepository friendshipStatusRepository;
    private final BlocksBetweenUsersRepository blocksBetweenUsersRepository;

    private NotificationService notificationService;

    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public FriendsResponse getFriends(String name, int offset, int limit) {
        int currentUserId = getCurrentUserId();
        Page<Friendship> friends;
        friends = friendsRepository.findByDst_IdAndStatusId(currentUserId, getPage(offset, limit), 2);
        friends.forEach(friendship ->{
            BlocksBetweenUsers blocksBetweenUsers = blocksBetweenUsersRepository
                    .findBySrc_IdAndDst_Id(currentUserId, friendship.getSrc().getId());
            if (!(blocksBetweenUsers == null)) {
                friendship.getSrc().setBlocked(true);
            } else {
                friendship.getSrc().setBlocked(false);
            }
        });
        return new FriendsResponse(friends);
    }

    @Override
    public FriendsResponse addFriend(int id) {
        int currentUserId = getCurrentUserId();
        if (friendsRepository.findBySrc_idAndDst_IdAndStatusId(currentUserId, id, 1) == null
                && !(id == currentUserId)) {
            Friendship friendship = friendsRepository.findByDst_IdAndSrc_IdAndStatusId(currentUserId, id, 1);
            if (friendship == null) {
                friendship = new Friendship();
                friendship.setDst(personRepository.findById(id));
                friendship.setSrc(personRepository.findById(currentUserId));
                friendship.setStatus(friendshipStatusRepository.findById(1));
            } else {
                friendship.setStatus(friendshipStatusRepository.findById(2));
                friendsRepository.save(friendship);
                friendship = new Friendship();
                friendship.setDst(personRepository.findById(id));
                friendship.setSrc(personRepository.findById(currentUserId));
                friendship.setStatus(friendshipStatusRepository.findById(2));
            }
            friendsRepository.save(friendship);
            notificationService.setNotification(friendship);
            FriendsResponse friendsResponse = new FriendsResponse();
            friendsResponse.setError("");
            friendsResponse.setDataMessage(new DataMessage("ok"));
            return friendsResponse;
        }
        FriendsResponse friendsResponse = new FriendsResponse();
        friendsResponse.setError("");
        friendsResponse.setDataMessage(new DataMessage("Заявка уже отправлена"));
        return friendsResponse;
    }

    @Override
    public FriendsResponse deleteFriend(int id) {
        int currentUserId = getCurrentUserId();
        Friendship friendship1 = friendsRepository.findByDst_IdAndSrc_IdAndStatusId(currentUserId, id, 2);
        Friendship friendship2 = friendsRepository.findBySrc_idAndDst_IdAndStatusId(currentUserId, id, 2);
        notificationService.deleteNotification(friendship1,friendship2);
        friendsRepository.delete(friendship1);
        friendsRepository.delete(friendship2);
        FriendsResponse friendsResponse = new FriendsResponse();
        friendsResponse.setError("");
        friendsResponse.setDataMessage(new DataMessage("ok"));
        return friendsResponse;
    }

    @Override
    public FriendsResponse blockFriend(int id) {
        int currentUserId = getCurrentUserId();
        Friendship friendship = friendsRepository.findByDst_IdAndSrc_IdAndStatusId(currentUserId, id, 2);
        friendship.setStatus(friendshipStatusRepository.findById(3));
        friendsRepository.save(friendship);
        friendship = friendsRepository.findBySrc_idAndDst_IdAndStatusId(currentUserId, id, 2);
        friendship.setStatus(friendshipStatusRepository.findById(3));
        friendsRepository.save(friendship);
        FriendsResponse friendsResponse = new FriendsResponse();
        friendsResponse.setError("");
        friendsResponse.setDataMessage(new DataMessage("ok"));
        return friendsResponse;
    }

    @Override
    public FriendsResponse getRecommendations(int offset, int limit) {
        List<Optional<Person>> recommendedFriends = personRepository.findByCityId(getCurrentUserCityId());
        recommendedFriends.remove( Optional.ofNullable(personRepository.findById(getCurrentUserId())));
        return new FriendsResponse(recommendedFriends, offset, limit);
    }

    @Override
    public FriendsResponse getRequests(int offset, int limit) {
        int currentUserId = getCurrentUserId();
        Page<Friendship> requests;
        System.out.println(111111);
        requests = friendsRepository.findByDst_IdAndStatusId(currentUserId, getPage(offset, limit), 1);
        return new FriendsResponse(requests);
    }

    @Override
    public Friendship findById(int id) {
        return friendsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Friendship> findByDst_IdAndStatusId(int dstId, int statusId){
        return friendsRepository.findByDst_IdAndStatusId(dstId, statusId);
    }

    @Override
    public FriendshipStatus findFriendshipStatusById(int id) {
        return friendshipStatusRepository.findById(id);
    }

    private Pageable getPage(int offset, int limit) {
        return PageRequest.of(offset / limit, limit);
    }

    private int getCurrentUserId() {
        return ((PersonPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getPerson().getId();
    }

    private int getCurrentUserCityId() {
        return ((PersonPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getPerson().getCity().getId();
    }
}
