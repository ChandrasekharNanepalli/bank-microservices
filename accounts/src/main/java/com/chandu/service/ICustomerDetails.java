package com.chandu.service;

import com.chandu.dto.CustomerDetailsVo;

public interface ICustomerDetails {
	
	/**
	 * @param mobileNumber - input mobileNumber
	 * @return customer details based on mobile Number
	 */
	CustomerDetailsVo fetchCustomerDetails(String mobileNumber); 
}
