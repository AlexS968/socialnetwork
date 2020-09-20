package main.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tag")
@Data
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String tag;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "post2tag",
            joinColumns = {@JoinColumn(name = "tag_id")},
            inverseJoinColumns = {@JoinColumn(name = "post_id")})
    private List<Post> postsByTag;
}
