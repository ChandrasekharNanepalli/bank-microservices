package com.chandu.mapper;

import com.chandu.dto.CustomerDetailsVo;
import com.chandu.dto.CustomerVo;
import com.chandu.model.Customer;

public class CustomerMapper {
	
	public static  Customer mapToCustomer(Customer customer,CustomerVo customerVo) {
		customer.setEmail(customerVo.getEmail());
		customer.setMobileNumber(customerVo.getMobileNumber());
		customer.setName(customerVo.getName());
		return customer;
	}

	public static  CustomerVo mapToCustomerDto(CustomerVo customerVo,Customer customer) {
		customerVo.setEmail(customer.getEmail());
		customerVo.setMobileNumber(customer.getMobileNumber());
		customerVo.setName(customer.getName());
		return customerVo;
	}
	
	public static  CustomerDetailsVo mapToCustomerDetailDto(Customer customer,CustomerDetailsVo customerDetailsVo) {
		customerDetailsVo.setEmail(customer.getEmail());
		customerDetailsVo.setMobileNumber(customer.getMobileNumber());
		customerDetailsVo.setName(customer.getName());
		return customerDetailsVo;
	}

}
