package com.cg.ibs.cardmanagement.service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.cg.ibs.cardmanagement.dao.CustomerDao;
import com.cg.ibs.cardmanagement.exceptionhandling.IBSException;
import com.cg.ibs.cardmanagement.bean.CaseIdBean;
import com.cg.ibs.cardmanagement.bean.CreditCardBean;
import com.cg.ibs.cardmanagement.bean.CreditCardTransaction;
import com.cg.ibs.cardmanagement.bean.DebitCardBean;
import com.cg.ibs.cardmanagement.bean.DebitCardTransaction;
import com.cg.ibs.cardmanagement.dao.CardManagementDaoImpl;

public interface CustomerService {

	public boolean verifyDebitCardNumber(BigInteger debitCardNumber) throws IBSException;

	public List<DebitCardBean> viewAllDebitCards();

	public List<CreditCardBean> viewAllCreditCards();

	public String getNewCardtype(int newCardType) throws IBSException;

	String verifyDebitcardType(BigInteger debitCardNumber) throws IBSException;

	String requestDebitCardUpgrade(BigInteger debitCardNumber, int myChoice);

	String resetDebitPin(BigInteger debitCardNumber, int newPin);

	public boolean verifyDebitPin(int pin, BigInteger debitCardNumber);

	boolean verifyCreditCardNumber(BigInteger creditCardNumber) throws IBSException;

	String resetCreditPin(BigInteger creditCardNumber, int newPin);

	public boolean verifyCreditPin(int pin, BigInteger creditCardNumber);

	String applyNewCreditCard();

	boolean requestDebitCardLost(BigInteger debitCardNumber) throws IBSException;

	boolean requestCreditCardLost(BigInteger creditCardNumber) throws IBSException;

	String verifyCreditcardType(BigInteger creditCardNumber);

	String requestCreditCardUpgrade(BigInteger creditCardNumber, int myChoice);

	boolean raiseDebitMismatchTicket(String transactionId) throws IBSException;

	boolean raiseCreditMismatchTicket(String transactionId) throws IBSException;

	public List<DebitCardTransaction> getDebitTransactions(int dys, BigInteger debitCardNumber) throws IBSException;

	public List<CreditCardTransaction> getCreditTrans(int days, BigInteger creditCardNumber) throws IBSException;

	public int getPinLength(int pin) throws IBSException;

	void getCardLength(BigInteger card) throws IBSException;

	String applyNewDebitCard(BigInteger accountNumber, String newCardType) throws IBSException;

	public boolean verifyAccountNumber(BigInteger accountNumber) throws IBSException;

	public String viewQueryStatus(String customerReferenceId) throws IBSException;

	

}