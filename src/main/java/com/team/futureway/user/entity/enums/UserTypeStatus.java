package com.team.futureway.user.entity.enums;

import lombok.Getter;

@Getter
public enum UserTypeStatus {
  // 혼란형
  INTERESTED_IN_TOPIC("혼란형-확신"),
  NOT_INTERESTED_IN_TOPIC("혼란형-불확신"),

  // 기타 유형
  HESITANT("망설임형"),
  FEELING_LOST("막막형");

  private final String description;

  UserTypeStatus(String description) {
    this.description = description;
  }

}
