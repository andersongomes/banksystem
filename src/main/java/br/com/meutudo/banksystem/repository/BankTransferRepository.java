package br.com.meutudo.banksystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.meutudo.banksystem.model.BankTransfer;

public interface BankTransferRepository extends JpaRepository<BankTransfer, Long> {

}
