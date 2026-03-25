package com.team.futureway.user.controller;

import com.team.futureway.user.dto.UserDTO;
import com.team.futureway.user.dto.UserTypeDTO;
import com.team.futureway.user.request.UserRequest;
import com.team.futureway.user.request.UserTypeRequest;
import com.team.futureway.user.response.UserResponse;
import com.team.futureway.user.response.UserTypeResponse;
import com.team.futureway.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;

    @Operation(summary = "사용자 생성")
    @PostMapping
    public ResponseEntity<UserResponse> save(@RequestBody UserRequest request) {
        UserDTO userDTO = userService.save(request.name());
        return ResponseEntity.ok(UserResponse.of(userDTO));
    }

    @Operation(summary = "사용자 유형 저장")
    @PutMapping("/{userId}/type")
    public ResponseEntity<UserTypeResponse> type(@PathVariable Long userId, @RequestBody UserTypeRequest request) {
        UserTypeDTO userTypeDTO = UserTypeDTO.of(
                userId,
                request.question(),
                request.selectType(),
                request.answer(),
                request.userType()
        );
        UserTypeDTO result = userService.saveUserType(userTypeDTO);
        return ResponseEntity.ok(UserTypeResponse.of(result));
    }

}
