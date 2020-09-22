package main.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.data.dto.*;
import main.model.*;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class FeedsResponse {
    private String error;
    private long timestamp;
    private long total;
    private long offset;
    private long perPage;
    @JsonProperty(value = "data")
    private List<PostDto> postsList;

    public FeedsResponse(long offset, long perPage){
        this.offset = offset;
        this.perPage = perPage;

        error = "string";
        timestamp = Instant.now().toEpochMilli();
        total = 15;

        postsList = new ArrayList<>();

        for (int i = 1; i < 15; i++) {
            postsList.add(new PostDto());

        }

    }

    public FeedsResponse(Page<Post> posts, List<PostComment> comments){
        this.offset = posts.getNumber() * posts.getNumberOfElements();
        this.perPage = posts.getNumberOfElements();
        error = "string";
        timestamp = Instant.now().toEpochMilli();
        total = posts.getTotalElements();
        postsList = new ArrayList<>();

        for (Post item : posts.getContent()) {
            postsList.add(postToPostDtoMapper(item));
        }
    }

    private PostDto postToPostDtoMapper(Post post) {
        PostDto postDto = new PostDto();

        postDto.setId(post.getId());
        postDto.setTime(post.getTime().getEpochSecond());
        postDto.setAuthor(personToPersonDtoMapper(post.getAuthor()));
        postDto.setTitle(post.getTitle());
        postDto.setPostText(post.getPostText());
        postDto.setLikes(post.getLikes().size()); //заглушка?

        List<CommentDto> listOfComments = takeCommentDtoList();
        postDto.setComments(listOfComments);

        return postDto;
    }

    private List<CommentDto> takeCommentDtoList(){
        List<CommentDto> list = new ArrayList<>();
        List<PostComment> comments = new ArrayList<>();

        list.add(new CommentDto());

//        for (PostComment item : comments){
//            CommentDto commentDto = commentsToCommentDtoMapper(item);
//            list.add(commentDto);
//        }

        return list;
    }

    private CommentDto commentsToCommentDtoMapper(PostComment comment) {
        //Заглушка
        CommentDto commentDto = new CommentDto();



        return commentDto;
    }

    private PersonDto personToPersonDtoMapper(Person person) {
        PersonDto personDto = new PersonDto();

        personDto.setId(person.getId());
        personDto.setFirstName(person.getFirstName());
        personDto.setLastName(person.getLastName());
        personDto.setRegDate(person.getRegDate().toEpochMilli());
        personDto.setBirthDate(person.getBirthDate().getTime());
        personDto.setEmail(person.getEmail());
        personDto.setPhone(person.getPhone());
        personDto.setPhoto("/static/img/user/1.jpg"); //заглушка
        personDto.setAbout(person.getAbout());
        personDto.setCountry(countryToCountryDto(person.getCountry()));
        personDto.setCity(cityToCityDto(person.getCity()));
        personDto.setMessagesPermission(person.getMessagesPermission());
        personDto.setLastOnlineTime(person.getLastOnlineTime().toEpochMilli());
        personDto.setIsBlocked(false); //заглушка
        return personDto;
    }

    private CountryDto countryToCountryDto(Country country) {
        return new CountryDto(country.getId(), country.getTitle());
    }

    private CityDto cityToCityDto(City city) {
        return new CityDto(city.getId(), city.getTitle());
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public List<PostDto> getPostsList() {
        return postsList;
    }

    public void setPostsList(List<PostDto> postsList) {
        this.postsList = postsList;
    }
}
