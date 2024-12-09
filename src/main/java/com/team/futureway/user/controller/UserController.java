package com.team.futureway.user.controller;

import com.team.futureway.user.dto.UserDTO;
import com.team.futureway.user.response.UserRequest;
import com.team.futureway.user.response.UserResponse;
import com.team.futureway.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/user/")
@RestController
public class UserController {

    private final UserService userService;

    @PutMapping("/save")
    public ResponseEntity<UserResponse> save(@RequestBody UserRequest request) {
        UserDTO userDTO = userService.save(request.name());
        return ResponseEntity.ok(UserResponse.of(userDTO));
    }

}
