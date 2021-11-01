package br.com.meutudo.banksystem.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.meutudo.banksystem.exception.ResourceNotFoundException;
import br.com.meutudo.banksystem.model.Bank;
import br.com.meutudo.banksystem.repository.BankRepository;
import br.com.meutudo.banksystem.service.BankService;

public class BankServiceImpl implements BankService {

	@Autowired
	private BankRepository bankRepository;

	@Override
	public Bank createBank(Bank bank) {
		return bankRepository.save(bank);
	}

	@Override
	public Bank updateBank(Bank bank) {
		Optional<Bank> bankDb = this.bankRepository.findById(bank.getId());

		if (bankDb.isPresent()) {
			Bank bankToUpdate = bankDb.get();
			bankToUpdate.setName(bank.getName());
			bankToUpdate.setBankCode(bank.getBankCode());
			bankToUpdate.setPhone(bank.getPhone());
			bankRepository.save(bankToUpdate);
			return bankToUpdate;
		} else {
			throw new ResourceNotFoundException("Record not found with id : " + bank.getId());
		}
	}

	@Override
	public List<Bank> getBanks() {
		return this.bankRepository.findAll();
	}

	@Override
	public Bank getBankById(long bankId) {
		Optional<Bank> bankDb = this.bankRepository.findById(bankId);

		if (bankDb.isPresent()) {
			return bankDb.get();
		} else {
			throw new ResourceNotFoundException("Record not found with id : " + bankId);
		}
	}

	@Override
	public void deleteBank(long bankId) {
		Optional<Bank> bankDb = this.bankRepository.findById(bankId);

		if (bankDb.isPresent()) {
			this.bankRepository.delete(bankDb.get());
		} else {
			throw new ResourceNotFoundException("Record not found with id : " + bankId);
		}
	}
}
