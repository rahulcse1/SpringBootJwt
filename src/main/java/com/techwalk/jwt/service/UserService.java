package com.techwalk.jwt.service;

import com.techwalk.jwt.model.Role;
import com.techwalk.jwt.model.User;

public interface UserService {

	User saveUser(User user);

	Role saveRole(Role role);

	void addRoleToUser(String role, String username);

	User getUser(String username);

}
