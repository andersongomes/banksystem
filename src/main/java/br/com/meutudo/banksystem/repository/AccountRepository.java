package br.com.meutudo.banksystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.meutudo.banksystem.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
	