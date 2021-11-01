package br.com.meutudo.banksystem.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.meutudo.banksystem.exception.ResourceNotFoundException;
import br.com.meutudo.banksystem.model.BankTransaction;
import br.com.meutudo.banksystem.repository.BankTransactionRepository;
import br.com.meutudo.banksystem.service.BankTransactionService;

@Service
@Transactional
public class BankTransactionServiceImpl implements BankTransactionService {

	@Autowired
	private BankTransactionRepository bankTransactionRepository;

	@Override
	public BankTransaction createBankTransaction(BankTransaction bankTransaction) {
		return bankTransactionRepository.save(bankTransaction);
	}

	@Override
	public BankTransaction updateBankTransaction(BankTransaction bankTransaction) {
		Optional<BankTransaction> bankTransactionDb = this.bankTransactionRepository.findById(bankTransaction.getId());

		if (bankTransactionDb.isPresent()) {
			BankTransaction bankTransactionToUpdate = bankTransactionDb.get();
			bankTransactionToUpdate.setTransactionSchedulingDate(bankTransaction.getTransactionSchedulingDate());
			bankTransactionToUpdate.setTransactionDate(bankTransaction.getTransactionDate());
			bankTransactionToUpdate.setTransactionFactor(bankTransaction.getTransactionFactor());
			bankTransactionToUpdate.setTransactionValue(bankTransaction.getTransactionValue());
			bankTransactionToUpdate.setTransactionReversalDate(bankTransaction.getTransactionReversalDate());
			bankTransactionToUpdate.setAccount(bankTransaction.getAccount());
			bankTransactionToUpdate.setUser(bankTransaction.getUser());
			bankTransactionToUpdate.setBankTransfer(bankTransaction.getBankTransfer());
			bankTransactionRepository.save(bankTransactionToUpdate);
			return bankTransactionToUpdate;
		} else {
			throw new ResourceNotFoundException("Record not found with id : " + bankTransaction.getId());
		}
	}

	@Override
	public List<BankTransaction> getBankTransactions() {
		return this.bankTransactionRepository.findAll();
	}

	@Override
	public BankTransaction getBankTransactionById(long bankTransactionId) {
		Optional<BankTransaction> bankTransactionDb = this.bankTransactionRepository.findById(bankTransactionId);

		if (bankTransactionDb.isPresent()) {
			return bankTransactionDb.get();
		} else {
			throw new ResourceNotFoundException("Record not found with id : " + bankTransactionId);
		}
	}

	@Override
	public void deleteBankTransaction(long bankTransactionId) {
		Optional<BankTransaction> bankTransactionDb = this.bankTransactionRepository.findById(bankTransactionId);

		if (bankTransactionDb.isPresent()) {
			this.bankTransactionRepository.delete(bankTransactionDb.get());
		} else {
			throw new ResourceNotFoundException("Record not found with id : " + bankTransactionId);
		}
	}

}
