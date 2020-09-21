package main.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "city")
@Data
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "country_id", nullable = false)
    private int countryId;

    @Column(nullable = false)
    private String title;
}
