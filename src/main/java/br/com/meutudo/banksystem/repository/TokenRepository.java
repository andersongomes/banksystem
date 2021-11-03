package br.com.meutudo.banksystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.meutudo.banksystem.model.Token;

public interface TokenRepository extends JpaRepository<Token, String> {

}
