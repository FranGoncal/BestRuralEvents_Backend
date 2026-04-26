package com.bestRuralEvents.UserService.Controller;

import com.bestRuralEvents.UserService.DTO.CreateUserProfileRequest;
import com.bestRuralEvents.UserService.DTO.MessageResponse;
import com.bestRuralEvents.UserService.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/users")
public class InternalUserController {

    private final UserService userService;

    public InternalUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public MessageResponse createUserProfile(
            @Valid @RequestBody CreateUserProfileRequest request
    ) {
        userService.createUserProfile(request);
        return new MessageResponse("User profile created successfully");
    }
}