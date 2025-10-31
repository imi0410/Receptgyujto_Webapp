package hu.unideb.inf.receptgyujto.controller;

import hu.unideb.inf.receptgyujto.service.ReceptService;
import hu.unideb.inf.receptgyujto.service.dto.ReceptDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/recipes")
public class ReceptController {

    final ReceptService receptService;

    public ReceptController(ReceptService receptService) {
        this.receptService = receptService;
    }

    @GetMapping("/init")
    void init(){
        ReceptDto receptDto = new ReceptDto();
        receptDto.setNev("palacsinta");
        receptDto.setLeiras(".....valami......");
        receptDto.setFelhasznaloId(null);
        receptService.save(receptDto);
    }

    @GetMapping("/byId")
    ReceptDto findById(@RequestParam Long id){
        return receptService.findById(id);
    }

    @GetMapping("/byName/{name}")
    ReceptDto findByName(@PathVariable String name){
        return receptService.findByName(name);
    }

    @PostMapping("/save")
    ReceptDto save(@RequestBody ReceptDto receptDto){
        return receptService.save(receptDto);
    }

    @PreAuthorize("hasRole('ADMIN') or @securityService.isReceptOwner(#id)")
    @DeleteMapping("/deleteByName")
    void delByName(@RequestParam String name){
        receptService.deleteByName(name);
    }

    @PreAuthorize("hasRole('ADMIN') or @securityService.isReceptOwner(#id)")
    @DeleteMapping("/deleteById")
    void delById(@RequestParam Long id){
        receptService.deleteById(id);
    }

    @PostMapping("/update")
    ReceptDto update(@RequestBody ReceptDto receptDto){
        return receptService.save(receptDto);
    }
}
