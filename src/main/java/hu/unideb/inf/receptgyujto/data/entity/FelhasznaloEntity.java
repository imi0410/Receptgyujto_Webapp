package hu.unideb.inf.receptgyujto.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class FelhasznaloEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name", nullable = false)
    private String nev;
    @Column(name = "date_of_birth", nullable = false)
    private Date szuletesiDatum;
    @Column(name = "sex")
    private String nem;
    @Column(name = "username", unique = true, nullable = false)
    private String felhasznalonev;
    @Column(name = "password", nullable = false)
    private String jelszo;
    @OneToMany(mappedBy = "felhasznalo", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ReceptEntity> receptek;

    @ManyToMany
    List<JogosultsagEntity> jogosultsagok;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return jogosultsagok;
    }

    @Override
    public String getPassword() {
        return jelszo;
    }

    @Override
    public String getUsername() {
        return felhasznalonev;
    }
}