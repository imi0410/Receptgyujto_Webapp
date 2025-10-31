package hu.unideb.inf.receptgyujto.service.mapper;

import hu.unideb.inf.receptgyujto.data.entity.FelhasznaloEntity;
import hu.unideb.inf.receptgyujto.service.dto.RegisztracioDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    FelhasznaloEntity dtoToEntity(RegisztracioDto dto);
}