package hu.unideb.inf.receptgyujto.service;

import hu.unideb.inf.receptgyujto.service.dto.ReceptDto;

import java.util.List;

public interface ReceptService {
    ReceptDto findById(Long id);
    ReceptDto findByName(String name);
    List<ReceptDto> findAll();
    void deleteByName(String name);
    ReceptDto save(ReceptDto receptDto);
}
