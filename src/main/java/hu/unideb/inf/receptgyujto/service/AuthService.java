package hu.unideb.inf.receptgyujto.service;

import hu.unideb.inf.receptgyujto.service.dto.BejelentkezesDto;
import hu.unideb.inf.receptgyujto.service.dto.RegisztracioDto;

public interface AuthService{
    public void bejelentkezes(BejelentkezesDto bejelentkezesDto);
    public void regisztracio(RegisztracioDto regisztracioDto);
}
