package com.team.futureway.member.controller;

import com.team.futureway.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {

  private final MemberService memberService;

  @GetMapping("")
  public ResponseEntity<?> getUsersAll() {
    return ResponseEntity.ok(memberService.getUsersAll());
  }

}
