package com.bestRuralEvents.AuthService.Service;


import com.bestRuralEvents.AuthService.DTO.CreateUserProfileRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class UserServiceClient {

    private final RestClient restClient;
    private final String userServiceUrl;

    public UserServiceClient(
            RestClient.Builder restClientBuilder,
            @Value("${services.user-service.url}") String userServiceUrl
    ) {
        this.restClient = restClientBuilder.build();
        this.userServiceUrl = userServiceUrl;
    }

    public void createUserProfile(CreateUserProfileRequest request) {
        restClient.post()
                .uri(userServiceUrl + "/internal/users")
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }
}