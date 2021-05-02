package az.etaskify.service;


import az.etaskify.dto.AuthRequest;
import az.etaskify.dto.AuthToken;

public interface AuthenticationService {
    AuthToken authenticate(AuthRequest authRequest);
}
