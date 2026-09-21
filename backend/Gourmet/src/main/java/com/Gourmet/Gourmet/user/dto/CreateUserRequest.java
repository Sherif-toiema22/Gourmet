package com.Gourmet.Gourmet.user.dto;
public record CreateUserRequest(
        String name,
        String email,
        String password,
        String phone
) {
}