package com.team.futureway.user.service;

import com.team.futureway.user.dto.UserDTO;
import com.team.futureway.user.entity.User;
import com.team.futureway.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserDTO save(String name) {
        User user = User.of(null, name, LocalDateTime.now());
        User result = userRepository.save(user);
        return UserDTO.of(result.getUserId(), result.getName(), result.getCreateAt());
    }
}
