package br.com.meutudo.banksystem.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
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

import br.com.meutudo.banksystem.model.BankTransaction;
import br.com.meutudo.banksystem.model.BankTransfer;
import br.com.meutudo.banksystem.model.User;
import br.com.meutudo.banksystem.service.AccountService;
import br.com.meutudo.banksystem.service.BankTransactionService;
import br.com.meutudo.banksystem.service.BankTransferService;
import br.com.meutudo.banksystem.service.TokenService;

@RestController
public class BankTransferController {

	@Autowired
	private BankTransferService bankTransferService;

	@Autowired
	private BankTransactionService bankTransactionService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TokenService tokenService;

	@GetMapping("/transfer/list")
	public ResponseEntity<List<BankTransfer>> getBankTransfers() {
		return ResponseEntity.ok().body(this.bankTransferService.getBankTransfers());
	}

	@GetMapping("/transfer/{id}")
	public ResponseEntity<BankTransfer> getBankTransferById(@PathVariable long id) {
		return ResponseEntity.ok().body(this.bankTransferService.getBankTransferById(id));
	}

	@PostMapping("/transfer/create")
	public ResponseEntity<String> createBankTransfer(@RequestBody BankTransfer bankTransfer,
			@RequestHeader String authentication) {
		User user = this.tokenService.validateToken(authentication);
		if (user != null) {
			if (this.bankTransactionService.accountBalance(bankTransfer.getOriginAccount().getId()) > bankTransfer
					.getTransferValue()) {
				BankTransfer bankTransferResult = this.bankTransferService.createBankTransfer(bankTransfer);

				BankTransaction bankTransaction = new BankTransaction();
				bankTransaction.setAccount(this.accountService.getAccountById(bankTransfer.getOriginAccount().getId()));
				bankTransaction.setUser(user);
				bankTransaction.setTransactionFactor(-1);
				bankTransaction.setTransactionValue(bankTransfer.getTransferValue());
				bankTransaction.setTransactionDate(new Date());
				bankTransaction.setTransactionSchedulingDate(new Date());
				bankTransaction.setBankTransfer(bankTransferResult);
				this.bankTransactionService.createBankTransaction(bankTransaction);

				bankTransaction = new BankTransaction();
				bankTransaction
						.setAccount(this.accountService.getAccountById(bankTransfer.getDestinyAccount().getId()));
				bankTransaction.setUser(user);
				bankTransaction.setTransactionFactor(1);
				bankTransaction.setTransactionValue(bankTransfer.getTransferValue());
				bankTransaction.setTransactionDate(new Date());
				bankTransaction.setTransactionSchedulingDate(new Date());
				bankTransaction.setBankTransfer(bankTransferResult);
				this.bankTransactionService.createBankTransaction(bankTransaction);

				return ResponseEntity.ok().body("Transfer concluded with success!");
			}
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insufficient balance!");
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid authentication!");
	}

	@PutMapping("/transfer/update/{id}")
	public ResponseEntity<BankTransfer> updateBankTransfer(@PathVariable long id,
			@RequestBody BankTransfer bankTransfer) {
		bankTransfer.setId(id);
		return ResponseEntity.ok().body(this.bankTransferService.updateBankTransfer(bankTransfer));
	}

	@DeleteMapping("/transfer/delete/{id}")
	public HttpStatus deleteBankTransfer(@PathVariable long id) {
		this.bankTransferService.deleteBankTransfer(id);
		return HttpStatus.OK;
	}

	@PutMapping("/transfer/revert/{id}")
	public ResponseEntity<String> bankTransferRevert(@PathVariable long id,
			@RequestHeader String authentication) {
		User user = this.tokenService.validateToken(authentication);
		if (user != null) {
			BankTransfer bankTransfer = this.bankTransferService.getBankTransferById(id);

			List<BankTransaction> bankTransactions = this.bankTransactionService
					.getBankTransactionByBankTransfer(bankTransfer.getId());
			if (bankTransactions != null) {
				for (BankTransaction bt : bankTransactions) {
					if (bt.getTransactionFactor() == 1) {
						Double x = this.bankTransactionService.accountBalance(bt.getAccount().getId());
						if (Double.compare(bt.getTransactionValue(), this.bankTransactionService.accountBalance(bt.getAccount().getId())) <= 0) {
							bt.setTransactionReversalDate(new Date());
							this.bankTransactionService.updateBankTransaction(bt);
						} else {
							return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insufficient balance!");
						}
					} else {
						bt.setTransactionReversalDate(new Date());
						this.bankTransactionService.updateBankTransaction(bt);
					}
				}
			}

			return ResponseEntity.ok().body("Transaction are reverted with success!");
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid authentication!");
	}

	@PostMapping("/transfer/create/parceled/{installments}")
	public ResponseEntity<String> createBankTransferParceled(@PathVariable int installments, @RequestBody BankTransfer bankTransfer,
			@RequestHeader String authentication) {
		User user = this.tokenService.validateToken(authentication);
		if (user != null) {
			if (installments > 0) {
				if (this.bankTransactionService.accountBalance(bankTransfer.getOriginAccount().getId()) > bankTransfer
						.getTransferValue()) {
					BankTransfer bankTransferResult = this.bankTransferService.createBankTransfer(bankTransfer);
					
					Double valueParceled = (Double) bankTransfer.getTransferValue() / installments;
					BigDecimal bd = new BigDecimal(valueParceled).setScale(2, RoundingMode.HALF_EVEN);
					valueParceled = bd.doubleValue();
					Date now = new Date();
					Calendar cal = Calendar.getInstance();
					cal.setTime(now);

					for (int i = 0; i < installments; i++) {
						BankTransaction bankTransaction = new BankTransaction();
						bankTransaction.setAccount(this.accountService.getAccountById(bankTransfer.getOriginAccount().getId()));
						bankTransaction.setUser(user);
						bankTransaction.setTransactionFactor(-1);
						bankTransaction.setTransactionValue(valueParceled);
						cal.add(Calendar.MONTH, 1);
						bankTransaction.setTransactionSchedulingDate(cal.getTime());
						bankTransaction.setBankTransfer(bankTransferResult);
						this.bankTransactionService.createBankTransaction(bankTransaction);
		
						bankTransaction = new BankTransaction();
						bankTransaction
								.setAccount(this.accountService.getAccountById(bankTransfer.getDestinyAccount().getId()));
						bankTransaction.setUser(user);
						bankTransaction.setTransactionFactor(1);
						bankTransaction.setTransactionValue(valueParceled);	
						bankTransaction.setTransactionSchedulingDate(cal.getTime());
						bankTransaction.setBankTransfer(bankTransferResult);
						this.bankTransactionService.createBankTransaction(bankTransaction);
					}
					return ResponseEntity.ok().body("Parceled transfer concluded with success!");
				}
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insufficient balance!");
			}
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid installments!");
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid authentication!");
	}
}
