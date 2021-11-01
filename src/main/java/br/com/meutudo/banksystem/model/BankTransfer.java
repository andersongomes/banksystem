package br.com.meutudo.banksystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name = "BANK_TRANSFER")
public class BankTransfer {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToOne
	private Account originAccount;

	@OneToOne
	private Account destinyAccount;

	@Column(name = "transfer_value")
	private Double transferValue;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Account getOriginAccount() {
		return originAccount;
	}

	public void setOriginAccount(Account originAccount) {
		this.originAccount = originAccount;
	}

	public Account getDestinyAccount() {
		return destinyAccount;
	}

	public void setDestinyAccount(Account destinyAccount) {
		this.destinyAccount = destinyAccount;
	}

	public Double getTransferValue() {
		return transferValue;
	}

	public void setTransferValue(Double transferValue) {
		this.transferValue = transferValue;
	}
}
