package com.capgemini.service;

import java.util.StringJoiner;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialBalanceException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Account;

public interface AccountService {

	Account createAccount(int accountNumber, int amount) throws InsufficientInitialBalanceException;
    public int showBalance(int accountnumber) throws InvalidAccountNumberException;
    public int withdrawAmount(int accountnumber ,int amount) throws InsufficientBalanceException,InvalidAccountNumberException;
    public int depositAmonut(int accountnumber,int amount)throws InvalidAccountNumberException;
    public StringJoiner fundTransfer(int issueraccountnumber,int acquireraccountnumber,int amount)throws InvalidAccountNumberException;
}