package hu.unideb.inf.receptgyujto.controller;

import hu.unideb.inf.receptgyujto.service.AuthService;
import hu.unideb.inf.receptgyujto.service.dto.BejelentkezesDto;
import hu.unideb.inf.receptgyujto.service.dto.RegisztracioDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<Map<String, Long>> bejelentkezes(@RequestBody BejelentkezesDto bejelentkezesDto, HttpServletRequest httpServletRequest) {
        Long id = authService.bejelentkezes(bejelentkezesDto);
        if(id != null) {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            HttpSession httpSession = httpServletRequest.getSession(true);
            httpSession.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            Map<String, Long> responseBody = new HashMap<>();
            responseBody.put("id", id);
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}