package com.bms.customer.mgmt.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bms.customer.mgmt.domain.CustomerDTO;
import com.bms.customer.mgmt.entities.Customer;
import com.bms.customer.mgmt.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("v1/customer")
@Slf4j
public class CustomerController {

	@Autowired
	CustomerService service;

	@PostMapping("/register")
	public ResponseEntity<Customer> register(@Valid @RequestBody CustomerDTO customer) {

		log.info("controller start");
		return new ResponseEntity<>(service.create(customer), HttpStatus.CREATED);

	}

	@GetMapping("/all")
	public List<Customer> getAll() {

		log.info("getAll start");
		return service.allCustomers();

	}

	@GetMapping("/all-active")
	public List<Customer> getAllActive() {

		log.info("getAllActive start");
		return service.activeCustomers();

	}

	@PutMapping("/{id}/enable")
	public Customer enable(@PathVariable("id") Long customerId) {

		log.info("enable start");
		return service.toggleStatus(customerId, true);

	}

	@PutMapping("/{id}/disable")
	public Customer disable(@PathVariable("id") Long customerId) {

		log.info("disable start");
		return service.toggleStatus(customerId, false);

	}

	@GetMapping("/{id}/")
	public Customer getCustomer(@PathVariable("id") Long customerId) {

		log.info("getCustomer start");
		return service.getCustomer(customerId);

	}

	@PutMapping("/{id}/")
	public Customer update(@PathVariable("id") Long customerId, @RequestBody CustomerDTO customer) {

		log.info("update start");
		return service.update(customer, customerId);

	}

}
