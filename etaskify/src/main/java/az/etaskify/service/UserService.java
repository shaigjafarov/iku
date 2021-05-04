package az.etaskify.service;

import az.etaskify.dto.UserDto;

import java.util.List;

public interface UserService {

    String saveOrUpdateUser(UserDto userDto);

    List<UserDto> organizationUsers();
}
