package com.chandu.mapper;

import com.chandu.dto.AccountVo;
import com.chandu.model.Accounts;

public class AccountsMapper {
	
	public static  Accounts mapToAccount(Accounts accounts,AccountVo accountVo) {
		accounts.setAccountNumber(accountVo.getAccountsNumber());
		accounts.setAccountType(accountVo.getAccountType());
		accounts.setBranchAddress(accountVo.getBranchAddress());
		return accounts;
	}

	public static  AccountVo mapToAccountDto(AccountVo accountVo,Accounts accounts) {
		accountVo.setAccountsNumber(accounts.getAccountNumber());
		accountVo.setAccountType(accounts.getAccountType());
		accountVo.setBranchAddress(accounts.getBranchAddress());
		return accountVo;
	}
}
