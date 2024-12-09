package com.team.futureway.user;

import com.team.futureway.user.dto.UserDTO;
import com.team.futureway.user.entity.User;
import com.team.futureway.user.repository.UserRepository;
import com.team.futureway.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void 사용자_등록_성공() {
        // Given
        String name = "John Doe";
        User mockUser = User.of(1L, name, LocalDateTime.now());
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // When
        UserDTO result = userService.save(name);

        // Then
        assertEquals(mockUser.getUserId(), result.getUserId());
        assertEquals(mockUser.getName(), result.getName());
    }

}
