package hu.unideb.inf.receptgyujto.service.impl;

import hu.unideb.inf.receptgyujto.data.entity.FelhasznaloEntity;
import hu.unideb.inf.receptgyujto.data.entity.ReceptEntity;
import hu.unideb.inf.receptgyujto.data.repository.FelhasznaloRepository;
import hu.unideb.inf.receptgyujto.data.repository.ReceptRepository;
import hu.unideb.inf.receptgyujto.service.ReceptService;
import hu.unideb.inf.receptgyujto.service.dto.ReceptDto;
import hu.unideb.inf.receptgyujto.service.mapper.ReceptMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceptServiceImpl implements ReceptService {
    final ReceptRepository repo;
    final ModelMapper mapper;
    final ReceptMapper receptMapper;
    private final FelhasznaloRepository felhasznaloRepository;

    public ReceptServiceImpl(ReceptRepository repo, ModelMapper mapper, ReceptMapper receptMapper,
                             FelhasznaloRepository felhasznaloRepository) {
        this.repo = repo;
        this.mapper = mapper;
        this.receptMapper = receptMapper;
        this.felhasznaloRepository = felhasznaloRepository;
    }

    @Override
    public ReceptDto findById(Long id) {
       return mapper.map(repo.getReferenceById(id),ReceptDto.class);
    }

    @Override
    public ReceptDto findByName(String name) {
        return mapper.map(repo.getByNev(name),ReceptDto.class);
    }

    @Override
    public List<ReceptDto> findAll() {
        return receptMapper.receptEntitiesToDtos(repo.findAll());
    }

    @Override
    @Modifying
    @Transactional
    public void deleteByName(String name) {
        repo.deleteByNev(name);
        repo.flush();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    @Transactional
    public ReceptDto save(ReceptDto receptDto) {
        if(receptDto.getId() == null) {
            ReceptEntity receptEntity = mapper.map(receptDto, ReceptEntity.class);
            receptEntity = repo.save(receptEntity);
            receptDto = mapper.map(receptEntity, ReceptDto.class);
            return receptDto;
        }else{
             ReceptEntity receptEntity = repo.findById(receptDto.getId()).orElseThrow(()-> new EntityNotFoundException("EntityNotFoundError"));
             receptEntity.setNev(receptDto.getNev());
             receptEntity.setLeiras(receptDto.getLeiras());
            if (receptDto.getFelhasznaloId() != null) {
                FelhasznaloEntity felhasznaloEntity = felhasznaloRepository.findById(receptDto.getFelhasznaloId()).orElseThrow(() -> new EntityNotFoundException("EntityNotFoundError"));
                receptEntity.setFelhasznalo(felhasznaloEntity);
            }

             receptEntity = repo.save(receptEntity);
             return receptMapper.receptEntityToDto(receptEntity);
            }
        }
    }

