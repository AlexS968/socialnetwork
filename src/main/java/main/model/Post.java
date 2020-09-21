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

    @Column(nullable = false)
    private int authorId;

    private String title;

    @Column(columnDefinition = "text")
    private String postText;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean isBlocked = false;
}
