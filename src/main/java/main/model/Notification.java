package main.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "notification")
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @OneToOne
    @JoinColumn(name = "type_id")
    private NotificationType type;

    @Column(nullable = false)
    private Instant sentTime;

    @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person receiver;

    @Column(nullable = false)
    private int entityId;

    @Column(nullable = false)
    private String contact;
}
