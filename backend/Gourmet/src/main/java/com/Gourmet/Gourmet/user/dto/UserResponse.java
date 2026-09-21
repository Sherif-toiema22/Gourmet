package com.Gourmet.Gourmet.user.dto;


import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String name,
        String email,
        String phone,
        LocalDateTime  createdAt,
        LocalDateTime updatedAt
) {
}
