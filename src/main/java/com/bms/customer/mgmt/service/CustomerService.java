package com.bms.customer.mgmt.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bms.customer.mgmt.domain.CustomerDTO;
import com.bms.customer.mgmt.entities.Customer;
import com.bms.customer.mgmt.entities.User;
import com.bms.customer.mgmt.exception.InvalidInputException;
import com.bms.customer.mgmt.exception.ResourceNotFoundException;
import com.bms.customer.mgmt.repository.CustomerRepository;
import com.bms.customer.mgmt.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RefreshScope
public class CustomerService {

	private static final String PROVIDED_CUSTOMER_ID_IS_INVALID_MSG = "Provided customer id is invalid!";

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	UserRepository userRepository;

	@Value("${user.name.already.exists.message}")
	private String userNameExistsMessage;

	public Customer create(CustomerDTO customer) {

		if (userRepository.existsByUsername(customer.getUsername())) {
			log.info("user name - {} already exists", customer.getUsername());
			throw new InvalidInputException(String.format(userNameExistsMessage, customer.getUsername()));
		}

		User user = new User();
		user.setActive(true);
		user.setUsername(customer.getUsername());
		user.setRoles("ROLE_USER");
		user.setPassword(new BCryptPasswordEncoder().encode(customer.getPassword()));
		Customer c = new Customer();
		BeanUtils.copyProperties(customer, c);
		c.setUpdatedDate(new Date());
		c.setCreatedDate(c.getUpdatedDate());
		c.setEnabled(true);
		userRepository.save(user);
		return customerRepository.save(c);

	}

	public Customer update(CustomerDTO customerDTO, Long customerId) {
		Optional<Customer> customer = customerRepository.findById(customerId);
		if (customer.isPresent()) {
			Customer c = customer.get();
			BeanUtils.copyProperties(customerDTO, c);
			c.setUpdatedDate(new Date());
			return customerRepository.save(c);
		}

		throw new ResourceNotFoundException(PROVIDED_CUSTOMER_ID_IS_INVALID_MSG);

	}

	public Customer toggleStatus(Long customerId, boolean enabled) {
		Optional<Customer> customer = customerRepository.findById(customerId);

		if (customer.isPresent()) {
			Customer c = customer.get();
			c.setEnabled(enabled);
			c.setUpdatedDate(new Date());
			return customerRepository.save(c);
		}

		throw new ResourceNotFoundException(PROVIDED_CUSTOMER_ID_IS_INVALID_MSG);
	}

	public List<Customer> allCustomers() {
		return customerRepository.findAll();

	}

	public List<Customer> activeCustomers() {
		return customerRepository.findByEnabledTrue();

	}

	public Customer getCustomer(Long customerId) {
		Optional<Customer> customer = customerRepository.findById(customerId);

		if (customer.isPresent()) {

			return customer.get();
		}

		throw new ResourceNotFoundException(PROVIDED_CUSTOMER_ID_IS_INVALID_MSG);

	}
}
