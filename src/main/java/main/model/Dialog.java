package main.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "dialog")
@Data
public class Dialog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "dialog2person",
            joinColumns = {@JoinColumn(name = "dialog_id")},
            inverseJoinColumns = {@JoinColumn(name = "person_id")})
    private List<Person> persons;
}
