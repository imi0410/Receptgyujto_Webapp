package hu.unideb.inf.receptgyujto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

    @GetMapping("/bejelentkezes")
    public String login() {
        return "bejelentkezes";
    }
}
