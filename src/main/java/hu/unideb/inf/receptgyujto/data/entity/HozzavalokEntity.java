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
    @Column(name = "name", nullable = false)
    private String nev;
    @Column(name = "quantity", nullable = false)
    private Double mennyiseg;
    @Column(name = "unit", nullable = false)
    private String mertekegyseg;
    @JoinColumn(name = "recipe_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private ReceptEntity recept;
}
