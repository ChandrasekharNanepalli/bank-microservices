package com.chandu.serviceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chandu.dto.AccountVo;
import com.chandu.dto.CardsVo;
import com.chandu.dto.CustomerDetailsVo;
import com.chandu.dto.CustomerVo;
import com.chandu.dto.LoansVo;
import com.chandu.exception.ResourceNotFoundException;
import com.chandu.mapper.AccountsMapper;
import com.chandu.mapper.CustomerMapper;
import com.chandu.model.Accounts;
import com.chandu.model.Customer;
import com.chandu.repo.AccountsRepo;
import com.chandu.repo.CustomerRepo;
import com.chandu.service.ICustomerDetails;
import com.chandu.service.clients.CardsFeginClient;
import com.chandu.service.clients.LoansFeginClient;

import io.swagger.v3.oas.annotations.servers.Server;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerDetails {

	private AccountsRepo accountsRepo;
	private CustomerRepo customerRepo;
	private CardsFeginClient cardsFeginClient;
	private LoansFeginClient loansFeginClient;

	@Override
	public CustomerDetailsVo fetchCustomerDetails(String mobileNumber) {
		Customer customer = customerRepo.findByMobileNumber(mobileNumber)
				.orElseThrow(() -> new ResourceNotFoundException("customer", "mobileNumber", mobileNumber));

		Accounts accounts = accountsRepo.findByCustomerId(customer.getCustomerId()).orElseThrow(
				() -> new ResourceNotFoundException("Account", "customer", customer.getCustomerId().toString()));

		CustomerVo customerVo = CustomerMapper.mapToCustomerDto(new CustomerVo(), customer);
		customerVo.setAccountVo(AccountsMapper.mapToAccountDto(new AccountVo(), accounts));
		
		CustomerDetailsVo customerDetailsVo=CustomerMapper.mapToCustomerDetailDto(customer, new CustomerDetailsVo());
		customerDetailsVo.setAccountVo(AccountsMapper.mapToAccountDto(new AccountVo(), accounts));
		
		ResponseEntity<LoansVo> loansVoResponseEntity=loansFeginClient.fetchLoanDetails(mobileNumber);
		customerDetailsVo.setLoansVo(loansVoResponseEntity.getBody());
		
		ResponseEntity<CardsVo> cardsVoResponseEntity=cardsFeginClient.fetchCardDetails(mobileNumber);
		customerDetailsVo.setCardsVo(cardsVoResponseEntity.getBody());
		
		return customerDetailsVo;
	}

}
