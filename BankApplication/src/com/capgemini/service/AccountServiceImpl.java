package com.capgemini.service;

import java.util.StringJoiner;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialBalanceException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;

public class AccountServiceImpl implements AccountService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.capgemini.service.AccountService#createAccount(int, int)
	 */

	AccountRepository accountRepository;

	public AccountServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public Account createAccount(int accountNumber, int amount) throws InsufficientInitialBalanceException {
		if (amount < 500) {
			throw new InsufficientInitialBalanceException();
		}
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setAmount(amount);
		if (accountRepository.save(account)) {
			return account;
		}
		return null;
	}

	@Override
	public int showBalance(int accountnumber) throws InvalidAccountNumberException {
		Account acc = accountRepository.searchAccount(accountnumber);
		if (acc == null) {
			throw new InvalidAccountNumberException();
		}
		return acc.getAmount();
	}

	@Override
	public int withdrawAmount(int accountnumber, int amount) throws InsufficientBalanceException,InvalidAccountNumberException {
		Account acc = accountRepository.searchAccount(accountnumber);
		if(acc==null){
			throw new InvalidAccountNumberException();
		}
		if (acc.getAmount() < amount) {
			throw new InsufficientBalanceException();
		}
		acc.setAmount(acc.getAmount() - amount);
		if (accountRepository.save(acc)) {
			return acc.getAmount();
		}
		return 0;
	}

	@Override
	public int depositAmonut(int accountnumber, int amount) throws InvalidAccountNumberException {
		Account acc = accountRepository.searchAccount(accountnumber);
		if (acc == null) {
			throw new InvalidAccountNumberException();
		}
		acc.setAmount(acc.getAmount() + amount);
		if (accountRepository.save(acc)) {
			return acc.getAmount();
		}
		return 0;
	}

	@Override
	public StringJoiner fundTransfer(int issueraccountnumber, int acquireraccountnumber, int amount)
			throws InvalidAccountNumberException {
		Account issueraccount = accountRepository.searchAccount(issueraccountnumber);
		Account acquireraccount = accountRepository.searchAccount(acquireraccountnumber);
		if (issueraccount == null || acquireraccount == null) {
			throw new InvalidAccountNumberException();
		}
		acquireraccount.setAmount(acquireraccount.getAmount() + amount);
		issueraccount.setAmount(issueraccount.getAmount() - amount);
		accountRepository.save(acquireraccount);
		accountRepository.save(issueraccount);
		return fundTransfer(issueraccountnumber, acquireraccountnumber, amount);

	}

}
