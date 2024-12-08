package com.team.futureway.member.service;

import com.team.futureway.member.entity.User;
import com.team.futureway.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {

  private final MemberRepository memberRepository;

  public List<User> getUsersAll() {
    return memberRepository.findAll();
  }

}
