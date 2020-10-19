package main.service;

import main.data.response.FriendsResponse;
import main.model.Person;

import java.util.List;

public interface FriendsService {

    FriendsResponse getFriends(String name, int offset, int limit);

    FriendsResponse addFriend(int id);

    FriendsResponse deleteFriend(int id);

    FriendsResponse blockFriend(int id);

    FriendsResponse getRecommendations(int offset, int limit);

    FriendsResponse getRequests(int offset, int limit);
}
