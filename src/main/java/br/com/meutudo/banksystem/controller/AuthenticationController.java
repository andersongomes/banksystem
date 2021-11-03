package br.com.meutudo.banksystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.meutudo.banksystem.exception.ResourceNotFoundException;
import br.com.meutudo.banksystem.model.Token;
import br.com.meutudo.banksystem.model.User;
import br.com.meutudo.banksystem.service.TokenService;
import br.com.meutudo.banksystem.service.UserService;

@RestController
public class AuthenticationController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private TokenService tokenService;

	@PostMapping("/authenticate")
	public ResponseEntity<Token> authenticate(@RequestBody User user) {
		long userId = userService.login(user);
		if(userId != 0L) {
			return ResponseEntity.ok().body(this.tokenService.createToken(userId));			
		}
		throw new ResourceNotFoundException("The user name or password are incorrect!");
	}
}
