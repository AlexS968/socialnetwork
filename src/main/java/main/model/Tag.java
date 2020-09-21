package main.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tag")
@Data
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String tag;
}
