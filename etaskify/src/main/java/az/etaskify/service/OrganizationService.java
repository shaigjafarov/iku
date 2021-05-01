package az.etaskify.service;

import az.etaskify.model.Organization;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrganizationService {

    ResponseEntity<Organization> saveOrganization(Organization organization);

    Organization findOrganizationByOwnerId(Long userId);
}
