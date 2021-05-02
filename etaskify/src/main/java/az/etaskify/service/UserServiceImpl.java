package az.etaskify.service;

import az.etaskify.dto.UserDto;
import az.etaskify.enums.AuthorityName;
import az.etaskify.mapper.UserMapper;
import az.etaskify.model.Organization;
import az.etaskify.repository.UserRepository;
import az.etaskify.model.User;
import az.etaskify.util.SecurityContextUtility;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final OrganizationService organizationService;
    private final PasswordService passwordService;

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
    public ResponseEntity<UserDto> saveOrUpdateUser(UserDto userDto) {
        try {
            User user = UserMapper.INSTANCE.toEntity(userDto);
            Organization organization = organizationService.findOrganizationByEmail(SecurityContextUtility.getLoggedUsername());
            user.setOrganization(organization);
            user.setAuthority(AuthorityName.ROLE_USER);
            user.setPassword(passwordService.bcryptEncryptor("123456"));
            UserMapper.INSTANCE.toDto(userRepository.save(user));
            return new ResponseEntity<>( HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<UserDto>> organizationUsers() {
        try {
            Organization organization = organizationService.findOrganizationByEmail(SecurityContextUtility.getLoggedUsername());
            List<User> userList = organization.getUsers();
            List<UserDto> userDtoList = UserMapper.INSTANCE.toUserDtoList(userList);
            return new ResponseEntity<>(userDtoList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> appUserEntity = userRepository
                .findUserEntityByEmail(email);
        if (appUserEntity.isPresent()) {
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(appUserEntity.get().getAuthority().name()));
            return new org.springframework.security.core.userdetails.User(appUserEntity.get().getUsername(), appUserEntity.get().getPassword(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("Username as email " + email + " not found");
        }

    }


}
