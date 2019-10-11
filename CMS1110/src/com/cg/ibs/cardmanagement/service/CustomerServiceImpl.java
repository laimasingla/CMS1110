package com.cg.ibs.cardmanagement.service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cg.ibs.cardmanagement.bean.CaseIdBean;
import com.cg.ibs.cardmanagement.bean.CreditCardBean;
import com.cg.ibs.cardmanagement.bean.CreditCardTransaction;
import com.cg.ibs.cardmanagement.bean.CustomerBean;
import com.cg.ibs.cardmanagement.bean.DebitCardBean;
import com.cg.ibs.cardmanagement.bean.DebitCardTransaction;
import com.cg.ibs.cardmanagement.dao.BankDao;
import com.cg.ibs.cardmanagement.dao.CustomerDao;
import com.cg.ibs.cardmanagement.exceptionhandling.IBSException;
import com.cg.ibs.cardmanagement.dao.CardManagementDaoImpl;

public class CustomerServiceImpl implements CustomerService {

	public void getCardLength(BigInteger card) throws IBSException {
		int length = card.toString().length();
		if (length != 16)
			throw new IBSException("Incorrect Length of pin ");

	}

	CustomerDao customerDao;

	public CustomerServiceImpl() {
		customerDao = new CardManagementDaoImpl();

	}

	CustomerBean customObj = new CustomerBean();

	String UCI = "7894561239632587";

	String caseIdGenOne = " ";
	static String caseIdTotal = " ";
	static int caseIdGenTwo = 0;
	LocalDateTime timestamp;
	LocalDateTime fromDate;
	LocalDateTime toDate;
	static String setCardType = null;
	static String uniqueID = null;
	static String customerReferenceID = null;

	String addToQueryTable(String caseIdGenOne) {
		caseIdTotal = caseIdGenOne + caseIdGenTwo;
		caseIdGenTwo++;
		return caseIdTotal;
	}

	@Override
	public String applyNewDebitCard(BigInteger accountNumber, String newCardType) throws IBSException {

		CaseIdBean caseIdObj = new CaseIdBean();

		caseIdGenOne = "ANDC";

		caseIdTotal = addToQueryTable(caseIdGenOne);
		customerReferenceID = (caseIdTotal + accountNumber);
		timestamp = LocalDateTime.now();
		caseIdObj.setDefineQuery(newCardType);
		caseIdObj.setAccountNumber(accountNumber);
		caseIdObj.setCardNumber("NA");
		caseIdObj.setCaseIdTotal(caseIdTotal);
		caseIdObj.setCaseTimeStamp(timestamp);
		caseIdObj.setStatusOfQuery("Pending");
		caseIdObj.setUCI(UCI);
		caseIdObj.setCustomerReferenceId(customerReferenceID);
		customerDao.newDebitCard(caseIdObj, accountNumber);
		return customerReferenceID;

	}

	public String getNewCardtype(int newCardType) throws IBSException {
		String cardType = newCardType + "";
		Pattern pattern = Pattern.compile("[123]");
		Matcher matcher = pattern.matcher(cardType);
		if (!(matcher.find() && matcher.group().equals(cardType)))
			throw new IBSException("Not a valid input");

		switch (newCardType) {
		case 1:

			setCardType = "Platinum";
			break;
		case 2:
			setCardType = "Gold";
			break;
		case 3:
			setCardType = "Silver";
			break;

		}
		return setCardType;
	}

	public static String getSetCardType() {
		return setCardType;
	}

	public static void setSetCardType(String setCardType) {
		CustomerServiceImpl.setCardType = setCardType;
	}

	@Override
	public String applyNewCreditCard() {
		CaseIdBean caseIdObj = new CaseIdBean();
		caseIdGenOne = "ANCC";
		caseIdTotal = addToQueryTable(caseIdGenOne);
		customerReferenceID = (caseIdTotal + UCI.toString().substring(6));
		timestamp = LocalDateTime.now();
		caseIdObj.setCustomerReferenceId(customerReferenceID);
		caseIdObj.setCaseIdTotal(caseIdTotal);
		caseIdObj.setCaseTimeStamp(timestamp);
		caseIdObj.setStatusOfQuery("Pending");
		caseIdObj.setUCI(UCI);
		caseIdObj.setDefineQuery("NA");
		customerDao.newCreditCard(caseIdObj);
		return customerReferenceID;
	}

	@Override
	public boolean requestDebitCardLost(BigInteger debitCardNumber) throws IBSException {
		boolean status = false;
		getCardLength(debitCardNumber);

		CaseIdBean caseIdObj = new CaseIdBean();
		caseIdTotal = addToQueryTable(caseIdGenOne);
		customerReferenceID = (caseIdTotal + debitCardNumber.toString().substring(6));
		caseIdGenOne = "RDCL";

		timestamp = LocalDateTime.now();

		caseIdObj.setCaseIdTotal(caseIdTotal);
		caseIdObj.setCaseTimeStamp(timestamp);
		caseIdObj.setStatusOfQuery("Pending");
		caseIdObj.setUCI(UCI);
		caseIdObj.setDefineQuery("Debit card Number" + debitCardNumber);
		caseIdObj.setCustomerReferenceId(customerReferenceID);
		customerDao.requestDebitCardLost(caseIdObj, debitCardNumber);
		status = true;
		return status;

	}

	@Override
	public boolean requestCreditCardLost(BigInteger creditCardNumber) throws IBSException {
		boolean status = false;
		boolean check = customerDao.verifyCreditCardNumber(creditCardNumber);
		if (check) {
			CaseIdBean caseIdObj = new CaseIdBean();

			caseIdGenOne = "RCCL";
			caseIdTotal = addToQueryTable(caseIdGenOne);
			customerReferenceID = (caseIdTotal + creditCardNumber.toString().substring(6));
			timestamp = LocalDateTime.now();

			caseIdObj.setCaseIdTotal(caseIdTotal);
			caseIdObj.setCaseTimeStamp(timestamp);
			caseIdObj.setStatusOfQuery("Pending");
			caseIdObj.setUCI(UCI);
			caseIdObj.setDefineQuery("Credit card number" + creditCardNumber);
			caseIdObj.setCustomerReferenceId(customerReferenceID);
			customerDao.requestCreditCardLost(caseIdObj, creditCardNumber);
			status = true;
			return status;
		} else {

			throw new IBSException("Credit card not found");
		}

	}

	public boolean raiseDebitMismatchTicket(String transactionId) throws IBSException {
		boolean status = false;
		boolean transactionResult = customerDao.verifyDebitTransactionId(transactionId);
		if (transactionResult) {
			CaseIdBean caseIdObj = new CaseIdBean();
			caseIdGenOne = "RDMT";

			timestamp = LocalDateTime.now();
			caseIdTotal = addToQueryTable(caseIdGenOne);
			customerReferenceID = (caseIdTotal + transactionId);
			caseIdObj.setCaseIdTotal(caseIdTotal);
			caseIdObj.setCaseTimeStamp(timestamp);
			caseIdObj.setStatusOfQuery("Pending");
			caseIdObj.setUCI(UCI);
			caseIdObj.setDefineQuery("Transaction ID" + transactionId);
			caseIdObj.setCustomerReferenceId(customerReferenceID);
			customerDao.raiseDebitMismatchTicket(caseIdObj, transactionId);
			status = true;
			return status;
		} else {

			throw new IBSException("Transaction ID not found");

		}

	}

	public List<DebitCardBean> viewAllDebitCards() {

		return customerDao.viewAllDebitCards();
	}

	@Override
	public List<CreditCardBean> viewAllCreditCards() {

		return customerDao.viewAllCreditCards();

	}

	public String verifyDebitcardType(BigInteger debitCardNumber) throws IBSException {

		boolean check = customerDao.verifyDebitCardNumber(debitCardNumber);
		if (check) {
			String type = customerDao.getdebitCardType(debitCardNumber);
			return type;
		} else {

			throw new NullPointerException("Debit card not found");
		}
	}

	@Override
	public String requestDebitCardUpgrade(BigInteger debitCardNumber, int myChoice) {

		CaseIdBean caseIdObj = new CaseIdBean();

		caseIdGenOne = "RDCU";
		timestamp = LocalDateTime.now();
		caseIdTotal = addToQueryTable(caseIdGenOne);
		customerReferenceID = (caseIdTotal + debitCardNumber.toString().substring(6));
		caseIdObj.setCaseIdTotal(caseIdTotal);
		caseIdObj.setCaseTimeStamp(timestamp);
		caseIdObj.setStatusOfQuery("Pending");
		caseIdObj.setCustomerReferenceId(customerReferenceID);
		caseIdObj.setUCI(UCI);
		if (myChoice == 1) {
			caseIdObj.setDefineQuery("Upgrade to Gold for card Number :" + debitCardNumber);

			customerDao.requestDebitCardUpgrade(caseIdObj, debitCardNumber);
			return (" Successful Application for Upgradation to GOLD  . Your refernce Id is:  " + customerReferenceID);
		} else if (myChoice == 2) {
			caseIdObj.setDefineQuery("Upgrade to Platinum for card Number :" + debitCardNumber);
			customerDao.requestDebitCardUpgrade(caseIdObj, debitCardNumber);
			return (" Successful Application for Upgradation to PLATINUM . Your refernce Id is:  "
					+ customerReferenceID);
		} else {
			return ("Choose a valid option");
		}

	}

	public boolean verifyDebitCardNumber(BigInteger debitCardNumber) throws IBSException {
		String debitCardNum = debitCardNumber.toString();
		Pattern pattern = Pattern.compile("[0-9]{16}");
		Matcher matcher = pattern.matcher(debitCardNum);
		if (!(matcher.find() && matcher.group().equals(debitCardNum)))
			throw new IBSException("Incorrect  length");
		boolean check = customerDao.verifyDebitCardNumber(debitCardNumber);
		if (!check)
			throw new IBSException(" Debit Card Number does not exist");
		return (check);

	}

	public boolean verifyAccountNumber(BigInteger accountNumber) throws IBSException {
		String accountNum = accountNumber.toString();
		Pattern pattern = Pattern.compile("[0-9]{10}");
		Matcher matcher = pattern.matcher(accountNum);
		if (!(matcher.find() && matcher.group().equals(accountNum)))
			throw new IBSException("Incorrect  length");
		boolean check = customerDao.verifyAccountNumber(accountNumber);
		if (!check)
			throw new IBSException(" Account Number does not exist");
		return (check);
	}

	public boolean verifyDebitPin(int pin, BigInteger debitCardNumber) {
		if (pin == customerDao.getDebitCardPin(debitCardNumber)) {

			return true;
		} else {
			return false;
		}
	}

	public String resetDebitPin(BigInteger debitCardNumber, int newPin) {

		customerDao.setNewDebitPin(debitCardNumber, newPin);
		return ("PIN UPDATED SUCCESSFULLY!");
	}

	public boolean verifyCreditCardNumber(BigInteger creditCardNumber) throws IBSException {
		String creditCardNum = creditCardNumber.toString();
		Pattern pattern = Pattern.compile("[0-9]{16}");
		Matcher matcher = pattern.matcher(creditCardNum);
		if (!(matcher.find() && matcher.group().equals(creditCardNum)))
			throw new IBSException("Incorrect  length");
		boolean check1 = customerDao.verifyCreditCardNumber(creditCardNumber);
		if (!check1)
			throw new IBSException(" Credit Card Number does not exist");
		return (check1);
	}

	public boolean verifyCreditPin(int pin, BigInteger creditCardNumber) {

		if (pin == customerDao.getCreditCardPin(creditCardNumber)) {

			return true;
		} else {
			return false;
		}
	}

	public String resetCreditPin(BigInteger creditCardNumber, int newPin) {

		customerDao.setNewCreditPin(creditCardNumber, newPin);
		return ("PIN UPDATED SUCCESSFULLY!");
	}

	@Override
	public String requestCreditCardUpgrade(BigInteger creditCardNumber, int myChoice) {
		CaseIdBean caseIdObj = new CaseIdBean();
		caseIdGenOne = "RCCU";
		timestamp = LocalDateTime.now();

		caseIdTotal = addToQueryTable(caseIdGenOne);
		customerReferenceID = (caseIdTotal + creditCardNumber.toString().substring(6));
		caseIdObj.setCaseIdTotal(caseIdTotal);
		caseIdObj.setCaseTimeStamp(timestamp);
		caseIdObj.setStatusOfQuery("Pending");
		caseIdObj.setUCI(UCI);
		if (myChoice == 1) {
			caseIdObj.setDefineQuery("Upgrade to Gold for credit card number:" + creditCardNumber);

			customerDao.requestCreditCardUpgrade(caseIdObj, creditCardNumber);
			return (" Successful Application for Upgradation to GOLD . Your Reference ID is :  " + customerReferenceID);
		} else if (myChoice == 2) {
			caseIdObj.setDefineQuery("Upgrade to Platinum for credit card number:" + creditCardNumber);
			customerDao.requestDebitCardUpgrade(caseIdObj, creditCardNumber);
			return (" Successful Application for Upgradation to PLATINUM . Your Reference ID is :  "
					+ customerReferenceID);
		} else {
			return ("Choose a valid option");
		}

	}

	@Override
	public String verifyCreditcardType(BigInteger creditCardNumber) {
		boolean check = customerDao.verifyCreditCardNumber(creditCardNumber);
		if (check) {
			String type = customerDao.getcreditCardType(creditCardNumber);
			return type;
		} else {

			throw new NullPointerException("Credit Card not found");
		}

	}

	@Override
	public boolean raiseCreditMismatchTicket(String transactionId) throws IBSException {
		boolean status = false;
		boolean transactionResult = customerDao.verifyCreditTransactionId(transactionId);
		if (transactionResult) {
			CaseIdBean caseIdObj = new CaseIdBean();
			caseIdGenOne = "RCMT";

			timestamp = LocalDateTime.now();
			caseIdTotal = addToQueryTable(caseIdGenOne);
			customerReferenceID = (caseIdTotal + transactionId);
			caseIdObj.setCaseIdTotal(caseIdTotal);
			caseIdObj.setCaseTimeStamp(timestamp);
			caseIdObj.setStatusOfQuery("Pending");
			caseIdObj.setUCI(UCI);
			caseIdObj.setDefineQuery("Transaction ID:" + transactionId);
			caseIdObj.setCustomerReferenceId(customerReferenceID);
			customerDao.raiseCreditMismatchTicket(caseIdObj, transactionId);
			status = true;
			return status;
		} else {
			throw new IBSException("TRANSACTION ID NOT FOUND");

		}

	}

	public List<DebitCardTransaction> getDebitTransactions(int days, BigInteger debitCardNumber) throws IBSException {
		boolean check = customerDao.verifyDebitCardNumber(debitCardNumber);
		if (check) {
			if (days < 1) {

				throw new IBSException("Statement can not be generated for less than 1 day");

			} else if (days >= 1 && days <= 730) {
				List<DebitCardTransaction> debitCardBeanTrns = customerDao.getDebitTrans(days, debitCardNumber);
				if (debitCardBeanTrns.size() == 0)
					throw new IBSException("NO TRANSACTIONS");
				return customerDao.getDebitTrans(days, debitCardNumber);
			} else {
				throw new IBSException("Enter days less than 730");

			}
		}
		return null;

	}

	@Override
	public List<CreditCardTransaction> getCreditTrans(int days, BigInteger creditCardNumber) throws IBSException {

		boolean check = customerDao.verifyCreditCardNumber(creditCardNumber);
		if (check) {
			if (days < 1) {

				throw new IBSException("Statement can not be generated for less than 1 day");

			} else if (days >= 1 && days <= 730) {

				List<CreditCardTransaction> creditCardBeanTrns = customerDao.getCreditTrans(days, creditCardNumber);
				if (creditCardBeanTrns.size() == 0)
					throw new IBSException("NO TRANSACTIONS");
				return customerDao.getCreditTrans(days, creditCardNumber);

			} else {
				throw new IBSException("Enter days less than 730");
			}
		}
		return null;
	}

	@Override
	public int getPinLength(int pin) throws IBSException {
		int count = 0;
		while (pin != 0) {
			pin = pin / 10;
			++count;
		}
		if (count != 4)
			throw new IBSException("Incorrect Length of pin ");
		return count;
	}

	@Override
	public String viewQueryStatus(String customerReferenceId) throws IBSException {
		CaseIdBean caseIdObj = new CaseIdBean();
		caseIdObj.setCustomerReferenceId(customerReferenceId);
		System.out.println("service");
		String currentQueryStatus = customerDao.getCustomerReferenceId(caseIdObj, customerReferenceId);
		if (currentQueryStatus == null)
			throw new IBSException("Invalid Transaction Id");
		return currentQueryStatus;

	}

}