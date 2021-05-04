package az.etaskify.controller;

import az.etaskify.dto.OrganizationDto;
import az.etaskify.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("organization")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @RequestMapping(value = "/sign-up")
    public ResponseEntity<String> signUpOrganization(@Valid @RequestBody OrganizationDto organizationDto) {
        return new ResponseEntity<>(organizationService.saveOrganization(organizationDto), HttpStatus.CREATED);
    }


}
