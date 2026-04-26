package com.bestRuralEvents.AuthService.Service;

import com.bestRuralEvents.AuthService.DTO.*;
import com.bestRuralEvents.AuthService.Model.AuthUser;
import com.bestRuralEvents.AuthService.Model.Role;
import com.bestRuralEvents.AuthService.Repository.AuthUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserServiceClient userServiceClient;

    public AuthService(
            AuthUserRepository authUserRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            UserServiceClient userServiceClient
    ) {
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userServiceClient = userServiceClient;
    }

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        if (authUserRepository.existsByEmail(request.email())) {
            throw new IllegalStateException("Email already exists");
        }

        AuthUser authUser = new AuthUser();
        authUser.setEmail(request.email());
        authUser.setPasswordHash(passwordEncoder.encode(request.password()));
        authUser.setRole(Role.USER);
        authUser.setEnabled(true);

        AuthUser savedUser = authUserRepository.save(authUser);

        CreateUserProfileRequest profileRequest = new CreateUserProfileRequest(
                savedUser.getId(),
                request.name(),
                savedUser.getEmail(),
                request.birthDate()
        );

        userServiceClient.createUserProfile(profileRequest);

        return new SignupResponse(
                "User registered successfully",
                savedUser.getId(),
                savedUser.getEmail()
        );
    }

    public LoginResponse login(LoginRequest request) {
        AuthUser user = authUserRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!user.isEnabled()) {
            throw new RuntimeException("User account is disabled");
        }

        boolean passwordMatches = passwordEncoder.matches(
                request.password(),
                user.getPasswordHash()
        );

        if (!passwordMatches) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateAccessToken(user);

        return new LoginResponse(
                "Login successful",
                user.getId(),
                user.getEmail(),
                user.getRole().name(),
                token,
                "Bearer"
        );
    }
}