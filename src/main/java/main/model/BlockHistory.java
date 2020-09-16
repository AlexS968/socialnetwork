package main.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "block_history")
@Data
public class BlockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private Instant time;

    @Column(nullable = false)
    private int personId;

    private int postId;

    private int commentId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('BLOCK', 'UNBLOCK')", nullable = false)
    private ActionType action;
}
