package com.techwalk.jwt.service;

import com.techwalk.jwt.model.AppUser;
import com.techwalk.jwt.model.Role;
import java.util.List;

public interface UserService {

  AppUser saveUser(AppUser user);

  Role saveRole(Role role);

  void addRoleToUser(String role, String username);

  AppUser getUser(String username);

  List<Role> roles();
}
