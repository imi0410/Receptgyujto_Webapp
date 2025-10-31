package hu.unideb.inf.receptgyujto.service.impl;

import hu.unideb.inf.receptgyujto.data.entity.HozzavalokEntity;
import hu.unideb.inf.receptgyujto.data.entity.ReceptEntity;
import hu.unideb.inf.receptgyujto.data.repository.HozzavalokRepository;
import hu.unideb.inf.receptgyujto.data.repository.ReceptRepository;
import hu.unideb.inf.receptgyujto.service.HozzavalokService;
import hu.unideb.inf.receptgyujto.service.dto.HozzavalokDto;
import hu.unideb.inf.receptgyujto.service.dto.ReceptDto;
import hu.unideb.inf.receptgyujto.service.mapper.HozzavalokMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HozzavalokServiceImpl implements HozzavalokService {
    final HozzavalokRepository repo;
    final ModelMapper mapper;
    final HozzavalokMapper hozzavalokMapper;
    private final ReceptRepository receptRepository;

    public HozzavalokServiceImpl(HozzavalokRepository repo, ModelMapper mapper, HozzavalokMapper hozzavalokMapper, ReceptRepository receptRepository) {
        this.repo = repo;
        this.mapper = mapper;
        this.hozzavalokMapper = hozzavalokMapper;
        this.receptRepository = receptRepository;
    }

    @Override
    public HozzavalokDto findById(Long id) {
        return mapper.map(repo.getReferenceById(id),HozzavalokDto.class);
    }

    @Override
    public HozzavalokDto findByName(String name) {
        return mapper.map(repo.getByNev(name),HozzavalokDto.class);
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
        return mapper.map(receptEntity,ReceptDto.class);
    }

    @Override
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
    public HozzavalokDto save(HozzavalokDto hozzavalokDto) {
        if(hozzavalokDto.getId() == null){
            HozzavalokEntity hozzavalokEntity = mapper.map(hozzavalokDto,HozzavalokEntity.class);

            if (hozzavalokDto.getReceptId() != null) {
                ReceptEntity receptEntity = receptRepository.findById(hozzavalokDto.getReceptId())
                        .orElseThrow(() -> new EntityNotFoundException("EntityNotFoundError"));
                hozzavalokEntity.setRecept(receptEntity);}

            hozzavalokEntity = repo.save(hozzavalokEntity);
            hozzavalokDto = mapper.map(hozzavalokEntity,HozzavalokDto.class);
            return hozzavalokDto;
        }else{
            HozzavalokEntity hozzavalokEntity = repo.findById(hozzavalokDto.getId()).orElseThrow(() -> new EntityNotFoundException("EntityNotFoundError"));
            hozzavalokEntity.setNev(hozzavalokDto.getNev());
            hozzavalokEntity.setMennyiseg(hozzavalokDto.getMennyiseg());
            hozzavalokEntity.setMertekegyseg(hozzavalokDto.getMertekegyseg());
            if(hozzavalokDto.getReceptId() != null){
                ReceptEntity receptEntity = receptRepository.findById(hozzavalokDto.getReceptId()).orElseThrow(() -> new EntityNotFoundException("EntityNotFoundError"));
                hozzavalokEntity.setRecept(receptEntity);
            }
            hozzavalokEntity = repo.save(hozzavalokEntity);
            return hozzavalokMapper.hozzavalokEntitytoDto(hozzavalokEntity);
        }
    }
}
