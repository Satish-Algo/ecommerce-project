package e_commerce.com.user.controller;

import e_commerce.com.common.response.ApiResponse;
import e_commerce.com.user.dto.UserRequest;
import e_commerce.com.user.dto.UserResponse;
import e_commerce.com.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(
            @RequestBody UserRequest request
    ) {
        UserResponse response = userService.createUser(request);

        return new ApiResponse<>(
                true,
                "User created successfully",
                response
        );
    }

    @GetMapping("/profile")
    public String getProfile() {
        return "User is authenticated";
    }
}