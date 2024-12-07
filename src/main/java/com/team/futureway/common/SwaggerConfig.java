package com.team.futureway.common;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  // JWT 사용 시 추가 설정 필요.
  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .components(new Components())
        .info(apiInfo());
  }

  private Info apiInfo() {
    return new Info()
        .title("내일찾기")
        .description("내일찾기 백엔드에서 호출 가능한 Rest-API 목록입니다.")
        .version("1.0.0");
  }

}
