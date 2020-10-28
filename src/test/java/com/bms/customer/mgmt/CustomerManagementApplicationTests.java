package com.bms.customer.mgmt;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.ResourceAccessException;

import com.bms.customer.mgmt.entities.Customer;
import com.bms.customer.mgmt.entities.User;
import com.bms.customer.mgmt.repository.CustomerRepository;
import com.bms.customer.mgmt.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = TestConfig.class, webEnvironment = WebEnvironment.RANDOM_PORT, properties = { "server.port:0",
		"spring.cloud.config.discovery.enabled:false", "spring.cloud.config.enabled:false",
		"spring.profiles.active:test" })
@TestPropertySource(locations = "classpath:junit.properties")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
class CustomerManagementApplicationTests {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	CustomerRepository customerRepository;

	@MockBean
	UserRepository userRepository;

	@Autowired
	TestRestTemplate testRestTemplate;

	public static final String REGISTER_URL = "/v1/customer/register";
	public static final String UPDATE_URL = "/v1/customer/1/";
	public static final String GETALL_URL = "/v1/customer/all";
	public static final String GETALL_ACTIVE_URL = "/v1/customer/all-active";
	public static final String ENABLE_URL = "/v1/customer/1/enable";
	public static final String DISABLE_URL = "/v1/customer/1/disable";

	@Test
	public void testRegister() throws JsonProcessingException, Exception {

		String json = "{\"addressLineOne\": \"Kollamkudimugal\",\"addressLineTwo\": \"Kakkanad\",\"city\": \"Kochi\",\"country\": \"India\",\"dob\": \"1991-01-01\",\"email\": \"arunxavy@gmail.com\",\"firstName\": \"Ajay\",\"gender\": \"M\",\"lastName\": \"K\",\"mobileNumber\": \"9496358072\",\"nationality\": \"Indian\",\"pan\": \"AQXPA1792H\",\"password\": \"12345678\",\"pincode\": \"682021\",\"state\": \"Kerala\",\"username\": \"ajay\"}";

		doReturn(false).when(userRepository).existsByUsername(Mockito.anyString());
		Customer c = new Customer();
		c.setCustomerId(Long.valueOf(10));
		doReturn(c).when(customerRepository).save(Mockito.any());

		User u = new User();
		doReturn(u).when(userRepository).save(Mockito.any());

		mockMvc.perform(post(REGISTER_URL).contentType("application/json").content(json))
				.andExpect(status().isCreated());

	}

	@Test
	public void testRegisterMandatoryFields() throws JsonProcessingException, Exception {

		String json = "{\"addressLineOne\": \"Kollamkudimugal\",\"addressLineTwo\": \"Kakkanad\",\"city\": \"Kochi\",\"country\": \"India\",\"dob\": \"1991-01-01\",\"email\": \"arunxavy@gmail.com\",\"firstName\": \"Ajay\",\"gender\": \"M\",\"lastName\": \"K\",\"nationality\": \"Indian\",\"pan\": \"AQXPA1792H\",\"password\": \"12345678\",\"pincode\": \"682021\",\"state\": \"Kerala\",\"username\": \"ajay\"}";

		doReturn(false).when(userRepository).existsByUsername(Mockito.anyString());
		Customer c = new Customer();
		c.setCustomerId(Long.valueOf(10));
		doReturn(c).when(customerRepository).save(Mockito.any());

		User u = new User();
		doReturn(u).when(userRepository).save(Mockito.any());

		mockMvc.perform(post(REGISTER_URL).contentType("application/json").content(json))
				.andExpect(status().isBadRequest());

	}

	@Test
	public void testRegisterUserNameExists() throws JsonProcessingException, Exception {

		String json = "{\"addressLineOne\": \"Kollamkudimugal\",\"addressLineTwo\": \"Kakkanad\",\"city\": \"Kochi\",\"country\": \"India\",\"dob\": \"1991-01-01\",\"email\": \"arunxavy@gmail.com\",\"firstName\": \"Ajay\",\"gender\": \"M\",\"lastName\": \"K\",\"mobileNumber\": \"9496358072\",\"nationality\": \"Indian\",\"pan\": \"AQXPA1792H\",\"password\": \"12345678\",\"pincode\": \"682021\",\"state\": \"Kerala\",\"username\": \"ajay\"}";

		doReturn(true).when(userRepository).existsByUsername(Mockito.anyString());
		Customer c = new Customer();
		c.setCustomerId(Long.valueOf(10));
		doReturn(c).when(customerRepository).save(Mockito.any());

		User u = new User();
		doReturn(u).when(userRepository).save(Mockito.any());

		mockMvc.perform(post(REGISTER_URL).contentType("application/json").content(json))
				.andExpect(status().isBadRequest());

	}

	@Test
	public void testUpdateFail() throws JsonProcessingException, Exception {

		String json = "{\"addressLineOne\": \"Kollamkudimugal\",\"addressLineTwo\": \"Kakkanad\",\"city\": \"Kochi\",\"country\": \"India\",\"dob\": \"1991-01-01\",\"email\": \"arunxavy@gmail.com\",\"firstName\": \"Ajay\",\"gender\": \"M\",\"lastName\": \"K\",\"mobileNumber\": \"9496358072\",\"nationality\": \"Indian\",\"pan\": \"AQXPA1792H\",\"password\": \"12345678\",\"pincode\": \"682021\",\"state\": \"Kerala\",\"username\": \"ajay\"}";
		Optional<Customer> customer = Optional.empty();
		doReturn(customer).when(customerRepository).findById(Mockito.any());

		mockMvc.perform(put(UPDATE_URL).contentType("application/json").content(json)).andExpect(status().isNotFound());

	}

	@Test
	public void testUpdateSuccess() throws JsonProcessingException, Exception {

		String json = "{\"addressLineOne\": \"Kollamkudimugal\",\"addressLineTwo\": \"Kakkanad\",\"city\": \"Kochi\",\"country\": \"India\",\"dob\": \"1991-01-01\",\"email\": \"arunxavy@gmail.com\",\"firstName\": \"Ajay\",\"gender\": \"M\",\"lastName\": \"K\",\"mobileNumber\": \"9496358072\",\"nationality\": \"Indian\",\"pan\": \"AQXPA1792H\",\"password\": \"12345678\",\"pincode\": \"682021\",\"state\": \"Kerala\",\"username\": \"ajay\"}";
		Optional<Customer> customer = Optional.of(new Customer());
		doReturn(customer).when(customerRepository).findById(Mockito.any());

		mockMvc.perform(put(UPDATE_URL).contentType("application/json").content(json)).andExpect(status().is2xxSuccessful());

	}

	@Test
	public void testUpdateError() throws JsonProcessingException, Exception {

		String json = "{\"addressLineOne\": \"Kollamkudimugal\",\"addressLineTwo\": \"Kakkanad\",\"city\": \"Kochi\",\"country\": \"India\",\"dob\": \"1991-01-01\",\"email\": \"arunxavy@gmail.com\",\"firstName\": \"Ajay\",\"gender\": \"M\",\"lastName\": \"K\",\"mobileNumber\": \"9496358072\",\"nationality\": \"Indian\",\"pan\": \"AQXPA1792H\",\"password\": \"12345678\",\"pincode\": \"682021\",\"state\": \"Kerala\",\"username\": \"ajay\"}";
		doThrow(new ResourceAccessException("unknown")).when(customerRepository).findById(Mockito.any());

		mockMvc.perform(put(UPDATE_URL).contentType("application/json").content(json))
				.andExpect(status().is5xxServerError());

	}

	@Test
	public void testGetAll() throws JsonProcessingException, Exception {

		mockMvc.perform(get(GETALL_URL).contentType("application/json")).andExpect(status().is2xxSuccessful());

	}

	@Test
	public void testGetAllActive() throws JsonProcessingException, Exception {

		mockMvc.perform(get(GETALL_ACTIVE_URL).contentType("application/json")).andExpect(status().is2xxSuccessful());

	}

	@Test
	public void testEnable() throws JsonProcessingException, Exception {
		Optional<Customer> customer = Optional.of(new Customer());
		doReturn(customer).when(customerRepository).findById(Mockito.any());

		mockMvc.perform(put(ENABLE_URL).contentType("application/json")).andExpect(status().is2xxSuccessful());

	}

	@Test
	public void testDisable() throws JsonProcessingException, Exception {
		Optional<Customer> customer = Optional.of(new Customer());
		doReturn(customer).when(customerRepository).findById(Mockito.any());

		mockMvc.perform(put(DISABLE_URL).contentType("application/json")).andExpect(status().is2xxSuccessful());

	}
}
