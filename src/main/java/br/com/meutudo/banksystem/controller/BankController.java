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
import org.springframework.web.bind.annotation.RestController;

import br.com.meutudo.banksystem.model.Bank;
import br.com.meutudo.banksystem.service.BankService;

@RestController
public class BankController {

	@Autowired
	private BankService bankService;

	@GetMapping("/bank/list")
	public ResponseEntity<List<Bank>> getBanks() {
		return ResponseEntity.ok().body(this.bankService.getBanks());
	}

	@GetMapping("/bank/{id}")
	public ResponseEntity<Bank> getBankById(@PathVariable long id) {
		return ResponseEntity.ok().body(this.bankService.getBankById(id));
	}

	@PostMapping("/bank/create")
	public ResponseEntity<Bank> createBank(@RequestBody Bank bank) {
		bank.setActive(true);
		bank.setCreationDate(new Date());
		return ResponseEntity.ok().body(this.bankService.createBank(bank));
	}

	@PutMapping("/bank/update/{id}")
	public ResponseEntity<Bank> updateBank(@PathVariable long id, @RequestBody Bank bank) {
		bank.setId(id);
		return ResponseEntity.ok().body(this.bankService.updateBank(bank));
	}

	@DeleteMapping("/bank/delete/{id}")
	public HttpStatus deleteBank(@PathVariable long id) {
		this.bankService.deleteBank(id);
		return HttpStatus.OK;
	}
}
