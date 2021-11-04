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

import br.com.meutudo.banksystem.model.Account;
import br.com.meutudo.banksystem.model.BankTransaction;
import br.com.meutudo.banksystem.model.User;
import br.com.meutudo.banksystem.service.AccountService;
import br.com.meutudo.banksystem.service.BankTransactionService;
import br.com.meutudo.banksystem.service.TokenService;

@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private BankTransactionService bankTransactionService;

	@GetMapping("/account/list")
	public ResponseEntity<List<Account>> getAccounts() {
		return ResponseEntity.ok().body(this.accountService.getAccounts());
	}

	@GetMapping("/account/{id}")
	public ResponseEntity<Account> getAccountById(@PathVariable long id) {
		return ResponseEntity.ok().body(this.accountService.getAccountById(id));
	}

	@PostMapping("/account/create")
	public ResponseEntity<Account> createAccount(@RequestBody Account account) {
		account.setActive(true);
		account.setCreationDate(new Date());
		return ResponseEntity.ok().body(this.accountService.createAccount(account));
	}

	@PutMapping("/account/update/{id}")
	public ResponseEntity<Account> updateAccount(@PathVariable long id, @RequestBody Account account) {
		account.setId(id);
		return ResponseEntity.ok().body(this.accountService.updateAccount(account));
	}

	@DeleteMapping("/account/delete/{id}")
	public HttpStatus deleteAccount(@PathVariable long id) {
		this.accountService.deleteAccount(id);
		return HttpStatus.OK;
	}

	@PostMapping("/account/deposit/{id}")
	public ResponseEntity<String> depositInAccount(@PathVariable long id, @RequestHeader String authentication,
			@RequestBody BankTransaction bankTransaction) {
		User user = this.tokenService.validateToken(authentication);
		if (user != null) {
			bankTransaction.setAccount(accountService.getAccountById(id));
			bankTransaction.setUser(user);
			bankTransaction.setTransactionFactor(1);
			bankTransactionService.createBankTransaction(bankTransaction);
			return ResponseEntity.ok().body("Deposit is maked!");
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid authentication!");
	}

	@PostMapping("/account/withdraw/{id}")
	public ResponseEntity<String> withdrawInAccount(@PathVariable long id, @RequestHeader String authentication,
			@RequestBody BankTransaction bankTransaction) {
		User user = this.tokenService.validateToken(authentication);
		if (user != null) {
			if (bankTransactionService.accountBalance(id) > bankTransaction.getTransactionValue()) {
				bankTransaction.setAccount(accountService.getAccountById(id));
				bankTransaction.setUser(user);
				bankTransaction.setTransactionFactor(-1);
				bankTransactionService.createBankTransaction(bankTransaction);
				return ResponseEntity.ok().body("The withdrawal was done!");
			}
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insufficient balance!");
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid authentication!");
	}

	@GetMapping("/account/balance/{id}")
	public ResponseEntity<String> getAccountBalance(@PathVariable long id, @RequestHeader String authentication) {
		User user = this.tokenService.validateToken(authentication);
		if (user != null) {
			Double balance = bankTransactionService.accountBalance(id);
			return ResponseEntity.ok().body(balance.toString());
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid authentication!");
	}
}
