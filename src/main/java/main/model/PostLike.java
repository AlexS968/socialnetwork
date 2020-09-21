package main.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "post_like")
@Data
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int postId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String path;
}
