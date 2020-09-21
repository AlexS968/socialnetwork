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

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private PostComment parent;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Person author;

    @Column(columnDefinition = "text")
    private String commentText;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean isBlocked = false;
}
