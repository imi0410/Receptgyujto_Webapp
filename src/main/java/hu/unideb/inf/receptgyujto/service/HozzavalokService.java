package hu.unideb.inf.receptgyujto.service;

import hu.unideb.inf.receptgyujto.service.dto.HozzavalokDto;
import hu.unideb.inf.receptgyujto.service.dto.ReceptDto;

import java.util.List;

public interface HozzavalokService {
    HozzavalokDto findById(Long id);
    HozzavalokDto findByName(String name);
    ReceptDto findReceptByHozzavalokId(Long hozzavalokId);
    List<HozzavalokDto> findAll();
    void deleteByName(String name);
    void deleteById(Long id);
    HozzavalokDto save(HozzavalokDto hozzavalokDto);

}
