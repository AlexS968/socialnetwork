package main.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "person")
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Instant regDate;

    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "e_mail", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String passwordHash;

    @Column(name = "photo", columnDefinition = "text")
    private String photoURL;

    @Column(columnDefinition = "text")
    private String about;

    @Column
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Column
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "confirmation_code")
    private String confirmationCode;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean isApproved = false;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('ALL', 'FRIENDS')", nullable = false)
    private MessagesPermission messagesPermission;

    @Column
    private Instant lastOnlineTime;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean isBlocked = false;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private List<BlockHistory> blockHistory;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Post> posts;

    @OneToMany(mappedBy = "receiver")
    private List<Notification> notifications;

    @OneToMany(mappedBy = "dst")
    private Set<Friendship> requestFr;

    @OneToMany(mappedBy = "src")
    private Set<Friendship> sendFr;
}
