package az.etaskify.service;

import az.etaskify.dto.OrganizationDto;
import az.etaskify.exception.ApiException;
import az.etaskify.mapper.OrganizationMapper;
import az.etaskify.mapper.UserOwnerMapper;
import az.etaskify.model.Organization;
import az.etaskify.model.User;
import az.etaskify.repository.OrganizationRepository;
import az.etaskify.repository.UserRepository;
import az.etaskify.util.ValidationObjects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final PasswordService passwordService;


    @Override
    public ResponseEntity<Organization> saveOrganization(OrganizationDto organizationDto) {
        ValidationObjects.controlObjectNotNull(organizationDto.getOwnerDto());
        String rawPassword = organizationDto.getOwnerDto().getPassword();
        String email = organizationDto.getOwnerDto().getEmail();
        if (userRepository.findUserEntityByEmail(email).isPresent()) {
            throw new ApiException("Email already exist ", email, HttpStatus.NOT_ACCEPTABLE, null);
        }
        organizationDto.getOwnerDto().setPassword(passwordService.bcryptEncryptor(rawPassword));
        Organization organization = setOrganization(organizationDto);
        Organization organizationInDb = organizationRepository.save(organization);
        return new ResponseEntity<>(organizationInDb, HttpStatus.OK);
    }

    @Override
    public Organization findOrganizationByOwnerId(Long userId) {
        return organizationRepository.findOrganizationByOwnerId(userId);
    }

    private Organization setOrganization(OrganizationDto organizationDto){
        Organization organization = OrganizationMapper.INSTANCE.toEntity(organizationDto);
        User user = UserOwnerMapper.INSTANCE.toEntity(organizationDto.getOwnerDto());
        user.setOrganization(organization);
        organization.setUsers(Collections.singletonList(user));
        return organization;
    }

}
