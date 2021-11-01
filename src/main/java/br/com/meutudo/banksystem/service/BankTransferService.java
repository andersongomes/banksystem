package br.com.meutudo.banksystem.service;

import java.util.List;

import br.com.meutudo.banksystem.model.BankTransfer;

public interface BankTransferService {
	BankTransfer createBankTransfer(BankTransfer bankTransfer);

	BankTransfer updateBankTransfer(BankTransfer bankTransfer);

	List<BankTransfer> getBankTransfers();

	BankTransfer getBankTransferById(long bankTransferId);

	void deleteBankTransfer(long bankTransferId);
}
