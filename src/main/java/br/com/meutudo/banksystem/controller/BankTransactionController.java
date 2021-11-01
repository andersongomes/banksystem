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
import org.springframework.web.bind.annotation.RestController;

import br.com.meutudo.banksystem.model.BankTransaction;
import br.com.meutudo.banksystem.service.BankTransactionService;

@RestController
public class BankTransactionController {
	@Autowired
	private BankTransactionService bankTransactionService;

	@GetMapping("/transaction/list")
	public ResponseEntity<List<BankTransaction>> getBankTransactions() {
		return ResponseEntity.ok().body(this.bankTransactionService.getBankTransactions());
	}

	@GetMapping("/transaction/{id}")
	public ResponseEntity<BankTransaction> getBankTransactionById(@PathVariable long id) {
		return ResponseEntity.ok().body(this.bankTransactionService.getBankTransactionById(id));
	}

	@PostMapping("/transaction/create")
	public ResponseEntity<BankTransaction> createBankTransaction(@RequestBody BankTransaction bankTransaction) {
		return ResponseEntity.ok().body(this.bankTransactionService.createBankTransaction(bankTransaction));
	}

	@PutMapping("/transaction/update/{id}")
	public ResponseEntity<BankTransaction> updateBankTransaction(@PathVariable long id,
			@RequestBody BankTransaction bankTransaction) {
		bankTransaction.setId(id);
		return ResponseEntity.ok().body(this.bankTransactionService.updateBankTransaction(bankTransaction));
	}

	@DeleteMapping("/transaction/delete/{id}")
	public HttpStatus deleteBankTransaction(@PathVariable long id) {
		this.bankTransactionService.deleteBankTransaction(id);
		return HttpStatus.OK;
	}
}
