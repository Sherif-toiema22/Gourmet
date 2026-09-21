package com.Gourmet.Gourmet.user.mapper;

import com.Gourmet.Gourmet.user.dto.CreateUserRequest;
import com.Gourmet.Gourmet.user.dto.UserResponse;
import com.Gourmet.Gourmet.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(CreateUserRequest request) {

        User user = new User();

        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setPhone(request.phone());

        return user;
    }

    public UserResponse toResponse(User user) {

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

}