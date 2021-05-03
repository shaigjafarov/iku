package az.etaskify.service;

import az.etaskify.dto.UserDto;
import az.etaskify.model.Organization;
import az.etaskify.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<UserDto> saveOrUpdateUser(UserDto userDto);

    ResponseEntity<List<UserDto>> organizationUsers();
}
