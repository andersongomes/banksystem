package br.com.meutudo.banksystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.meutudo.banksystem.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
