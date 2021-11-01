package br.com.meutudo.banksystem.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.meutudo.banksystem.exception.ResourceNotFoundException;
import br.com.meutudo.banksystem.model.BankTransfer;
import br.com.meutudo.banksystem.repository.BankTransferRepository;
import br.com.meutudo.banksystem.service.BankTransferService;

public class BankTransferServiceImpl implements BankTransferService {

	@Autowired
	private BankTransferRepository bankTransferRepository;

	@Override
	public BankTransfer createBankTransfer(BankTransfer bankTransfer) {
		return bankTransferRepository.save(bankTransfer);
	}

	@Override
	public BankTransfer updateBankTransfer(BankTransfer bankTransfer) {
		Optional<BankTransfer> bankTransferDb = this.bankTransferRepository.findById(bankTransfer.getId());

		if (bankTransferDb.isPresent()) {
			BankTransfer bankTransferToUpdate = bankTransferDb.get();
			bankTransferToUpdate.setOriginAccount(bankTransfer.getOriginAccount());
			bankTransferToUpdate.setDestinyAccount(bankTransfer.getDestinyAccount());
			bankTransferToUpdate.setTransferValue(bankTransfer.getTransferValue());
			bankTransferRepository.save(bankTransferToUpdate);
			return bankTransferToUpdate;
		} else {
			throw new ResourceNotFoundException("Record not found with id : " + bankTransfer.getId());
		}
	}

	@Override
	public List<BankTransfer> getBankTransfers() {
		return this.bankTransferRepository.findAll();
	}

	@Override
	public BankTransfer getBankTransferById(long bankTransferId) {
		Optional<BankTransfer> bankTransferDb = this.bankTransferRepository.findById(bankTransferId);

		if (bankTransferDb.isPresent()) {
			return bankTransferDb.get();
		} else {
			throw new ResourceNotFoundException("Record not found with id : " + bankTransferId);
		}
	}

	@Override
	public void deleteBankTransfer(long bankTransferId) {
		Optional<BankTransfer> bankTransferDb = this.bankTransferRepository.findById(bankTransferId);

		if (bankTransferDb.isPresent()) {
			this.bankTransferRepository.delete(bankTransferDb.get());
		} else {
			throw new ResourceNotFoundException("Record not found with id : " + bankTransferId);
		}
	}

}
