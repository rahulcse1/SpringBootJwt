package com.techwalk.jwt;

import com.techwalk.jwt.model.AppUser;
import com.techwalk.jwt.model.Role;
import com.techwalk.jwt.service.UserService;
import java.util.ArrayList;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class JwtApplication {

  public static void main(String[] args) {
    SpringApplication.run(JwtApplication.class, args);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  CommandLineRunner run(UserService service) {
    return args -> {
      service.saveRole(new Role(null, "ROLE_USER"));
      service.saveRole(new Role(null, "ROLE_ADMIN"));
      service.saveUser(new AppUser(null, "Jeet", "Jeet", "Jeet", new ArrayList<>()));
      service.addRoleToUser("ROLE_ADMIN", "Jeet");
    };
  }
}
