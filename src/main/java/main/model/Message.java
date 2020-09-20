package main.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "message")
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private Instant time;

    @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person author;

    @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private Person recipient;

    @Column(columnDefinition = "text")
    private String messageText;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('SENT', 'READ')", nullable = false)
    private ReadStatus readStatus;
}
