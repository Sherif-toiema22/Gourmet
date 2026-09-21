package com.Gourmet.Gourmet.user.service;

import com.Gourmet.Gourmet.user.dto.CreateUserRequest;
import com.Gourmet.Gourmet.user.dto.UserResponse;
import com.Gourmet.Gourmet.user.entity.User;
import com.Gourmet.Gourmet.user.mapper.UserMapper;
import com.Gourmet.Gourmet.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(
            UserRepository userRepository,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    public UserResponse createUser(CreateUserRequest request) {

        User user = userMapper.toEntity(request);

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }
}