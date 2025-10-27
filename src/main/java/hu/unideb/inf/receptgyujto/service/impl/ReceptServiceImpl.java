package hu.unideb.inf.receptgyujto.service.impl;

import hu.unideb.inf.receptgyujto.data.entity.ReceptEntity;
import hu.unideb.inf.receptgyujto.data.repository.ReceptRepository;
import hu.unideb.inf.receptgyujto.service.ReceptService;
import hu.unideb.inf.receptgyujto.service.dto.ReceptDto;
import org.modelmapper.ModelMapper;

import java.util.List;

public class ReceptServiceImpl implements ReceptService {
    final ReceptRepository repo;
    final ModelMapper mapper;

    public ReceptServiceImpl(ReceptRepository repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public ReceptDto findById(Long id) {
        return null;
    }

    @Override
    public ReceptDto findByName(String name) {
        return null;
    }

    @Override
    public List<ReceptDto> findAll() {
        return List.of();
    }

    @Override
    public void deleteByName(String name) {

    }

    @Override
    public ReceptDto save(ReceptDto receptDto) {
        ReceptEntity entity = mapper.map(receptDto,ReceptEntity.class);
        entity = repo.save(entity);
        receptDto = mapper.map(entity,ReceptDto.class);
        return receptDto;
    }
}
