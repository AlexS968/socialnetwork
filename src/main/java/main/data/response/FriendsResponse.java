package main.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.data.response.base.ListResponse;
import main.data.response.type.DataMessage;
import main.data.response.type.MeProfile;
import main.model.Friendship;
import main.model.Person;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendsResponse extends ListResponse {

    @JsonProperty(value = "data")
    private List<MeProfile> FriendsList;
    private List<MeProfile> RecommendedList;
    private DataMessage dataMessage;

    public FriendsResponse(Page<Friendship> friends, List<Person> recommendedFriends){
        this.setOffset(friends.getNumber() * friends.getNumberOfElements());
        this.setPerPage(friends.getNumberOfElements());
        this.setError("");
        this.setTimestamp(Instant.now().toEpochMilli());
        this.setTotal(friends.getTotalElements());
        FriendsList = new ArrayList<>();
        //System.out.println(3);

        RecommendedList = new ArrayList<>();
        for (Person item : recommendedFriends) {
            RecommendedList.add(new MeProfile(item));
        }

        for (Friendship item : friends.getContent()) {
            FriendsList.add(new MeProfile(item.getSrc()));
        }
    }

    public FriendsResponse(List<Person> recommendedFriends, int offset, int itemPerPage){
        this.setOffset(itemPerPage);
        this.setPerPage(itemPerPage);
        this.setError("");
        this.setTimestamp(Instant.now().toEpochMilli());
        this.setTotal(recommendedFriends.size());
        RecommendedList = new ArrayList<>();
        for (Person item : recommendedFriends) {
            RecommendedList.add(new MeProfile(item));
        }
    }


}
