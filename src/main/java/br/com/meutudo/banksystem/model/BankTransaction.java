package br.com.meutudo.banksystem.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name = "BANK_TRANSACTION")
public class BankTransaction {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "transaction_scheduling_date")
	private Date transactionSchedulingDate;

	@Column(name = "transaction_date")
	private Date transactionDate;

	@Column(name = "transaction_factor")
	private int transactionFactor;

	@Column(name = "transaction_value")
	private Double transactionValue;

	@OneToOne
	private Account account;

	@OneToOne
	private User user;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getTransactionSchedulingDate() {
		return transactionSchedulingDate;
	}

	public void setTransactionSchedulingDate(Date transactionSchedulingDate) {
		this.transactionSchedulingDate = transactionSchedulingDate;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public int getTransactionFactor() {
		return transactionFactor;
	}

	public void setTransactionFactor(int transactionFactor) {
		this.transactionFactor = transactionFactor;
	}

	public Double getTransactionValue() {
		return transactionValue;
	}

	public void setTransactionValue(Double transactionValue) {
		this.transactionValue = transactionValue;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
