package br.com.meutudo.banksystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.meutudo.banksystem.model.Bank;

public interface BankRepository extends JpaRepository<Bank, Long> {

}
