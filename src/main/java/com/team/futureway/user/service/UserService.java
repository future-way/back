package com.team.futureway.user.service;

import com.team.futureway.user.dto.UserDTO;
import com.team.futureway.user.dto.UserTypeDTO;
import com.team.futureway.user.entity.User;
import com.team.futureway.user.entity.UserType;
import com.team.futureway.user.repository.UserRepository;
import com.team.futureway.user.repository.UserTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserTypeRepository userTypeRepository;

    public UserDTO save(String name) {
        User user = User.of(null, name, LocalDateTime.now());
        User result = userRepository.save(user);
        return UserDTO.of(result.getUserId(), result.getName(), result.getCreateAt());
    }

    public UserTypeDTO saveUserType(UserTypeDTO userTypeDTO) {
        UserType existUserType = userTypeRepository.findByUserId(userTypeDTO.getUserId());

        Long userTypeId = null;

        if (existUserType != null) {
            userTypeId = existUserType.getUserTypeId();
        }

        UserType userType = UserType.of(userTypeId
                , userTypeDTO.getUserId()
                , userTypeDTO.getQuestion()
                , userTypeDTO.getSelectType()
                , userTypeDTO.getAnswer()
                , userTypeDTO.getUserType()
        );

        UserType result = userTypeRepository.save(userType);

        return userTypeDTO.of(result.getUserId(), result.getQuestion(), result.getSelectType(), result.getAnswer(), result.getUserType());
    }
}
