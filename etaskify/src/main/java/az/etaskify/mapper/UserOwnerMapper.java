package az.etaskify.mapper;

import az.etaskify.dto.UserOwnerDto;
import az.etaskify.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserOwnerMapper {

    UserOwnerMapper INSTANCE = Mappers.getMapper(UserOwnerMapper.class);

    List<UserOwnerDto> toUserOwnerDtoList(List<User> users);

    List<User> toUserList(List<UserOwnerDto> userOwnerDtos);

    User toEntity(UserOwnerDto userOwnerDto);

    UserOwnerDto toDto(User user);
}
