package az.etaskify.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import az.etaskify.dto.UserDto;
import az.etaskify.enums.AuthorityName;
import az.etaskify.model.Organization;
import az.etaskify.model.User;
import az.etaskify.repository.UserRepository;
import az.etaskify.service.impl.UserServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrganizationService organizationService;
    @Mock
    private PasswordService passwordService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, organizationService, passwordService, "12345");
    }

    @Test
    void givenUserWithOrganizationWhenSaveOrUpdateThenCreateUserWithDefaultPassword() {
        when(organizationService.findOrganizationByEmail(any())).thenReturn(new Organization());
        when(passwordService.bcryptEncryptor(any())).thenReturn("encoded");
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        Assertions.assertEquals(HttpStatus.CREATED, userService.saveOrUpdateUser(new UserDto()).getStatusCode());
    }

    @Test
    void givenEmailWhenFindOrganizationByEmailThenReturnUsersOfTheOrg() {
        when(organizationService.findOrganizationByEmail(any())).thenReturn(new Organization());
        Assertions.assertEquals(HttpStatus.OK, userService.organizationUsers().getStatusCode());
    }

    @Test
    void givenWrongUsernameWhenLoadUsernameThenThrowUsernameNotFoundException() {
        when(userRepository.findUserEntityByEmail(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(any()));
    }

    @Test
    void givenCorrectUsernameAndPassWhenLoadUsernameThenReturnSuccess() {
        User user = new User();
        user.setAuthority(AuthorityName.ROLE_ADMIN);
        user.setEmail("123");
        user.setPassword("12345");
        when(userRepository.findUserEntityByEmail(any())).thenReturn(Optional.of(user));
        Assertions.assertEquals(user.getUsername(), userService.loadUserByUsername(any()).getUsername());
    }
}