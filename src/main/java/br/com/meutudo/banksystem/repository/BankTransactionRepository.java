package br.com.meutudo.banksystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.meutudo.banksystem.model.BankTransaction;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {

}
