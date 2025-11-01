package hu.unideb.inf.receptgyujto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("html")
public class HtmlController {

    @GetMapping("/bejelentkezes")
    public String login() {
        return "bejelentkezes";
    }

    @GetMapping("/regisztracio")
    public String register(){
        return "regisztracio";
    }

    @GetMapping("/receptfo")
    public String toReceptfoPage(){
        return "receptfo";
    }
}
