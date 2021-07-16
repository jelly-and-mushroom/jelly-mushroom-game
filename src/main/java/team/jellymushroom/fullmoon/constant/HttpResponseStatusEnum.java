package team.jellymushroom.fullmoon.constant;

import lombok.Getter;

@Getter
public enum HttpResponseStatusEnum {

  SUCCESS("success"),
  ERROR("error");

  private String value;

  HttpResponseStatusEnum(String value) {
    this.value = value;
  }
}
