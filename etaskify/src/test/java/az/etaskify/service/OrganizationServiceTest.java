package az.etaskify.service;

import az.etaskify.config.JwtTokenUtil;
import az.etaskify.dto.AuthRequest;
import az.etaskify.dto.OrganizationDto;
import az.etaskify.dto.UserOwnerDto;
import az.etaskify.exception.AlreadyExistsException;
import az.etaskify.exception.ValidationException;
import az.etaskify.model.Organization;
import az.etaskify.model.User;
import az.etaskify.repository.OrganizationRepository;
import az.etaskify.repository.UserRepository;
import az.etaskify.service.impl.AuthenticationServiceImpl;
import az.etaskify.service.impl.OrganizationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrganizationServiceTest {
    @InjectMocks
    private OrganizationServiceImpl organizationService;
    @Mock
    private OrganizationRepository organizationRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordService passwordService;

    @Test
    void givenOrgWithUserOwnerDtoWhenSaveOrUpdateThenThrowAlreadyExistsException() {
        Optional<User> userOptional = Optional.of(new User());
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setOwnerDto(new UserOwnerDto("email"));
        when(userRepository.findUserEntityByEmail(any())).thenReturn(userOptional);
        Assertions.assertThrows(AlreadyExistsException.class, () -> organizationService.saveOrganization(organizationDto));


    }
    @Test
    void givenOrgDtoWithUsrOwnDtoWhenSaveOrUpdateThenReturnOrganization() {
       Organization organization= new Organization();
        Optional<User> userOptional = Optional.empty();
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setOwnerDto(new UserOwnerDto("email"));
        when(userRepository.findUserEntityByEmail(any())).thenReturn(userOptional);
        when(passwordService.bcryptEncryptor(any())).thenReturn("bcryptPass");
        when(organizationRepository.save(any())).thenReturn(organization);
        Assertions.assertEquals("Organization created successful", organizationService.saveOrganization(organizationDto));


    }

    @Test
    void givenEmailWhenFindOrganizationByEmailThenReturnOrganization() {
        Organization organization = new Organization();
        when(organizationRepository.findOrganizationByEmail(any())).thenReturn(organization);
        Assertions.assertEquals(organization, organizationService.findOrganizationByEmail("test@gmail.com"));

    }


    @Test
     void givenOwnerUserIdWhenFindOrganizationOwnerIdThenReturnOrganization() {
        Organization organization = new Organization();
        when(organizationRepository.findOrganizationByOwnerId(any())).thenReturn(organization);
        Assertions.assertEquals(organization, organizationService.findOrganizationByOwnerId(3l));
    }
}