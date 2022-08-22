package com.techwalk.jwt.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techwalk.jwt.model.User;

public interface UserRepo extends JpaRepository<User, Long>{
	
	User findByUsername(String username);

}
