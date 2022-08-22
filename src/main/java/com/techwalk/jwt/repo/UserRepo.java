package com.techwalk.jwt.repo;

import com.techwalk.jwt.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<AppUser, Long> {

  AppUser findByUsername(String username);
}
