package hu.unideb.inf.receptgyujto.data.repository;

import hu.unideb.inf.receptgyujto.data.entity.JogosultsagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogosultsagRepository extends JpaRepository<JogosultsagEntity,Long> {
    JogosultsagEntity findByNev(String name);
}
