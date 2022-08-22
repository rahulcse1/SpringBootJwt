package com.techwalk.jwt.endpoints;

import com.techwalk.jwt.model.AppUser;
import com.techwalk.jwt.model.Role;
import com.techwalk.jwt.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
	private final UserService service;

	@GetMapping("/roles")
	public ResponseEntity<?> getRoles() {
		return ResponseEntity.ok().body(service.roles());
	}

	@GetMapping("/users")
	public ResponseEntity<?> getUsers() {
		return ResponseEntity.ok().body(service.users());
	}

	@PostMapping("/user/save")
	public ResponseEntity<?> saveUser(@RequestBody AppUser user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.saveUser(user));
	}

	@PostMapping("/role/save")
	public ResponseEntity<?> saveRole(@RequestBody Role role) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.saveRole(role));
	}

	@PostMapping("/role/addRoleToUser")
	public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUser roleToUser) {
		service.addRoleToUser(roleToUser.getRole(), roleToUser.getUsername());
		return ResponseEntity.ok().body("Role Added Successfully.");
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		log.info("username: " + loginRequest.getUsername());
		return ResponseEntity.status(HttpStatus.OK).body("LoggedIn Successfully.");
	}

	@PostMapping("/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
		var authHeader = request.getHeader(AUTHORIZATION);
	}
}

@Data
class RoleToUser {
	private String username;
	private String role;
}
