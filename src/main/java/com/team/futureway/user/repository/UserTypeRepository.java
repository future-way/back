package com.team.futureway.user.repository;

import com.team.futureway.user.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
    UserType findByUserId(Long userId);

}
