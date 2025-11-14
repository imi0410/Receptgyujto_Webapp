package hu.unideb.inf.receptgyujto.service.impl;

import hu.unideb.inf.receptgyujto.data.entity.FelhasznaloEntity;
import hu.unideb.inf.receptgyujto.data.entity.HozzavalokEntity;
import hu.unideb.inf.receptgyujto.data.entity.ReceptEntity;
import hu.unideb.inf.receptgyujto.data.repository.FelhasznaloRepository;
import hu.unideb.inf.receptgyujto.data.repository.ReceptRepository;
import hu.unideb.inf.receptgyujto.service.ReceptService;
import hu.unideb.inf.receptgyujto.service.dto.ReceptDto;
import hu.unideb.inf.receptgyujto.service.mapper.HozzavalokMapper;
import hu.unideb.inf.receptgyujto.service.mapper.ReceptMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceptServiceImpl implements ReceptService {
    final ReceptRepository repo;
    final ReceptMapper receptMapper;
    final HozzavalokMapper hozzavalokMapper;
    final FelhasznaloRepository felhasznaloRepository;

    public ReceptServiceImpl(ReceptRepository repo, ReceptMapper receptMapper, HozzavalokMapper hozzavalokMapper,
                             FelhasznaloRepository felhasznaloRepository) {
        this.repo = repo;
        this.receptMapper = receptMapper;
        this.hozzavalokMapper = hozzavalokMapper;
        this.felhasznaloRepository = felhasznaloRepository;
    }

    @Override
    public ReceptDto findById(Long id) {
       return receptMapper.receptEntityToDto(repo.getReferenceById(id));
    }

    @Override
    public ReceptDto findByName(String name) {
        return receptMapper.receptEntityToDto(repo.getByNev(name));
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
    @Modifying
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<ReceptDto> findByUserId(Long id) {
        return receptMapper.receptEntitiesToDtos(repo.findByFelhasznaloId(id));
    }

    @Override
    @Transactional
    public ReceptDto save(ReceptDto receptDto) {
        if(receptDto.getId() == null) {
            ReceptEntity receptEntity = receptMapper.receptDtoToEntity(receptDto);
            if (receptEntity.getHozzavalok() != null) {
                ReceptEntity finalReceptEntity = receptEntity;
                receptEntity.getHozzavalok().forEach(hozzavalok -> {
                    hozzavalok.setRecept(finalReceptEntity);
                });
            }
            if (receptDto.getFelhasznaloId() != null) {
                FelhasznaloEntity felhasznaloEntity = felhasznaloRepository.findById(receptDto.getFelhasznaloId()).orElseThrow(() -> new EntityNotFoundException("EntityNotFoundError"));
                receptEntity.setFelhasznalo(felhasznaloEntity);
            }
            receptEntity = repo.save(receptEntity);
            receptDto = receptMapper.receptEntityToDto(receptEntity);
            return receptDto;
        }else{
             ReceptEntity receptEntity = repo.findById(receptDto.getId()).orElseThrow(()-> new EntityNotFoundException("EntityNotFoundError"));
             receptEntity.setNev(receptDto.getNev());
             receptEntity.setLeiras(receptDto.getLeiras());
            if (receptDto.getFelhasznaloId() != null) {
                FelhasznaloEntity felhasznaloEntity = felhasznaloRepository.findById(receptDto.getFelhasznaloId()).orElseThrow(() -> new EntityNotFoundException("EntityNotFoundError"));
                receptEntity.setFelhasznalo(felhasznaloEntity);
            }
            if (receptDto.getHozzavalok() != null) {
                receptEntity.getHozzavalok().clear();
                List<HozzavalokEntity> ujHozzavalok = hozzavalokMapper.hozzavalokDtosToEntities(receptDto.getHozzavalok());
                ReceptEntity finalReceptEntity = receptEntity;
                ujHozzavalok.forEach(h -> h.setRecept(finalReceptEntity));
                receptEntity.getHozzavalok().addAll(ujHozzavalok);
            }

             receptEntity = repo.save(receptEntity);
             return receptMapper.receptEntityToDto(receptEntity);
            }
        }
    }

