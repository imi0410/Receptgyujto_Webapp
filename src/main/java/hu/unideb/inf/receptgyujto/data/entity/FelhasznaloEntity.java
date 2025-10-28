package hu.unideb.inf.receptgyujto.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class FelhasznaloEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name", nullable = false)
    private String nev;
    @Column(name = "date_of_birth", nullable = false)
    private Date szuletesiDatum;
    @Column(name = "sex")
    private String nem;
    @Column(name = "username", unique = true)
    private String felhasznalonev;
    @Column(name = "password")
    private String jelszo;
    @OneToMany(mappedBy = "felhasznalo", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ReceptEntity> receptEntities;
}