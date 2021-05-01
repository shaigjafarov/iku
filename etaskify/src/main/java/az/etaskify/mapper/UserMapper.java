package az.etaskify.mapper;

import az.etaskify.dto.UserDto;
import az.etaskify.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    List<UserDto> toUserDtoList(List<User> users);

    List<User> toUserList(List<UserDto> userDtos);



    User toDto(UserDto userDto);

    UserDto toEntity(User user);
}
