package br.com.meutudo.banksystem.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import br.com.meutudo.banksystem.model.Bank;
import br.com.meutudo.banksystem.model.User;
import br.com.meutudo.banksystem.service.BankService;
import br.com.meutudo.banksystem.service.TokenService;

@RestController
public class BankController {

	@Autowired
	private BankService bankService;

	@Autowired
	private TokenService tokenService;

	@GetMapping("/bank/list")
	public ResponseEntity<List<Bank>> getBanks(@RequestHeader String authentication) {
		User user = this.tokenService.validateToken(authentication);
		if (user != null) {
			return ResponseEntity.ok().body(this.bankService.getBanks());
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	}

	@GetMapping("/bank/{id}")
	public ResponseEntity<Bank> getBankById(@PathVariable long id, @RequestHeader String authentication) {
		User user = this.tokenService.validateToken(authentication);
		if (user != null) {
			return ResponseEntity.ok().body(this.bankService.getBankById(id));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	}

	@PostMapping("/bank/create")
	public ResponseEntity<Bank> createBank(@RequestBody Bank bank, @RequestHeader String authentication) {
		User user = this.tokenService.validateToken(authentication);
		if (user != null) {
			bank.setActive(true);
			bank.setCreationDate(new Date());
			return ResponseEntity.ok().body(this.bankService.createBank(bank));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	}

	@PutMapping("/bank/update/{id}")
	public ResponseEntity<Bank> updateBank(@PathVariable long id, @RequestBody Bank bank,
			@RequestHeader String authentication) {
		User user = this.tokenService.validateToken(authentication);
		if (user != null) {
			bank.setId(id);
			return ResponseEntity.ok().body(this.bankService.updateBank(bank));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	}

	@DeleteMapping("/bank/delete/{id}")
	public ResponseEntity<String> deleteBank(@PathVariable long id, @RequestHeader String authentication) {
		User user = this.tokenService.validateToken(authentication);
		if (user != null) {
			this.bankService.deleteBank(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	}
}