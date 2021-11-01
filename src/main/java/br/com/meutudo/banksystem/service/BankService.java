package br.com.meutudo.banksystem.service;

import java.util.List;

import br.com.meutudo.banksystem.model.Bank;

public interface BankService {
	Bank createBank(Bank bank);

    Bank updateBank(Bank bank);

    List<Bank> getBanks();

    Bank getBankById(long bankId);

    void deleteBank(long bankId);
}
