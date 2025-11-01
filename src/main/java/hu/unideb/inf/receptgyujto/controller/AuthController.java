package hu.unideb.inf.receptgyujto.controller;

import hu.unideb.inf.receptgyujto.service.AuthService;
import hu.unideb.inf.receptgyujto.service.dto.BejelentkezesDto;
import hu.unideb.inf.receptgyujto.service.dto.RegisztracioDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/regisztracio")
    public void regisztracio(@RequestBody RegisztracioDto regisztracioDto) {
        authService.regisztracio(regisztracioDto);
    }

    @PostMapping("/bejelentkezes")
    public void bejelentkezes(@RequestBody BejelentkezesDto bejelentkezesDto) {
        authService.bejelentkezes(bejelentkezesDto);
    }
}