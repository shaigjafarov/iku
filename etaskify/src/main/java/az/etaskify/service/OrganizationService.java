package az.etaskify.service;

import az.etaskify.dto.OrganizationDto;
import az.etaskify.model.Organization;
import org.springframework.http.ResponseEntity;


public interface OrganizationService {

    String saveOrganization(OrganizationDto organizationDto);

    Organization findOrganizationByOwnerId(Long userId);

    Organization findOrganizationByEmail(String email);

}
