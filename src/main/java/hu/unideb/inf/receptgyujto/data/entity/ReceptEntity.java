package hu.unideb.inf.receptgyujto.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "recipes")
public class ReceptEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String nev;
    @Column(name = "description")
    private String leiras;
    @JoinColumn(name = "user_id")
    @ManyToOne
    private FelhasznaloEntity felhasznalo;
}
