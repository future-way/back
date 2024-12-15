package com.team.futureway.gemini.repository;

import com.team.futureway.gemini.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
    UserType findByUserId(Long userId);

}
