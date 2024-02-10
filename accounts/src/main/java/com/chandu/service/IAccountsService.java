package com.chandu.service;

import com.chandu.dto.CustomerVo;

public interface IAccountsService {
	
	/**
	 * @param customerVo
	 */
	public void createAccount(CustomerVo customerVo);
	
	/**
	 * @param mobile
	 * @return
	 */
	CustomerVo fetchAccount(String mobile);
	
	/**
	 * @param customerVo
	 * @return
	 */
	boolean updateAccount(CustomerVo customerVo);
	
	/**
	 * @param MobileNumber
	 * @return
	 */
	boolean deleteAccount(String mobileNumber);

}
