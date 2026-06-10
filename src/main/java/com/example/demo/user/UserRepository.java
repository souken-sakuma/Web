package com.example.demo.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
	
	User findTopByOrderByIdDesc();
	
	List<User> findByMemberNumberContaining(String keyword);
	
	void deleteByUsername(String username);

}
