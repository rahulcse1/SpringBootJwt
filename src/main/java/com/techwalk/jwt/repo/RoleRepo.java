package com.techwalk.jwt.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techwalk.jwt.model.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
