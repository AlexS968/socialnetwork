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

    private String town;

    private String confirmationCode;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean isApproved = false;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('ALL', 'FRIENDS')", nullable = false)
    private MessagesPermission messagesPermission;

    private Instant lastOnlineTime;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private boolean isBlocked = false;
}
