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

    @Column(nullable = false)
    private int statusId;

    @Column(nullable = false)
    private int srcPersonId;

    @Column(nullable = false)
    private int dstPersonId;
}
