package az.etaskify.controller;

import az.etaskify.model.Organization;
import az.etaskify.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("organization")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @RequestMapping(value = "/save") //where is method type
    public ResponseEntity<Organization> signUpOrganization(@RequestBody Organization organization) {
       return organizationService.saveOrganization(organization);
    }


}
