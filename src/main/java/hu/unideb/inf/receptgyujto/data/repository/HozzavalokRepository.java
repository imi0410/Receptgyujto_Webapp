package hu.unideb.inf.receptgyujto.data.repository;

import hu.unideb.inf.receptgyujto.data.entity.HozzavalokEntity;
import hu.unideb.inf.receptgyujto.data.entity.ReceptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HozzavalokRepository extends JpaRepository<HozzavalokEntity,Long> {
    ReceptEntity getByNev(String nev);
    void deleteByNev(String nev);
}
