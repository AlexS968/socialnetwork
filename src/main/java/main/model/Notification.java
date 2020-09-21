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
    private int typeId;

    @Column(nullable = false)
    private Instant sentTime;

    @Column(nullable = false)
    private int personId;

    private int entityId;

    @Column(nullable = false)
    private String contact;
}
