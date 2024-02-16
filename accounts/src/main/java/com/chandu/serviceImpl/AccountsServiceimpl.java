package com.chandu.serviceImpl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.chandu.constants.AccountsConstants;
import com.chandu.dto.AccountVo;
import com.chandu.dto.CustomerVo;
import com.chandu.exception.CustomerAlreadyPresentException;
import com.chandu.exception.ResourceNotFoundException;
import com.chandu.mapper.AccountsMapper;
import com.chandu.mapper.CustomerMapper;
import com.chandu.model.Accounts;
import com.chandu.model.Customer;
import com.chandu.repo.AccountsRepo;
import com.chandu.repo.CustomerRepo;
import com.chandu.service.IAccountsService;

import lombok.AllArgsConstructor;




@Service @AllArgsConstructor
public class AccountsServiceimpl implements IAccountsService {
	
	CustomerRepo customerRepo;
	AccountsRepo accountsRepo;

	@Override
	public void createAccount(CustomerVo customerVo) {
		Customer customer=CustomerMapper.mapToCustomer(new Customer(),customerVo);
		Optional<Customer> optionalCustomer=customerRepo.findByMobileNumber(customerVo.getMobileNumber());
		if(optionalCustomer.isPresent()) {
			throw new CustomerAlreadyPresentException("Customer already registred with this Mobile Number"+customerVo.getMobileNumber());
		}
//		customer.setCreatedAt(LocalDateTime.now());
//		customer.setCreatedBy("Anonymous");
		Customer savedCustomer=customerRepo.save(customer);
		accountsRepo.save(createNewAccount(savedCustomer));
		 
		
	}
	
	/**
	 * @param customer
	 * @return 
	 */
	public Accounts createNewAccount(Customer customer) {
		Accounts newAcoount=new Accounts();
		newAcoount.setCustomerId(customer.getCustomerId());
		long randomAccNumber=1000000000L+new Random().nextInt(900000000);
		newAcoount.setAccountNumber(randomAccNumber);
		newAcoount.setAccountType(AccountsConstants.SAVINGS);
		newAcoount.setBranchAddress(AccountsConstants.ADDRESS);
//		newAcoount.setCreatedAt(LocalDateTime.now());
//		newAcoount.setCreatedBy("Anonymous");
		return newAcoount;
	}

	@Override
	public CustomerVo fetchAccount(String mobile) {
		
		Customer customer=customerRepo.findByMobileNumber(mobile)
									  	.orElseThrow(()->  new ResourceNotFoundException("customer","mobileNumber" , mobile));
		
		Accounts accounts=accountsRepo.findByCustomerId(customer.getCustomerId())
										.orElseThrow(()->new ResourceNotFoundException("Account", "customer", customer.getCustomerId().toString()));
		
		CustomerVo customerVo=CustomerMapper.mapToCustomerDto(new CustomerVo(), customer);
		customerVo.setAccountVo(AccountsMapper.mapToAccountDto(new AccountVo(), accounts));
		return customerVo;
	}

	
	
	@Override
	public boolean updateAccount(CustomerVo customerVo) {
		boolean update =false;
		AccountVo accountVo=customerVo.getAccountVo();
		if(accountVo!=null) {
			Accounts accounts=accountsRepo.findById(accountVo.getAccountsNumber()).orElseThrow(
					()->  new ResourceNotFoundException("Account", "AccountNumber", accountVo.getAccountsNumber().toString())
					);
			
			AccountsMapper.mapToAccount(accounts, accountVo);
			accounts=accountsRepo.save(accounts);
			Long customerId=accounts.getCustomerId();
			Customer customer=customerRepo.findById(customerId).orElseThrow(
					()->new ResourceNotFoundException("Customer","CustomerId",customerId.toString())
					);
			CustomerMapper.mapToCustomer(customer, customerVo);
			customerRepo.save(customer);
			update=true;
		}
		return update;
		
		
		
		
	}


	@Override
	public boolean deleteAccount(String mobileNumber) {
		Customer customer=customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
				()->new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber)
				);
		
		accountsRepo.deleteByCustomerId(customer.getCustomerId());
		customerRepo.deleteById(customer.getCustomerId());
		return true;
		
	}

	

} 
