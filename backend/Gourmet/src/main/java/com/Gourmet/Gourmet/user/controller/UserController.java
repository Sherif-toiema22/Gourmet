package com.Gourmet.Gourmet.user.controller;

import com.Gourmet.Gourmet.user.dto.CreateUserRequest;
import com.Gourmet.Gourmet.user.dto.UserResponse;
import com.Gourmet.Gourmet.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse createUser(
            @RequestBody CreateUserRequest request
    ) {
        return userService.createUser(request);
    }
}