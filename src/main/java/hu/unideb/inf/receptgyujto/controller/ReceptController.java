package hu.unideb.inf.receptgyujto.controller;

import hu.unideb.inf.receptgyujto.service.ReceptService;
import hu.unideb.inf.receptgyujto.service.dto.ReceptDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/recipe")
public class ReceptController {

    final ReceptService receptService;

    public ReceptController(ReceptService receptService) {
        this.receptService = receptService;
    }

    void init(){
        ReceptDto receptDto = new ReceptDto();
        receptDto.setNev("vmi");
        receptDto.setLeiras(".....");
        receptDto.setFelhasznaloId(null);
        receptService.save(receptDto);
    }
}
