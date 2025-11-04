package hu.unideb.inf.receptgyujto.data.repository;

import hu.unideb.inf.receptgyujto.data.entity.ReceptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceptRepository extends JpaRepository<ReceptEntity,Long> {
    ReceptEntity getByNev(String nev);
    void deleteByNev(String nev);
    List<ReceptEntity> findByFelhasznaloId(Long id);
}
