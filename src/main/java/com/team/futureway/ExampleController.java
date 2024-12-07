package com.team.futureway;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Tag(name = "Example", description = "Example API")
@RequestMapping("/example")
@RestController
public class ExampleController {

  @Operation(summary = "Http GET Request 예시")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "리턴 성공")
  })
  @GetMapping()
  public String helloGet(@RequestParam final String name) {
    return "Hello " + name;
  }

  @Operation(summary = "Http POST Request 예시")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "리턴 성공"),
      @ApiResponse(responseCode = "404", description = "body에 name을 넣어주세요."),
      @ApiResponse(responseCode = "500", description = "Server 에러")
  })
  @PostMapping()
  public String helloPost(@RequestBody Map<String, String> nameMap) {
    return "Http body : " + nameMap.get("name");
  }

  @GetMapping("/log")
  public void log() {
    log.trace("Trace");
    log.debug("Debug");
    log.info("Info");
    log.warn("Warn");
    log.error("Error");
  }

}
