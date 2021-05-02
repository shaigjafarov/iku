package az.etaskify.service;


import az.etaskify.config.JwtTokenUtil;
import az.etaskify.dto.AuthRequest;
import az.etaskify.dto.AuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;


    @Override
    public AuthToken authenticate(AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Username or password is incorrect", e);
        }

        String token = jwtTokenUtil.generateToken(authRequest.getEmail());

        return AuthToken
                .builder()
                .token(token)
                .username(authRequest.getEmail())
                .build();
    }
}
