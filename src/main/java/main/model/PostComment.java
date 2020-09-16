package main.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "post_comment")
@Data
public class PostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private Instant time;

    @Column(nullable = false)
    private int postId;

    private int parentId;

    @Column(nullable = false)
    private int authorId;

    @Column(columnDefinition = "text")
    private String commentText;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean isBlocked = false;
}
