package hu.unideb.inf.receptgyujto.service.impl;

import hu.unideb.inf.receptgyujto.data.entity.FelhasznaloEntity;
import hu.unideb.inf.receptgyujto.data.entity.HozzavalokEntity;
import hu.unideb.inf.receptgyujto.data.entity.ReceptEntity;
import hu.unideb.inf.receptgyujto.data.repository.HozzavalokRepository;
import hu.unideb.inf.receptgyujto.data.repository.ReceptRepository;
import hu.unideb.inf.receptgyujto.service.HozzavalokService;
import hu.unideb.inf.receptgyujto.service.dto.HozzavalokDto;
import hu.unideb.inf.receptgyujto.service.dto.ReceptDto;
import hu.unideb.inf.receptgyujto.service.mapper.HozzavalokMapper;
import hu.unideb.inf.receptgyujto.service.mapper.ReceptMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HozzavalokServiceImpl implements HozzavalokService {
    final HozzavalokRepository repo;
    final ReceptMapper receptMapper;
    final HozzavalokMapper hozzavalokMapper;
    private final ReceptRepository receptRepository;

    public HozzavalokServiceImpl(HozzavalokRepository repo, ReceptMapper receptMapper, HozzavalokMapper hozzavalokMapper,
                                 ReceptRepository receptRepository) {
        this.repo = repo;
        this.receptMapper = receptMapper;
        this.hozzavalokMapper = hozzavalokMapper;
        this.receptRepository = receptRepository;
    }

    @Override
    public HozzavalokDto findById(Long id) {
        return hozzavalokMapper.hozzavalokEntitytoDto(repo.getReferenceById(id));
    }

    @Override
    public HozzavalokDto findByName(String name) {
        return hozzavalokMapper.hozzavalokEntitytoDto(repo.getByNev(name));
    }

    @Override
    public List<HozzavalokDto> findAll() {
        return hozzavalokMapper.hozzavalokEntitiesToDtos(repo.findAll());
    }

    @Override
    @Transactional
    public ReceptDto findReceptByHozzavalokId(Long hozzavalokId) {
        HozzavalokEntity hozzavalokEntity = repo.findById(hozzavalokId).orElseThrow(() -> new EntityNotFoundException("EntityNotFoundError"));
        ReceptEntity receptEntity = hozzavalokEntity.getRecept();
        if(receptEntity == null){
            throw new EntityNotFoundException("EntityNotFoundError");
        }
        return receptMapper.receptEntityToDto(receptEntity);
    }

    @Override
    @Transactional
    @Modifying
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
    @Transactional
    @Modifying
    public HozzavalokDto save(HozzavalokDto hozzavalokDto) {
        if(hozzavalokDto.getId() == null){
            HozzavalokEntity hozzavalokEntity = hozzavalokMapper.hozzavalokDtoToEntity(hozzavalokDto);
            if (hozzavalokDto.getReceptId() != null) {
                ReceptEntity receptEntity = receptRepository.findById(hozzavalokDto.getReceptId()).orElseThrow(() -> new EntityNotFoundException("EntityNotFoundError"));
                hozzavalokEntity.setRecept(receptEntity);
            }
            hozzavalokEntity = repo.save(hozzavalokEntity);
            hozzavalokDto = hozzavalokMapper.hozzavalokEntitytoDto(hozzavalokEntity);
            return hozzavalokDto;
        }else{
            HozzavalokEntity hozzavalokEntity = repo.findById(hozzavalokDto.getId()).orElseThrow(() -> new EntityNotFoundException("EntityNotFoundError"));
            hozzavalokEntity.setNev(hozzavalokDto.getNev());
            hozzavalokEntity.setMennyiseg(hozzavalokDto.getMennyiseg());
            hozzavalokEntity.setMertekegyseg(hozzavalokDto.getMertekegyseg());
            hozzavalokEntity = repo.save(hozzavalokEntity);
            return hozzavalokMapper.hozzavalokEntitytoDto(hozzavalokEntity);
        }
    }
}
