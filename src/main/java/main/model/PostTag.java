package main.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "post2tag")
@Data
@NoArgsConstructor
public class PostTag {
    @EmbeddedId
    private PostTagId id = new PostTagId();

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    private Post post;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId")
    private Tag tag;

    public PostTag(Post post, Tag tag) {
        this.post = post;
        this.tag = tag;
    }
}