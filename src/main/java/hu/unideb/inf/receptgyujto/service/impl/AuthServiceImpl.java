package hu.unideb.inf.receptgyujto.service.impl;

import hu.unideb.inf.receptgyujto.data.entity.FelhasznaloEntity;
import hu.unideb.inf.receptgyujto.data.entity.JogosultsagEntity;
import hu.unideb.inf.receptgyujto.data.repository.FelhasznaloRepository;
import hu.unideb.inf.receptgyujto.data.repository.JogosultsagRepository;
import hu.unideb.inf.receptgyujto.service.AuthService;
import hu.unideb.inf.receptgyujto.service.dto.BejelentkezesDto;
import hu.unideb.inf.receptgyujto.service.dto.RegisztracioDto;
import hu.unideb.inf.receptgyujto.service.mapper.UserMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final JogosultsagRepository jogRepo;
    private final FelhasznaloRepository felhRepo;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserMapper mapper, PasswordEncoder passwordEncoder,
                           JogosultsagRepository jogRepo, FelhasznaloRepository felhRepo, AuthenticationManager authenticationManager) {
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.jogRepo = jogRepo;
        this.felhRepo = felhRepo;
        this.authenticationManager = authenticationManager;
    }
    @Override
    public void regisztracio(RegisztracioDto dto) {
        FelhasznaloEntity e = mapper.dtoToEntity(dto);
        e.setJelszo(passwordEncoder.encode(e.getJelszo()));

        JogosultsagEntity jog = jogRepo.findByNev("FELHASZNALO");
        if(jog != null){
            e.setJogosultsagok(List.of(jog));
        } else {
            jog = new JogosultsagEntity();
            jog.setNev("FELHASZNALO");
            jog = jogRepo.save(jog);

            e.setJogosultsagok(List.of(jog));
        }
        felhRepo.save(e);
    }

    @Override
    public void bejelentkezes(BejelentkezesDto dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getFelhasznalonev(), dto.getJelszo()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
