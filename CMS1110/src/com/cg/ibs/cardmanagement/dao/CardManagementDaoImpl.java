package com.cg.ibs.cardmanagement.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.Adler32;

import com.cg.ibs.cardmanagement.bean.CaseIdBean;
import com.cg.ibs.cardmanagement.bean.CreditCardBean;
import com.cg.ibs.cardmanagement.bean.CreditCardTransaction;
import com.cg.ibs.cardmanagement.bean.CustomerBean;
import com.cg.ibs.cardmanagement.bean.DebitCardBean;
import com.cg.ibs.cardmanagement.bean.DebitCardTransaction;

public class CardManagementDaoImpl implements CustomerDao, BankDao {

	CaseIdBean caseIdObj = new CaseIdBean();
	DebitCardBean bean = new DebitCardBean();
	CreditCardBean bean1 = new CreditCardBean();
	CustomerBean bean2 = new CustomerBean();
	private static boolean result = false;
	private String caseIdTotal;
	private static String queryStatus;
	private static Map<String, DebitCardTransaction> debitCardTransactionDetails = new HashMap<String, DebitCardTransaction>();
	private static Map<String, CreditCardTransaction> creditCardTransactionDetails = new HashMap<String, CreditCardTransaction>();
	private static Map<String, CaseIdBean> queryDetails = new HashMap<String, CaseIdBean>();
	private static Map<BigInteger, DebitCardBean> debitCardDetails = new HashMap<BigInteger, DebitCardBean>();
	private static Map<BigInteger, CreditCardBean> creditCardDetails = new HashMap<BigInteger, CreditCardBean>();
	private static Map<BigInteger, CustomerBean> customerDetails = new HashMap<BigInteger, CustomerBean>();

	static {

		DebitCardBean debit1 = new DebitCardBean(new BigInteger("1234567890"), new BigInteger("5234567891012131"), true,
				"Mohit Pursnani", 067, 2131, LocalDate.of(2024, Month.JULY, 30), "7894561239632587", "Platinum");
		DebitCardBean debit2 = new DebitCardBean(new BigInteger("1234567890"), new BigInteger("5221562391012233"), true,
				"Mohit Pursnani", 057, 2233, LocalDate.of(2022, Month.AUGUST, 30), "7894561239632587", "Silver");

		debitCardDetails.put(debit1.getDebitCardNumber(), debit1);
		debitCardDetails.put(debit2.getDebitCardNumber(), debit2);

		CreditCardBean credit1 = new CreditCardBean(new BigInteger("5189101213259898"), true, "Mohit Pursnani", 623,
				9898, LocalDate.of(2023, Month.JUNE, 30), "7894561239632587", "Gold", 200, new BigDecimal("1000000.00"),
				68900600.0);

		creditCardDetails.put(credit1.getCreditCardNumber(), credit1);

		CustomerBean cust1 = new CustomerBean(new BigInteger("1234567890"), "7894561239632587", "Mohit", "Pursnani",
				"mohit@gmail.com", "201965231351", "9774216545");

		customerDetails.put(cust1.getAccountNumber(), cust1);

		DebitCardTransaction debittrans1 = new DebitCardTransaction("DEB101", "7894561239632587",
				new BigInteger("1234567890"), new BigInteger("5234567891012131"),
				LocalDateTime.of(2019, Month.OCTOBER, 04, 12, 54, 6), new BigDecimal("1563"), "Petrol", "Debit");
		DebitCardTransaction debittrans2 = new DebitCardTransaction("DEB102", "7894561239632587",
				new BigInteger("1234567890"), new BigInteger("5234567891012131"),
				LocalDateTime.of(2019, Month.AUGUST, 8, 18, 32, 8), new BigDecimal("20.45"), "Interest", "Credit");
		debitCardTransactionDetails.put(debittrans1.getTransactionId(), debittrans1);
		debitCardTransactionDetails.put(debittrans2.getTransactionId(), debittrans2);

		CreditCardTransaction credittrans1 = new CreditCardTransaction("CRED101", "7894561239632587",
				new BigInteger("5189101213259898"), LocalDateTime.of(2018, Month.OCTOBER, 12, 11, 54, 6),
				new BigDecimal("5000"), "Shopping");
		CreditCardTransaction credittrans2 = new CreditCardTransaction("CRED102", "7894561239632587",
				new BigInteger("5189101213259898"), LocalDateTime.of(2018, Month.SEPTEMBER, 25, 23, 21, 6),
				new BigDecimal("5000"), "Movie");
		creditCardTransactionDetails.put(credittrans1.getTransactionid(), credittrans1);
		creditCardTransactionDetails.put(credittrans2.getTransactionid(), credittrans2);

	}

	public CardManagementDaoImpl() {
		super();
	}

	public CardManagementDaoImpl(CaseIdBean caseIdObj) {
		super();

		this.caseIdObj = caseIdObj;

	}

	public boolean verifyAccountNumber(BigInteger accountNumber) {

		result = customerDetails.containsKey(accountNumber);

		return result;
	}

	public boolean verifyDebitCardNumber(BigInteger debitCardNumber) {

		result = debitCardDetails.containsKey(debitCardNumber);
		return result;
	}

	public boolean verifyCreditCardNumber(BigInteger creditCardNumber) {

		result = creditCardDetails.containsKey(creditCardNumber);
		return result;
	}

	public BigInteger getAccountNumber(BigInteger debitCardNumber) {
		BigInteger acc = debitCardDetails.get(debitCardNumber).getAccountNumber();
		return acc;
	}

	@Override
	public void newCreditCard(CaseIdBean caseIdObj) {

		queryDetails.put(caseIdObj.getCaseIdTotal(), caseIdObj);

	}

	@Override
	public void newDebitCard(CaseIdBean caseIdObj, BigInteger accountNumber) {

		queryDetails.put(caseIdObj.getCaseIdTotal(), caseIdObj);

	}

	@Override
	public List<CaseIdBean> viewAllQueries() {
		List<CaseIdBean> query = new ArrayList();

		for (Entry<String, CaseIdBean> entry : queryDetails.entrySet()) {
			query.add(entry.getValue());
		}

		return query;

	}

	@Override
	public List<DebitCardBean> viewAllDebitCards() {
		List<DebitCardBean> debitCards = new ArrayList();

		for (Entry<BigInteger, DebitCardBean> entry : debitCardDetails.entrySet()) {
			debitCards.add(entry.getValue());
		}

		return debitCards;

	}

	public List<CreditCardBean> viewAllCreditCards() {
		List<CreditCardBean> creditCards = new ArrayList<>();

		for (Entry<BigInteger, CreditCardBean> entry : creditCardDetails.entrySet()) {
			creditCards.add(entry.getValue());
		}
		return creditCards;

	}

	public void requestDebitCardLost(CaseIdBean caseIdObj, BigInteger debitCardNumber) {

		queryDetails.put(caseIdObj.getCaseIdTotal(), caseIdObj);

	}

	public void requestCreditCardLost(CaseIdBean caseIdObj, BigInteger creditCardNumber) {

		queryDetails.put(caseIdObj.getCaseIdTotal(), caseIdObj);

	}

	public String getdebitCardType(BigInteger debitCardNumber) {
		String type = debitCardDetails.get(debitCardNumber).getDebitCardType();
		return type;

	}

	public String requestDebitCardUpgrade(CaseIdBean caseIdObj, BigInteger debitCardNumber) {
		queryDetails.put(caseIdObj.getCaseIdTotal(), caseIdObj);
		return ("TICKET RAISED SUCCESSFULLY");

	}

	public int getDebitCardPin(BigInteger debitCardNumber) {
		int pin = debitCardDetails.get(debitCardNumber).getDebitCurrentPin();
		return pin;

	}

	public void setNewDebitPin(BigInteger debitCardNumber, int newPin) {
		bean = debitCardDetails.get(debitCardNumber);
		bean.setDebitCurrentPin(newPin);
		debitCardDetails.replace(debitCardNumber, bean);
	}

	public int getCreditCardPin(BigInteger creditCardNumber) {
		int pin = creditCardDetails.get(creditCardNumber).getCreditCurrentPin();
		return pin;

	}

	public void setNewCreditPin(BigInteger creditCardNumber, int newPin) {
		bean1 = creditCardDetails.get(creditCardNumber);
		bean1.setCreditCurrentPin(newPin);
		creditCardDetails.replace(creditCardNumber, bean1);
	}

	public void requestCreditCardUpgrade(CaseIdBean caseIdObj, BigInteger creditCardNumber) {

		queryDetails.put(caseIdObj.getCaseIdTotal(), caseIdObj);
		System.out.println("TICKET RAISED SUCCESSFULLY");

	}

	public String getcreditCardType(BigInteger creditCardNumber) {

		String type = creditCardDetails.get(creditCardNumber).getCreditCardType();
		return type;
	}

	@Override
	public boolean verifyDebitTransactionId(String transactionId) {

		boolean result = debitCardTransactionDetails.containsKey(transactionId);
		return result;
	}

	@Override
	public void raiseDebitMismatchTicket(CaseIdBean caseIdObj, String transactionId) {

		queryDetails.put(caseIdObj.getCaseIdTotal(), caseIdObj);

	}

	@Override
	public void raiseCreditMismatchTicket(CaseIdBean caseIdObj, String transactionId) {

		queryDetails.put(caseIdObj.getCaseIdTotal(), caseIdObj);

	}

	public List<DebitCardTransaction> getDebitTrans(int dys, BigInteger debitCardNumber) {

		List<DebitCardTransaction> debitCardsList = new ArrayList();
		LocalDateTime dLocalDateTime = LocalDateTime.now().minusDays(dys);
		for (Entry<String, DebitCardTransaction> entry : debitCardTransactionDetails.entrySet()) {
			if (entry.getValue().getDate().isAfter(dLocalDateTime))
				debitCardsList.add(entry.getValue());
		}

		return debitCardsList;
	}

	@Override
	public List<CreditCardTransaction> getCreditTrans(int days, BigInteger creditCardNumber) {

		List<CreditCardTransaction> creditCardsList = new ArrayList();
		LocalDateTime dLocalDateTime = LocalDateTime.now().minusDays(days);
		for (Entry<String, CreditCardTransaction> entry : creditCardTransactionDetails.entrySet()) {
			if (entry.getValue().getDate().isAfter(dLocalDateTime))
				creditCardsList.add(entry.getValue());
		}

		return creditCardsList;
	}

	@Override
	public boolean verifyQueryId(String queryId) {

		boolean result = queryDetails.containsKey(queryId);
		return result;

	}

	@Override
	public void setQueryStatus(String queryId, String newStatus) {

		caseIdObj = queryDetails.get(queryId);
		caseIdObj.setStatusOfQuery(newStatus);
		queryDetails.replace(newStatus, caseIdObj);

	}

	@Override
	public boolean verifyCreditTransactionId(String transactionId) {
		boolean result = creditCardTransactionDetails.containsKey(transactionId);
		return result;
	}

	@Override
	public String getCustomerReferenceId(CaseIdBean caseIdObjService, String customerReferenceId) {

		String BankId = null;
		System.out.println("dao");
		for (Entry<String, CaseIdBean> entry : queryDetails.entrySet()) {
			if (customerReferenceId.equals(entry.getValue().getCustomerReferenceId()))
				BankId = entry.getValue().getCaseIdTotal();
		}

		queryStatus = queryDetails.get(BankId).getStatusOfQuery();

		return queryStatus;
	}

}