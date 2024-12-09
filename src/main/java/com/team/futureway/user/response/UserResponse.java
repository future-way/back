package com.team.futureway.user.response;

import com.team.futureway.user.dto.UserDTO;

public record UserResponse(Long userId, String name) {

    public static UserResponse of(UserDTO userDTO) {
        return new UserResponse(userDTO.getUserId(), userDTO.getName());
    }
}
