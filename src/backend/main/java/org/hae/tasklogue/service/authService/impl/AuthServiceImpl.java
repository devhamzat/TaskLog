package org.hae.tasklogue.service.authService.impl;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hae.tasklogue.dto.requestdto.ApplicationUserSignUp;
import org.hae.tasklogue.dto.requestdto.SignInRequest;
import org.hae.tasklogue.dto.response.AuthenticationResponse;
import org.hae.tasklogue.dto.response.CreationResponse;
import org.hae.tasklogue.entity.applicationUser.ApplicationUser;
import org.hae.tasklogue.entity.applicationUser.Token;
import org.hae.tasklogue.exceptions.errors.AccountExist;
import org.hae.tasklogue.exceptions.errors.ActivationCodeExpired;
import org.hae.tasklogue.exceptions.errors.ForbiddenRequest;
import org.hae.tasklogue.repository.ApplicationUserRepository;
import org.hae.tasklogue.repository.TokenRepository;
import org.hae.tasklogue.security.JwtService;
import org.hae.tasklogue.service.authService.AuthService;
import org.hae.tasklogue.service.email.EmailService;
import org.hae.tasklogue.service.roles.RolesService;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;
    @Value("${application.activation.token.length}")
    private int activationTokenLength;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ApplicationUserRepository applicationUserRepository;
    @Autowired
    private RolesService rolesService;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private EmailService emailService;


    @Autowired
    public AuthServiceImpl(PasswordEncoder passwordEncoder, JwtService jwtService, RolesService rolesService, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.rolesService = rolesService;
        this.emailService = emailService;
    }

    @Override
    public ResponseEntity<CreationResponse> register(@Valid ApplicationUserSignUp applicationUserSignUp) throws MessagingException {


        Optional<ApplicationUser> existingUser = applicationUserRepository.findApplicationUserByUserName(applicationUserSignUp.getUserName());
        if (existingUser.isPresent()) {
            throw new AccountExist("username or email already in use, sign in");
        }
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUserName(applicationUserSignUp.getUserName());
        applicationUser.setEmail(applicationUserSignUp.getEmail());
        applicationUser.setSecretPassword(passwordEncoder.encode(applicationUserSignUp.getPassword()));
        applicationUser.setAccountEnabled(false);
        applicationUser.setAccountLocked(false);
        applicationUserRepository.save(applicationUser);
        var role = rolesService.createRole(applicationUser);
        applicationUser.setRoles(role);
        applicationUserRepository.save(applicationUser);
        log.info("Role created {}", role);
        sendValidationEmail(applicationUser);


        CreationResponse response = new CreationResponse();
        response.setStatus(HttpStatus.CREATED);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AuthenticationResponse> authentication(SignInRequest signInRequest) {

        var usernamePresentInDatabase = applicationUserRepository.existsByUserName(signInRequest.getUserName());
        if (!usernamePresentInDatabase) {
            throw new ForbiddenRequest("Forbidden request");
        }
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUserName(), signInRequest.getPassword()));
        Map<String, Object> claims = new HashMap<>();
        ApplicationUser user = (ApplicationUser) auth.getPrincipal();
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());
        claims.put("isAccountEnabled", user.isAccountEnabled());
        String jwtToken = jwtService.generateToken(claims, user);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setTokenType("Bearer");
        authenticationResponse.setToken(jwtToken);
        authenticationResponse.setMessage("success");
        return ResponseEntity.ok(authenticationResponse);
    }


    @Override
    @Transactional
    public void activateAccount(String token) throws MessagingException {
        String hashedUserTokenInput = hashToken(token);
        Optional<Token> savedToken = tokenRepository.findByToken(hashedUserTokenInput);
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
        String newToken = generateAndSaveToken(applicationUser);
        emailService.sendActivationCodeEmail(applicationUser.getEmail(),
                applicationUser.getUsername(),
                EmailTemplateName.Activate_Account,
                activationUrl,
                newToken,
                "Account activation");
    }

    private String generateAndSaveToken(ApplicationUser applicationUser) {
        String generatedToken = generateCode(activationTokenLength);
        String hashedToken = hashToken(generatedToken);
        var token = Token.builder()
                .token(hashedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .applicationUser(applicationUser)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateCode(int size) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int index = 0; index < size; index++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    private String hashToken(String activationCode) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(activationCode.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error initializing SHA-256", e);
        }
    }

}

