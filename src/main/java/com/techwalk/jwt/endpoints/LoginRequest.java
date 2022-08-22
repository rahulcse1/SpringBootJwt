package com.techwalk.jwt.endpoints;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginRequest {
  private String username;
  private String password;
}
