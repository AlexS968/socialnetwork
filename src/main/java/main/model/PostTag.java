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

    @Column(nullable = false)
    private int postId;

    @Column(nullable = false)
    private int tagId;
}
