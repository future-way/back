package com.team.futureway.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
public class User {

  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Column(name = "email", length = 100)
  private String email;

  @Column(name = "password", length = 200)
  private String password;

  @Column(name = "name", length = 50)
  private String name;

  @Column(name = "job", length = 50)
  private String job;

  @Column(name = "phone", length = 11)
  private String phone;

  @Column(name = "address", length = 200)
  private String address;

  @Column(name = "gender", length = 6)
  private String gender;

  @Column(name = "login", length = 50)
  private String login;

  @Column(name = "create_at")
  @CreatedDate
  private LocalDateTime createAt;

  @Column(name = "update_at")
  @LastModifiedDate
  private LocalDateTime updateAt;

  @Column(name = "delete_at")
  private LocalDateTime deleteAt;

}
