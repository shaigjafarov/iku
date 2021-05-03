package az.etaskify.service.impl;

import az.etaskify.dto.OrganizationDto;
import az.etaskify.enums.AuthorityName;
import az.etaskify.exception.AlreadyExistsException;
import az.etaskify.mapper.OrganizationMapper;
import az.etaskify.mapper.UserOwnerMapper;
import az.etaskify.model.Organization;
import az.etaskify.model.User;
import az.etaskify.repository.OrganizationRepository;
import az.etaskify.repository.UserRepository;
import az.etaskify.service.OrganizationService;
import az.etaskify.service.PasswordService;
import az.etaskify.util.ValidationObjects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final PasswordService passwordService;


    @Override
    public ResponseEntity<Organization> saveOrganization(OrganizationDto organizationDto) {
        ValidationObjects.controlObjectNotNull(organizationDto.getOwnerDto(), "Owner does not null.");
        String rawPassword = organizationDto.getOwnerDto().getPassword();
        if (userRepository.findUserEntityByEmail(organizationDto.getOwnerDto().getEmail()).isPresent()) {
            throw new AlreadyExistsException("email already exist");
        }
        organizationDto.getOwnerDto().setPassword(passwordService.bcryptEncryptor(rawPassword));
        Organization organization = OrganizationMapper.INSTANCE.toEntity(organizationDto);
        User user = UserOwnerMapper.INSTANCE.toEntity(organizationDto.getOwnerDto());
        user.setOrganization(organization);
        user.setAuthority(AuthorityName.ROLE_ADMIN);
        organization.setUsers(Collections.singletonList(user));
        Organization organizationInDb = organizationRepository.save(organization);
        return new ResponseEntity<>(organizationInDb, HttpStatus.OK);
    }

    @Override
    public Organization findOrganizationByOwnerId(Long userId) {
            return organizationRepository.findOrganizationByOwnerId(userId);

    }

    @Override
    public Organization findOrganizationByEmail(String email) {
            return organizationRepository.findOrganizationByEmail(email);

    }
}
