package com.team.futureway.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class UserDTO {
    private Long userId;
    private String name;
    private LocalDateTime createAt;

    public static UserDTO of(Long userId, String name, LocalDateTime createAt) {
        return new UserDTO(userId, name, createAt);
    }
}
