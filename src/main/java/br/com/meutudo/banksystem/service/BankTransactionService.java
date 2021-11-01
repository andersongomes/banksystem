package br.com.meutudo.banksystem.service;

import java.util.List;

import br.com.meutudo.banksystem.model.BankTransaction;

public interface BankTransactionService {
	BankTransaction createBankTransaction(BankTransaction bankTransaction);

	BankTransaction updateBankTransaction(BankTransaction bankTransaction);

    List<BankTransaction> getBankTransactions();

    BankTransaction getBankTransactionById(long bankTransactionId);

    void deleteBankTransaction(long bankTransactionId);
}
