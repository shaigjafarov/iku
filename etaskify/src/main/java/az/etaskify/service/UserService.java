package az.etaskify.service;

import az.etaskify.dto.UserDto;
import az.etaskify.model.Organization;
import az.etaskify.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    List<User> findAll();

    ResponseEntity<User> saveOrUpdateUser(User user);

    ResponseEntity<User> saveOrUpdateUserByOwner(User user);

    ResponseEntity<List<UserDto>> organizationUsersByOwner(Long id);
}
