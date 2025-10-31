package hu.unideb.inf.receptgyujto.data.repository;

import hu.unideb.inf.receptgyujto.data.entity.FelhasznaloEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FelhasznaloRepository extends JpaRepository<FelhasznaloEntity,Long> {
    FelhasznaloEntity findByFelhasznalonev(String felhasznalonev);
}
