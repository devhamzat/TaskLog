package org.hae.tasklogue.service.authService;

import jakarta.mail.MessagingException;
import org.hae.tasklogue.dto.requestdto.ApplicationUserSignUp;
import org.hae.tasklogue.dto.requestdto.SignInRequest;
import org.hae.tasklogue.dto.response.AuthenticationResponse;
import org.hae.tasklogue.dto.response.CreationResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<CreationResponse> register(ApplicationUserSignUp applicationUserSignUp) throws MessagingException;

    ResponseEntity<AuthenticationResponse> authentication(SignInRequest signInRequest);

    void activateAccount(String token) throws MessagingException;
}
