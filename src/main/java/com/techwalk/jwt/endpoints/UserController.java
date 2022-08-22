package com.techwalk.jwt.endpoints;

import com.techwalk.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
  private final UserService service;

  @GetMapping("/roles")
  public ResponseEntity<?> getRoles() {
    return ResponseEntity.ok().body(service.roles());
  }
}
