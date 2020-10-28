package com.bms.customer.mgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bms.customer.mgmt.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	List<Customer> findByEnabledTrue();



}
