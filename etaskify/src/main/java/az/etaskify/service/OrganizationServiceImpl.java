package az.etaskify.service;

import az.etaskify.model.Organization;
import az.etaskify.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;


    @Override
    public ResponseEntity<Organization> saveOrganization(Organization organization) {
        try {
            organization.getUsers().forEach(user -> user.setOrganization(organization));
            Organization organizationInDb=organizationRepository.save(organization);

            return new ResponseEntity<>(organizationInDb, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Organization findOrganizationByOwnerId(Long userId) {
        try {

            return  organizationRepository.findOrganizationByOwnerId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
