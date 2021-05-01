package az.etaskify.service;

import az.etaskify.dto.UserDto;
import az.etaskify.mapper.UserMapper;
import az.etaskify.model.Organization;
import az.etaskify.repository.UserRepository;
import az.etaskify.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrganizationService organizationService;

    @Override
    public List<User> findAll() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseEntity<User> saveOrUpdateUser(User user) {
        try {
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<User> saveOrUpdateUserByOwner(User user, Long ownerId) {
        try {
            Organization organization = organizationService.findOrganizationByOwnerId(ownerId);
            user.setOrganization(organization);
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<UserDto>> organizationUsersByOwner(Long ownerId) {
        try {
            Organization organization = organizationService.findOrganizationByOwnerId(ownerId);
            List<User> userList = organization.getUsers();
            List<UserDto> userDtoList =UserMapper.INSTANCE.toUserDtoList(userList);
            return new ResponseEntity<>(userDtoList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
