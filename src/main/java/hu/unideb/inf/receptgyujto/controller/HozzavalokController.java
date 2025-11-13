package hu.unideb.inf.receptgyujto.controller;

import hu.unideb.inf.receptgyujto.service.HozzavalokService;
import hu.unideb.inf.receptgyujto.service.dto.HozzavalokDto;
import hu.unideb.inf.receptgyujto.service.dto.ReceptDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ingredients")
public class HozzavalokController {

    final HozzavalokService hozzavalokService;

    public HozzavalokController(HozzavalokService hozzavalokService) {
        this.hozzavalokService = hozzavalokService;
    }


    @GetMapping("/byId")
    HozzavalokDto findById(@RequestParam Long id){
        return hozzavalokService.findById(id);
    }

    @GetMapping("/byName/{name}")
    HozzavalokDto findByName(@PathVariable String name){
        return hozzavalokService.findByName(name);
    }

    @GetMapping("/{hozzavalokId}/recipe")
    ReceptDto findReceptByHozzavalokId(@PathVariable Long hozzavalokId){
        return hozzavalokService.findReceptByHozzavalokId(hozzavalokId);
    }

    @GetMapping
    List<HozzavalokDto> findAll(){return hozzavalokService.findAll();}

    @PostMapping("/save")
    HozzavalokDto save(@RequestBody HozzavalokDto hozzavalokDto){
        return hozzavalokService.save(hozzavalokDto);
    }

    @DeleteMapping("/deleteById")
    void delById(@RequestParam Long id){
        hozzavalokService.deleteById(id);
    }

    @DeleteMapping("/deleteByName")
    void delByName(@RequestParam String name){
        hozzavalokService.deleteByName(name);
    }

    @PostMapping("/update")
    HozzavalokDto update(@RequestBody HozzavalokDto hozzavalokDto){
        return hozzavalokService.save(hozzavalokDto);
    }
}
