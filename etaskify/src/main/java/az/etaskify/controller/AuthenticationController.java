package az.etaskify.controller;


import az.etaskify.dto.AuthRequest;
import az.etaskify.dto.AuthToken;
import az.etaskify.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public ResponseEntity<AuthToken> signIn(@RequestBody @Valid AuthRequest authRequest) {

        return ResponseEntity.ok(authenticationService.authenticate(authRequest));
    }
}
