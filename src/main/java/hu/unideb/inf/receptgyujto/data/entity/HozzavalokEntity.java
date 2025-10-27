package hu.unideb.inf.receptgyujto.data.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ingredients")
public class HozzavalokEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String nev;
    @Column(name = "quantity")
    private Double mennyiseg;
    @Column(name = "unit")
    private String mertekegyseg;
    @JoinColumn(name = "recipe_id")
    @ManyToOne
    private ReceptEntity recept;
}
