package com.team.futureway.user.controller;

import com.team.futureway.user.dto.UserDTO;
import com.team.futureway.user.dto.UserTypeDTO;
import com.team.futureway.user.response.UserRequest;
import com.team.futureway.user.response.UserResponse;
import com.team.futureway.user.response.UserTypeRequest;
import com.team.futureway.user.response.UserTypeResponse;
import com.team.futureway.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "사용자 유형 저장")
    @PostMapping("/type")
    public ResponseEntity<UserTypeResponse> type(@RequestBody UserTypeRequest request) {
        UserTypeDTO userTypeDTO = UserTypeDTO.of(
                request.userId(),
                request.question(),
                request.selectType(),
                request.answer(),
                request.userType()
        );
        UserTypeDTO result = userService.saveUserType(userTypeDTO);
        return ResponseEntity.ok(UserTypeResponse.of(result));
    }

}
