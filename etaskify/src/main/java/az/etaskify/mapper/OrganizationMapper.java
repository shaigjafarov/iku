package az.etaskify.mapper;

import az.etaskify.dto.OrganizationDto;
import az.etaskify.model.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrganizationMapper {

    OrganizationMapper INSTANCE = Mappers.getMapper(OrganizationMapper.class);

    Organization toEntity(OrganizationDto organizationDto);

    OrganizationDto toDto(Organization organization);


}
