package br.com.meutudo.banksystem.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

	@PersistenceContext
	private EntityManager em;

	@Override
	public BankTransaction createBankTransaction(BankTransaction bankTransaction) {
		return this.bankTransactionRepository.save(bankTransaction);
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
			this.bankTransactionRepository.save(bankTransactionToUpdate);
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

	@Override
	public Double accountBalance(long accountId) {
		Query query = em.createQuery(
				"SELECT bt FROM BANK_TRANSACTION bt WHERE bt.account.id = :id AND bt.transactionReversalDate IS NULL",
				BankTransaction.class);
		query.setParameter("id", accountId);
		List<BankTransaction> transactions = (List<BankTransaction>) query.getResultList();
		Double value = 0.0;
		for (BankTransaction t : transactions) {
			value += t.getTransactionValue() * t.getTransactionFactor();
		}
		BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN);
		return bd.doubleValue();
	}

	@Override
	public List<BankTransaction> getBankTransactionByBankTransfer(long bankTransferId) {
		Query query = em.createQuery(
				"SELECT bt FROM BANK_TRANSACTION bt WHERE bt.bankTransfer.id = :id AND bt.transactionReversalDate IS NULL",
				BankTransaction.class);
		query.setParameter("id", bankTransferId);
		try {
			List<BankTransaction> transactions = (List<BankTransaction>) query.getResultList();
			return transactions;
		} catch (NoResultException e) {
			return null;
		} catch (Exception ee) {
			return null;
		}
	}

	@Override
	public List<BankTransaction> getFutureBankTransactionByAccount(long accountId) {
		Query query = em.createQuery(
				"SELECT bt FROM BANK_TRANSACTION bt WHERE bt.account.id = :accountId AND bt.transactionReversalDate IS NULL AND bt.transactionDate IS NULL",
				BankTransaction.class);
		query.setParameter("accountId", accountId);
		try {
			List<BankTransaction> transactions = (List<BankTransaction>) query.getResultList();
			return transactions;
		} catch (NoResultException e) {
			return null;
		} catch (Exception ee) {
			return null;
		}
	}

}
