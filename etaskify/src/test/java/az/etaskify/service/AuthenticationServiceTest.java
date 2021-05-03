package az.etaskify.service;

import az.etaskify.config.JwtTokenUtil;
import az.etaskify.dto.AuthRequest;
import az.etaskify.exception.InvalidCredentialsExceptions;
import az.etaskify.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenUtil jwtTokenUtil;


    @Test
    public void givenBadCredentialsWhenAuthenticateThenThrowBadCredentialsExceptions() {

        Mockito.doAnswer(
                invocation -> {
                    throw new BadCredentialsException("Username or password is incorrect");
                }
        ).when(authenticationManager).authenticate(any());

        Assertions.assertThrows(InvalidCredentialsExceptions.class,
                () -> authenticationService.authenticate(new AuthRequest()));

    }

    @Test
    public void givenCorrectCredentialsWhenAuthenticateThenReturnSuccess() {
        String token = "TOKEN";
        when(jwtTokenUtil.generateToken(any())).thenReturn(token);
        Assertions.assertEquals(token, authenticationService.authenticate(new AuthRequest()).getToken());
    }

}