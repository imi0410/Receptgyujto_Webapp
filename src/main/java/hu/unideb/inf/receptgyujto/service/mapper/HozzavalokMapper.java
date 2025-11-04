package hu.unideb.inf.receptgyujto.service.mapper;

import hu.unideb.inf.receptgyujto.data.entity.HozzavalokEntity;
import hu.unideb.inf.receptgyujto.service.dto.HozzavalokDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HozzavalokMapper {
    HozzavalokDto hozzavalokEntitytoDto(HozzavalokEntity e);
    List<HozzavalokDto> hozzavalokEntitiesToDtos(List<HozzavalokEntity> l);

    HozzavalokEntity hozzavalokDtoToEntity(HozzavalokDto d);
    List<HozzavalokEntity> hozzavalokDtosToEntities(List<HozzavalokDto> l);
}
