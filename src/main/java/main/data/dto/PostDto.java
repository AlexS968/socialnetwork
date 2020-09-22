package main.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import main.model.MessagesPermission;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class PostDto {
    private long id;
    private long time;
    private PersonDto author;
    private String title;
    @JsonProperty(value = "post_text")
    private String postText;
    @JsonProperty(value = "is_blocked")
    private boolean blocked;
    private long likes;
    private List<CommentDto> comments;

    public PostDto(){
//        id = 1;
//        time = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
//        time = Instant.now();
//
//        author = new PersonDto();
//        author.setId(1);
//        author.setFirstName("Петр");
//        author.setLastName("Петрович");
//        author.setRegDate(Instant.now(););
//        author.setBirthDate(Instant.now(););
//        author.setEmail("dsf@dfsa.ru");
//        author.setPhone("815323213");
//        author.setPhoto("/static/img/user/1.jpg");
//        author.setAbout("Посадил дерево");
//        author.setMessagesPermission(MessagesPermission.ALL);
//        author.setLastOnlineTime(Instant.now(););
//        author.setIsBlocked(false);
//
//        title = "Первый пост";
//        postText = "Текст поста";
//        likes = 20;
//        comments = new ArrayList<>();
//        comments.add(new CommentDto());

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public PersonDto getAuthor() {
        return author;
    }

    public void setAuthor(PersonDto author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }
}
