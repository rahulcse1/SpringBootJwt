package com.techwalk.jwt.service;

import com.techwalk.jwt.model.AppUser;
import com.techwalk.jwt.model.Role;
import com.techwalk.jwt.repo.RoleRepo;
import com.techwalk.jwt.repo.UserRepo;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepo userRepo;
  private final RoleRepo roleRepo;

  @Override
  public AppUser saveUser(AppUser user) {
    log.info("save user");
    return userRepo.save(user);
  }

  @Override
  public Role saveRole(Role role) {
    return roleRepo.save(role);
  }

  @Override
  public void addRoleToUser(String rol, String username) {
    var user = userRepo.findByUsername(username);
    var role = roleRepo.findByName(rol);
    user.getRoles().add(role);
  }

  @Override
  public AppUser getUser(String username) {
    return userRepo.findByUsername(username);
  }

  @Override
  public List<Role> roles() {
    return roleRepo.findAll();
  }
}
