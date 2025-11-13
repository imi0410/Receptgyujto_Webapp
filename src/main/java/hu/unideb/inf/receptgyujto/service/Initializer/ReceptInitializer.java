package hu.unideb.inf.receptgyujto.service.Initializer;

import hu.unideb.inf.receptgyujto.data.entity.HozzavalokEntity;
import hu.unideb.inf.receptgyujto.data.entity.ReceptEntity;
import hu.unideb.inf.receptgyujto.data.repository.HozzavalokRepository;
import hu.unideb.inf.receptgyujto.data.repository.ReceptRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReceptInitializer implements CommandLineRunner {
    final ReceptRepository receptRepository;
    final HozzavalokRepository hozzavalokRepository;

    public ReceptInitializer(ReceptRepository receptRepository, HozzavalokRepository hozzavalokRepository) {
        this.receptRepository = receptRepository;
        this.hozzavalokRepository = hozzavalokRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        ReceptEntity receptEntity = new ReceptEntity();
        receptEntity.setNev("Tükörtojás");
        receptEntity.setLeiras("......");
        HozzavalokEntity hozzavalo1 = new HozzavalokEntity();
        hozzavalo1.setNev("tojás");
        hozzavalo1.setMennyiseg(1.0);
        hozzavalo1.setMertekegyseg("db");
        hozzavalo1.setRecept(receptEntity);
        HozzavalokEntity hozzavalo2 = new HozzavalokEntity();
        hozzavalo2.setNev("só");
        hozzavalo2.setMennyiseg(5.0);
        hozzavalo2.setMertekegyseg("g");
        hozzavalo2.setRecept(receptEntity);
        receptEntity.setHozzavalok(List.of(hozzavalo1,hozzavalo2));
        receptRepository.save(receptEntity);
    }
}
