package main.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "post_like")
@Data
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

//    @Column(nullable = false)
    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(nullable = false)
    private Instant time;
}
