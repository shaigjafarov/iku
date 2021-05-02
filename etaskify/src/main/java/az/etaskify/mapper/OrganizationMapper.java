package az.etaskify.mapper;

import az.etaskify.dto.OrganizationDto;
import az.etaskify.dto.TaskDto;
import az.etaskify.dto.UserOwnerDto;
import az.etaskify.model.Organization;
import az.etaskify.model.Task;
import az.etaskify.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrganizationMapper {


//    @Mappings({
//            @Mapping(target = "ownerDto", expression = "java(mapOwnerDto(userOwnerDto))")
//    })
//    User toUserEntity (UserOwnerDto userOwnerDto);

    OrganizationMapper INSTANCE = Mappers.getMapper(OrganizationMapper.class);

    Organization toEntity(OrganizationDto organizationDto);

    OrganizationDto toDto(Organization organization);



}
