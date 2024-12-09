package com.team.futureway.common.utils;

import lombok.Getter;

@Getter
public enum ConsultKind {

  CONFUSED("혼란형"),
  HESITANT("망설임형"),
  OVERWHELMED("막막형");

  private final String description;

  ConsultKind(String description) {
    this.description = description;
  }

}
