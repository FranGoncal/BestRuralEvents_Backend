package com.bestRuralEvents.UserService.Controller;

import com.bestRuralEvents.UserService.DTO.CreateUserProfileRequest;
import com.bestRuralEvents.UserService.DTO.MessageResponse;
import com.bestRuralEvents.UserService.DTO.UserResponse;
import com.bestRuralEvents.UserService.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/users")
public class InternalUserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public MessageResponse createUserProfile(
            @Valid @RequestBody CreateUserProfileRequest request
    ) {
        userService.createUserProfile(request);
        return new MessageResponse("User profile created successfully");
    }

    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }
}