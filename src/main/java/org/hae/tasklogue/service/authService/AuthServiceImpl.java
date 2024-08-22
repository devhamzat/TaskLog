package org.hae.tasklogue.service.authService;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.hae.tasklogue.dto.requestdto.ApplicationUserSignUp;
import org.hae.tasklogue.dto.requestdto.SignInRequest;
import org.hae.tasklogue.dto.response.AuthenticationResponse;
import org.hae.tasklogue.dto.response.CreationResponse;
import org.hae.tasklogue.entity.Role;
import org.hae.tasklogue.entity.applicationUser.ApplicationUser;
import org.hae.tasklogue.entity.applicationUser.Token;
import org.hae.tasklogue.exceptions.ActivationCodeExpired;
import org.hae.tasklogue.repository.ApplicationUserRepository;
import org.hae.tasklogue.repository.RoleRepository;
import org.hae.tasklogue.repository.TokenRepository;
import org.hae.tasklogue.security.JwtService;
import org.hae.tasklogue.service.email.EmailService;
import org.hae.tasklogue.utils.enums.EmailTemplateName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    @Value("${application.mailing.fontend.activation-url}")
    private String activationUrl;

    private PasswordEncoder passwordEncoder;
    private EmailService emailService;
    private JwtService jwtService;


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ApplicationUserRepository applicationUserRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TokenRepository tokenRepository;


    public AuthServiceImpl(PasswordEncoder passwordEncoder, EmailService emailService, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.jwtService = jwtService;
    }

    public ResponseEntity<CreationResponse> register(@Valid ApplicationUserSignUp applicationUserSignUp) throws MessagingException {
        Optional<Role> userRole = roleRepository.findByName("USER");
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUserName(applicationUserSignUp.getUserName());
        applicationUser.setEmail(applicationUserSignUp.getEmail());
        applicationUser.setSecretPassword(passwordEncoder.encode(applicationUserSignUp.getPassword()));
        applicationUser.setAccountEnabled(false);
        applicationUser.setAccountLocked(false);
        applicationUser.setRoles(List.of(userRole.get()));
        applicationUserRepository.save(applicationUser);
        sendValidationEmail(applicationUser);


        CreationResponse response = new CreationResponse();
        response.setStatusCode(201);
        response.setStatus(HttpStatus.CREATED);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AuthenticationResponse> authentication(SignInRequest signInRequest) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUserName(), signInRequest.getPassword()));
        Map<String, Object> claims = new HashMap<>();
        ApplicationUser user = (ApplicationUser) auth.getPrincipal();
        claims.put("Username", user.getUsername());
        String jwtToken = jwtService.generateToken(claims, user);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(jwtToken);
        return ResponseEntity.ok(authenticationResponse);
    }


    @Override
    public void activateAccount(String token) throws MessagingException {
        Optional<Token> savedToken = tokenRepository.findByToken(token);
        if (savedToken.isEmpty()) {
            throw new IllegalArgumentException("Invalid token");
        }

        Token tokenUser = savedToken.get();
        if (LocalDateTime.now().isAfter(tokenUser.getExpiresAt())) {
            sendValidationEmail(tokenUser.getApplicationUser());
            throw new ActivationCodeExpired("Activation token has expired. A new token has been sent to the same email address");
        }

        activateAccountTransactional(tokenUser);
    }

    @Transactional
    protected void activateAccountTransactional(Token tokenUser) {
        ApplicationUser user = tokenUser.getApplicationUser();
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        user.setAccountEnabled(true);
        applicationUserRepository.save(user);

        tokenUser.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(tokenUser);
    }


    private void sendValidationEmail(ApplicationUser applicationUser) throws MessagingException {
        String newToken = generateAndSaveActivationToken(applicationUser);
        emailService.sendEmail(applicationUser.getEmail(),
                applicationUser.getUsername(),
                EmailTemplateName.Activate_Account,
                activationUrl,
                newToken,
                "Account activation"

        );
    }

    private String generateAndSaveActivationToken(ApplicationUser applicationUser) {
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .applicationUser(applicationUser)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int size) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int index = 0; index < size; index++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
}

