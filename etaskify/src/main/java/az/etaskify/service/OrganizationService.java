package az.etaskify.service;

import az.etaskify.dto.OrganizationDto;
import az.etaskify.model.Organization;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrganizationService {

    ResponseEntity<Organization> saveOrganization(OrganizationDto organizationDto);

    Organization findOrganizationByOwnerId(Long userId);
}
