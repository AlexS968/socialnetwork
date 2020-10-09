package main.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "post")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private Instant time;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Person author;

    @Column(nullable = false)
    private String title;

    @Column(name = "post_text", columnDefinition = "text")
    private String postText;

    @Column(name = "is_blocked", nullable = false, columnDefinition = "TINYINT DEFAULT false")
    private boolean isBlocked = false;

    @JsonManagedReference
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostTag> tags;

    @JsonManagedReference
    @OneToMany(mappedBy = "post")
    private List<PostLike> likes;

    @JsonManagedReference
    @OneToMany(mappedBy = "post")
    private List<PostFile> files;
}
