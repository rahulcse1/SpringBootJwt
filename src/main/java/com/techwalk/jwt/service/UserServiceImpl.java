package com.techwalk.jwt.service;

import com.techwalk.jwt.model.AppUser;
import com.techwalk.jwt.model.Role;
import com.techwalk.jwt.repo.RoleRepo;
import com.techwalk.jwt.repo.UserRepo;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

  private final UserRepo userRepo;
  private final RoleRepo roleRepo;
  private final PasswordEncoder encoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = userRepo.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found.");
    }

    var authorities = new ArrayList<SimpleGrantedAuthority>();
    user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

    return new User(user.getUsername(), user.getPassword(), authorities);
  }

  @Override
  public AppUser saveUser(AppUser user) {
    log.info("save user");
    user.setPassword(encoder.encode(user.getPassword()));
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

  @Override
  public List<AppUser> users() {
    return userRepo.findAll();
  }
}
