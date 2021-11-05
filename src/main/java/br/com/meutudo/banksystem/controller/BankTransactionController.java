package br.com.meutudo.banksystem.controller;

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

import br.com.meutudo.banksystem.model.BankTransaction;
import br.com.meutudo.banksystem.model.User;
import br.com.meutudo.banksystem.service.BankTransactionService;
import br.com.meutudo.banksystem.service.TokenService;

@RestController
public class BankTransactionController {
	@Autowired
	private BankTransactionService bankTransactionService;

	@Autowired
	private TokenService tokenService;

	@GetMapping("/transaction/list")
	public ResponseEntity<List<BankTransaction>> getBankTransactions(@RequestHeader String authentication) {
		User user = this.tokenService.validateToken(authentication);
		if (user != null) {
			return ResponseEntity.ok().body(this.bankTransactionService.getBankTransactions());
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	}

	@GetMapping("/transaction/{id}")
	public ResponseEntity<BankTransaction> getBankTransactionById(@PathVariable long id,
			@RequestHeader String authentication) {
		User user = this.tokenService.validateToken(authentication);
		if (user != null) {
			return ResponseEntity.ok().body(this.bankTransactionService.getBankTransactionById(id));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	}

	@PostMapping("/transaction/create")
	public ResponseEntity<BankTransaction> createBankTransaction(@RequestBody BankTransaction bankTransaction,
			@RequestHeader String authentication) {
		User user = this.tokenService.validateToken(authentication);
		if (user != null) {
			return ResponseEntity.ok().body(this.bankTransactionService.createBankTransaction(bankTransaction));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	}

	@PutMapping("/transaction/update/{id}")
	public ResponseEntity<BankTransaction> updateBankTransaction(@PathVariable long id,
			@RequestBody BankTransaction bankTransaction, @RequestHeader String authentication) {
		User user = this.tokenService.validateToken(authentication);
		if (user != null) {
			bankTransaction.setId(id);
			return ResponseEntity.ok().body(this.bankTransactionService.updateBankTransaction(bankTransaction));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	}

	@DeleteMapping("/transaction/delete/{id}")
	public ResponseEntity<String> deleteBankTransaction(@PathVariable long id, @RequestHeader String authentication) {
		User user = this.tokenService.validateToken(authentication);
		if (user != null) {
			this.bankTransactionService.deleteBankTransaction(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid authentication!");
	}
}
