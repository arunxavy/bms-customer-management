package com.bms.customer.mgmt.domain;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {

	private Long customerId;

	@NotBlank(message = "Pan Number is mandatory")
	private String pan;

	@NotBlank(message = "Username is mandatory")
	private String username;

	@NotBlank(message = "First Name is mandatory")
	private String firstName;

	private String lastName;

	private String gender;

	@NotNull(message = "Date of Birth is mandatory")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dob;

	@NotBlank(message = "Email Address is mandatory")
	private String email;

	@NotBlank(message = "Mobile Number is mandatory")
	@Size(max = 13)
	private String mobileNumber;

	@Size(max = 50)
	private String addressLineOne;

	@Size(max = 50)
	private String addressLineTwo;

	@Size(max = 10)
	private String pincode;

	private String city;

	private String state;

	private String country;

	private String nationality;

	private boolean enabled;

	@JsonProperty(access = Access.WRITE_ONLY)
	@Setter(value = AccessLevel.NONE)
	private String password;

}
