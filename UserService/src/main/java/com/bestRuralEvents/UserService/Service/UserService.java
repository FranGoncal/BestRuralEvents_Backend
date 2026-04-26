package com.bestRuralEvents.UserService.Service;

import com.bestRuralEvents.UserService.DTO.CreateUserProfileRequest;
import com.bestRuralEvents.UserService.DTO.UpdateUserProfileRequest;
import com.bestRuralEvents.UserService.DTO.UserProfileResponse;
import com.bestRuralEvents.UserService.Repository.UserRepository;
import com.bestRuralEvents.UserService.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUserProfile(CreateUserProfileRequest request) {
        if (userRepository.existsById(request.id())) {
            throw new IllegalStateException("User profile already exists");
        }

        userRepository.findByEmail(request.email()).ifPresent(user -> {
            throw new IllegalStateException("Email already exists");
        });

        User user = new User();
        user.setId(request.id());
        user.setName(request.name());
        user.setEmail(request.email());
        user.setBirthDate(request.birthDate());

        userRepository.save(user);
    }

    public UserProfileResponse getUserProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserProfileResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getBirthDate()
        );
    }

    public void updateUserProfile(Long id, UpdateUserProfileRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(request.name());
        user.setBirthDate(request.birthDate());

        userRepository.save(user);
    }
}