package com.bestRuralEvents.UserService.Controller;

import com.bestRuralEvents.UserService.DTO.MessageResponse;
import com.bestRuralEvents.UserService.DTO.UpdateUserProfileRequest;
import com.bestRuralEvents.UserService.DTO.UserProfileResponse;
import com.bestRuralEvents.UserService.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserProfileResponse getUserProfile(@PathVariable Long id) {
        return userService.getUserProfile(id);
    }

    @PutMapping("/{id}")
    public MessageResponse updateUserProfile(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserProfileRequest request
    ) {
        userService.updateUserProfile(id, request);
        return new MessageResponse("Profile updated successfully");
    }
}