package com.cg.ibs.cardmanagement.bean;

import java.math.BigInteger;
import java.time.LocalDate;

public class DebitCardBean {
	
	
	private BigInteger accountNumber;
    private BigInteger debitCardNumber;
	private boolean debitCardStatus;
	private String nameOnDebitCard;
	private int debitCvvNum;
	private int debitCurrentPin;
	private LocalDate debitDateOfExpiry;
	private String UCI;
	private String debitCardType;

	public DebitCardBean() {
		super();
	}

	

	public BigInteger getAccountNumber() {
		return accountNumber;
	}



	public void setAccountNumber(BigInteger accountNumber) {
		this.accountNumber = accountNumber;
	}



	public String getUCI() {
		return UCI;
	}



	public void setUCI(String uCI) {
		UCI = uCI;
	}



	public String getDebitCardType() {
		return debitCardType;
	}



	public void setDebitCardType(String debitCardType) {
		this.debitCardType = debitCardType;
	}



	public DebitCardBean(BigInteger accountNumber , BigInteger debitCardNumber, boolean debitCardStatus, String nameOnDebitCard, int debitCvvNum, int debitCurrentPin,
			LocalDate debitDateOfExpiry, String UCI, String debitCardType) {
	
		this.accountNumber=accountNumber;
		this.debitCardNumber=debitCardNumber;
		this.debitCardStatus=debitCardStatus;
		this.nameOnDebitCard=nameOnDebitCard;
		this.debitCvvNum=debitCvvNum;
		this.debitCurrentPin=debitCurrentPin;
		this.debitDateOfExpiry =debitDateOfExpiry;
		this.UCI=UCI;
		this.debitCardType=debitCardType;
		
	}

	public boolean isDebitCardStatus() {
		return debitCardStatus;
	}

	public BigInteger getDebitCardNumber() {
		return debitCardNumber;
	}

	public void setDebitCardNumber(BigInteger debitCardNumber) {
		this.debitCardNumber = debitCardNumber;
	}

	public boolean iDebitCardStatus() {
		return debitCardStatus;
	}

	public void setDebitCardStatus(boolean debitCardStatus) {
		this.debitCardStatus = debitCardStatus;
	}

	public String getNameOnDebitCard() {
		return nameOnDebitCard;
	}

	public void setNameOnDebitCard(String nameOnDebitCard) {
		this.nameOnDebitCard = nameOnDebitCard;
	}

	public int getDebitCvvNum() {
		return debitCvvNum;
	}

	public void setDebitCvvNum(int debitCvvNum) {
		this.debitCvvNum = debitCvvNum;
	}

	public int getDebitCurrentPin() {
		return debitCurrentPin;
	}

	public void setDebitCurrentPin(int debitCurrentPin) {
		this.debitCurrentPin = debitCurrentPin;
	}

	@Override
	public String toString() {
		return "AccountNumber =" + accountNumber + "\nDebit Card Number =\t" + debitCardNumber
				+ ", debitCardStatus=" + debitCardStatus + ", nameOnDebitCard=" + nameOnDebitCard +  ", debitDateOfExpiry=" + debitDateOfExpiry
				+ ", UCI=" + UCI + ", debitCardType=" + debitCardType ;
	}



	public LocalDate getDebitDateOfExpiry() {
		return debitDateOfExpiry;
	}

	public void setDebitDateOfExpiry(LocalDate debitDateOfExpiry) {
		this.debitDateOfExpiry = debitDateOfExpiry;
	}

}
