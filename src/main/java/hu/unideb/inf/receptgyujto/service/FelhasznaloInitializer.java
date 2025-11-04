package hu.unideb.inf.receptgyujto.service;

import hu.unideb.inf.receptgyujto.data.entity.FelhasznaloEntity;
import hu.unideb.inf.receptgyujto.data.entity.JogosultsagEntity;
import hu.unideb.inf.receptgyujto.data.repository.FelhasznaloRepository;
import hu.unideb.inf.receptgyujto.data.repository.JogosultsagRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class FelhasznaloInitializer implements CommandLineRunner {
    final FelhasznaloRepository felhasznaloRepository;
    final PasswordEncoder passwordEncoder;
    final JogosultsagRepository jogosultsagRepository;

    public FelhasznaloInitializer(FelhasznaloRepository felhasznaloRepository, PasswordEncoder passwordEncoder, JogosultsagRepository jogosultsagRepository) {
        this.felhasznaloRepository = felhasznaloRepository;
        this.passwordEncoder = passwordEncoder;
        this.jogosultsagRepository = jogosultsagRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        FelhasznaloEntity admin = new FelhasznaloEntity();
        JogosultsagEntity adminJog = new JogosultsagEntity();
        adminJog.setNev("ADMIN");
        jogosultsagRepository.save(adminJog);
        admin.setNev("vmi");
        admin.setSzuletesiDatum(new Date("2000/01/01"));
        admin.setNem("ferfi");
        admin.setFelhasznalonev("admin");
        admin.setJelszo(passwordEncoder.encode("admin"));
        admin.setJogosultsagok(List.of(adminJog));
        felhasznaloRepository.save(admin);
    }
}
