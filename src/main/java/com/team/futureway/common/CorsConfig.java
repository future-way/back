/*
package com.team.futureway.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    // 추후 도메인 결정 시
    // config.setAllowCredentials(true); // 쿠키 등 인증 사용 시.
    // config.addAllowedOriginPattern("https://www.your.domain");
    // config.addAllowedOriginPattern("https://your.domain");
    config.addAllowedOriginPattern("*");
    config.addAllowedHeader("*");
    config.addExposedHeader("*");
    config.addAllowedMethod("*");
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

}
*/
