package main.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "person")
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "reg_date", nullable = false)
    private Instant regDate;

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "e_mail", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String passwordHash;

    @Column(name = "phone")
    private String phone;

    @Column(name = "photo", columnDefinition = "text")
    private String photoURL;

    @Column(columnDefinition = "text")
    private String about;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "confirmation_code")
    private String confirmationCode;

    @Column(name = "is_approved", nullable = false, columnDefinition = "TINYINT")
    private boolean isApproved = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "messages_permission", columnDefinition = "enum('ALL', 'FRIENDS')", nullable = false)
    private MessagesPermission messagesPermission;

    @Column(name = "")
    private Instant lastOnlineTime;

    @Column(name = "is_blocked", nullable = false, columnDefinition = "TINYINT")
    private boolean isBlocked = false;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private List<BlockHistory> blockHistory;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Post> posts;

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    private List<Notification> notifications;

    @OneToMany(mappedBy = "dst", fetch = FetchType.LAZY)
    private Set<Friendship> requestFr;

    @OneToMany(mappedBy = "src", fetch = FetchType.LAZY)
    private Set<Friendship> sendFr;
}
