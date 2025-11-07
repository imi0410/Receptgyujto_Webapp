package hu.unideb.inf.receptgyujto.controller;

import hu.unideb.inf.receptgyujto.service.ReceptService;
import hu.unideb.inf.receptgyujto.service.dto.ReceptDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/recipes")
public class ReceptController {

    final ReceptService receptService;

    public ReceptController(ReceptService receptService) {
        this.receptService = receptService;
    }

    @GetMapping
    public List<ReceptDto> findAll() {
        return receptService.findAll();
    }

    @GetMapping("/byId")
    ReceptDto findById(@RequestParam Long id){
        return receptService.findById(id);
    }

    @GetMapping("/byName/{name}")
    ReceptDto findByName(@PathVariable String name){
        return receptService.findByName(name);
    }

    @GetMapping("/user/{userId}")
    List<ReceptDto> getRecipesByUser(@PathVariable Long userId) {
        return receptService.findByUserId(userId);
    }

    @PostMapping("/save")
    ReceptDto save(@RequestBody ReceptDto receptDto){
        return receptService.save(receptDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteByName")
    void delByName(@RequestParam String name){
        receptService.deleteByName(name);
    }

    @DeleteMapping("/deleteById")
    void delById(@RequestParam Long id){
        receptService.deleteById(id);
    }

    @PostMapping("/update")
    ReceptDto update(@RequestBody ReceptDto receptDto){
        return receptService.save(receptDto);
    }
}
