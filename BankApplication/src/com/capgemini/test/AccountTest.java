package com.capgemini.test;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.assertEquals;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialBalanceException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;
import static org.mockito.Mockito.when;
public class AccountTest {

	@Mock
	AccountRepository accountRepository;
	
	AccountService accountService;
	
	
	
	
	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		accountService = new AccountServiceImpl(accountRepository);
	}

	/*
	 * create account
	 * 1. when the amount is less than 500 system should throw exception
	 * 2. when the valid info is passed account should be created successfully
	 */
	
	@Test(expected=com.capgemini.exceptions.InsufficientInitialBalanceException.class)
	public void whenTheAmountIsLessThan500SystemShouldThrowException() throws InsufficientInitialBalanceException
	{
		accountService.createAccount(101, 400);
	}
	
	@Test
	public void whenTheValidInfoIsPassedAccountShouldBeCreatedSuccessfully() throws InsufficientInitialBalanceException
	{
		Account account = new Account(101,5000);
		
		
		when(accountRepository.save(account)).thenReturn(true);
		
		assertEquals(account, accountService.createAccount(101, 5000));
		
	}
	
	/*
	 * When Invalid account number is entered the exception should be thrown
	 * When valid account no is passed  it should show the balance
	 * 
	 * */
	
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void whenAccountNuberIsIncorrectSystemShouldThrowException() throws InvalidAccountNumberException
	{
		accountService.showBalance(254);
	}
	
	@Test
	public void whenValidAccountNumberIsPassedSytemShouldShowBalance() throws InvalidAccountNumberException{
		Account account = new Account(101,5000);		
		
		assertEquals(5000, accountService.showBalance(101));
		
	}
	
	
	/*
	 * When Invalid account number is passed exception should be shown
	 * when whthdraw amount is greater than present amount exception insufficient balance should be shown
	 
	 * */
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void whenAccountNuberIsIncorrectforWithdrawSystemShouldThrowException() throws InvalidAccountNumberException
	{
		int accountno=1234;
		Account account = new Account(101,5000);
	
	
	when(accountRepository.searchAccount(1234)).thenReturn(null);
		
	}
}
