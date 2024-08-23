package org.hae.tasklogue.controllers.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.hae.tasklogue.dto.requestdto.ApplicationUserSignUp;
import org.hae.tasklogue.dto.requestdto.SignInRequest;
import org.hae.tasklogue.dto.response.AuthenticationResponse;
import org.hae.tasklogue.dto.response.CreationResponse;
import org.hae.tasklogue.service.authService.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@Tag(name = "Authentication")

public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(value = "/register")
    public ResponseEntity<CreationResponse> register(@RequestBody @Valid ApplicationUserSignUp applicationUserSignUp) throws MessagingException {
        ResponseEntity<CreationResponse> creationResponse = authService.register(applicationUserSignUp);
        return creationResponse;
    }

    @PostMapping(value = "/sign_in")
    public ResponseEntity<AuthenticationResponse> authentication(@RequestBody @Valid SignInRequest signInRequest) {

        return ResponseEntity.ok(authService.authentication(signInRequest).getBody());
    }
    @GetMapping(value = "/activate_account")
    public void activateAccount(
            @RequestParam String token
    ) throws MessagingException {
        authService.activateAccount(token);
    }

}
