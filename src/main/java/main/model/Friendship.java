package main.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "friendship")
@Data
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @Column(nullable = false)
    @OneToOne
    @JoinColumn(name = "status_id")
    private FriendshipStatus status;

//    @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "src_person_id")
    private Person src;

//    @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "dst_person_id")
    private Person dst;
}
