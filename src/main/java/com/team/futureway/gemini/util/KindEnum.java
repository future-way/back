package com.team.futureway.gemini.util;

public enum KindEnum {

  CONFUSED("혼란형"),
  HESITANT("망설임형"),
  OVERWHELMED("막막형");

  private final String description;

  KindEnum(String description) {
    this.description = description;
  }

  public static KindEnum fromString(String value) {
    for (KindEnum kind : KindEnum.values()) {
      if (kind.name().equalsIgnoreCase(value)) {
        return kind;
      }
    }
    throw new IllegalArgumentException("Invalid Consult Kind: " + value);
  }
}
