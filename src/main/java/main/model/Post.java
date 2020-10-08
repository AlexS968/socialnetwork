package main.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "post")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private Instant time;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Person author;

    @Column(nullable = false)
    private String title;

    @Column(name = "post_text", columnDefinition = "text")
    private String postText;

    @Column(name = "is_blocked", nullable = false, columnDefinition = "TINYINT DEFAULT false")
    private boolean isBlocked = false;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "post2tag",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<Tag> tags;

    @OneToMany(mappedBy = "post")
    private List<PostLike> likes;

    @OneToMany(mappedBy = "post")
    private List<PostFile> files;
}
