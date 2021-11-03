package br.com.meutudo.banksystem.service;

import br.com.meutudo.banksystem.model.Token;
import br.com.meutudo.banksystem.model.User;

public interface TokenService {
	Token createToken(long userId);
	
	User validateToken(String token);
}
