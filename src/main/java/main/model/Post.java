package main.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "post")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private Instant time;

    @Column(name = "author_id", nullable = false)
    private int authorId;

    private String title;

    @Column(name = "post_text", columnDefinition = "text")
    private String postText;

    @Column(name = "is_blocked", nullable = false, columnDefinition = "TINYINT")
    private boolean isBlocked = false;
}
