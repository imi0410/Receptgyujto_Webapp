package hu.unideb.inf.receptgyujto.service.mapper;

import hu.unideb.inf.receptgyujto.data.entity.ReceptEntity;
import hu.unideb.inf.receptgyujto.service.dto.ReceptDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReceptMapper {
    ReceptDto receptEntityToDto(ReceptEntity e);
    List<ReceptDto> receptEntitiesToDtos(List<ReceptEntity> l);

    ReceptEntity receptDtoToEntity(ReceptDto d);
    List<ReceptEntity> receptDtosToEntities(List<ReceptDto> l);
}
