package com.bms.customer.mgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bms.customer.mgmt.entities.User;

public interface UserRepository extends JpaRepository<User, String> {

	List<User> findByActiveTrue();

	boolean existsByUsername(String username);

}
