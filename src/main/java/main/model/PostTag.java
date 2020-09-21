package main.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "post2tag")
@Data
public class PostTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "post_id", nullable = false)
    private int postId;

    @Column(name = "tag_id", nullable = false)
    private int tagId;
}
