package br.com.meutudo.banksystem.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.meutudo.banksystem.exception.ResourceNotFoundException;
import br.com.meutudo.banksystem.model.Account;
import br.com.meutudo.banksystem.repository.AccountRepository;
import br.com.meutudo.banksystem.service.AccountService;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Account createAccount(Account account) {
		return accountRepository.save(account);
	}

	@Override
	public Account updateAccount(Account account) {
		Optional<Account> accountDb = this.accountRepository.findById(account.getId());

		if (accountDb.isPresent()) {
			Account accountToUpdate = accountDb.get();
			accountToUpdate.setUser(accountToUpdate.getUser());
			accountToUpdate.setBank(accountToUpdate.getBank());
			accountToUpdate.setAgency(accountToUpdate.getAgency());
			accountToUpdate.setAccountNumber(accountToUpdate.getAccountNumber());
			accountToUpdate.setOperation(accountToUpdate.getOperation());

			accountRepository.save(accountToUpdate);
			return accountToUpdate;
		} else {
			throw new ResourceNotFoundException("Record not found with id : " + account.getId());
		}
	}

	@Override
	public List<Account> getAccounts() {
		return this.accountRepository.findAll();
	}

	@Override
	public Account getAccountById(long accountId) {
		Optional<Account> acccountDb = this.accountRepository.findById(accountId);

		if (acccountDb.isPresent()) {
			return acccountDb.get();
		} else {
			throw new ResourceNotFoundException("Record not found with id : " + accountId);
		}
	}

	@Override
	public void deleteAccount(long accountId) {
		Optional<Account> accountDb = this.accountRepository.findById(accountId);

		if (accountDb.isPresent()) {
			this.accountRepository.delete(accountDb.get());
		} else {
			throw new ResourceNotFoundException("Record not found with id : " + accountId);
		}
	}

}
