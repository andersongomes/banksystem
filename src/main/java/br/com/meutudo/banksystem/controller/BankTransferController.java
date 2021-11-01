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

import br.com.meutudo.banksystem.model.BankTransfer;
import br.com.meutudo.banksystem.service.BankTransferService;

@RestController
public class BankTransferController {

	@Autowired
	private BankTransferService bankTransferService;

	@GetMapping("/transfer/list")
	public ResponseEntity<List<BankTransfer>> getBankTransfers() {
		return ResponseEntity.ok().body(this.bankTransferService.getBankTransfers());
	}

	@GetMapping("/transfer/{id}")
	public ResponseEntity<BankTransfer> getBankTransferById(@PathVariable long bankTransferId) {
		return ResponseEntity.ok().body(this.bankTransferService.getBankTransferById(bankTransferId));
	}

	@PostMapping("/transfer/create")
	public ResponseEntity<BankTransfer> createBankTransfer(@RequestBody BankTransfer bankTransfer) {
		return ResponseEntity.ok().body(this.bankTransferService.createBankTransfer(bankTransfer));
	}

	@PutMapping("/transfer/update/{id}")
	public ResponseEntity<BankTransfer> updateBankTransfer(@PathVariable long bankTransferId,
			@RequestBody BankTransfer bankTransfer) {
		bankTransfer.setId(bankTransferId);
		return ResponseEntity.ok().body(this.bankTransferService.updateBankTransfer(bankTransfer));
	}

	@DeleteMapping("/transfer/delete/{id}")
	public HttpStatus deleteBankTransfer(@PathVariable long bankTransferId) {
		this.bankTransferService.deleteBankTransfer(bankTransferId);
		return HttpStatus.OK;
	}
}
