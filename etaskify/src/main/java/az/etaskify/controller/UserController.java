package az.etaskify.controller;

import az.etaskify.dto.UserDto;
import az.etaskify.model.Organization;
import az.etaskify.model.User;
import az.etaskify.service.OrganizationService;
import az.etaskify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private  final UserService userService;
    private final OrganizationService organizationService;

    @PostMapping(value = "/save")
    public ResponseEntity<User> signUpOrganization(@Valid @RequestBody User user) {
        return userService.saveOrUpdateUserByOwner(user);
    }


    @GetMapping(value = "/organizationUsers")
    public ResponseEntity<List<UserDto>> organizationUsers (@RequestParam Long id) {
        return userService.organizationUsersByOwner(id);
    }



}