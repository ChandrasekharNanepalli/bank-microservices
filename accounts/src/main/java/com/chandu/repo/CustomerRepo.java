package com.chandu.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chandu.model.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

	
	Optional<Customer> findByMobileNumber(String mobileNumber);
}
