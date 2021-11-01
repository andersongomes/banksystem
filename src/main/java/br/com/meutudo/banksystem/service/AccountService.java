package br.com.meutudo.banksystem.service;

import java.util.List;

import br.com.meutudo.banksystem.model.Account;

public interface AccountService {
	Account createAccount(Account account);

	Account updateAccount(Account account);

	List<Account> getAccounts();

	Account getAccountById(long accountId);

	void deleteAccount(long accountId);
}
