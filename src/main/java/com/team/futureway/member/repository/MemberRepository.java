package com.team.futureway.member.repository;

import com.team.futureway.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<User, Long> {
}
