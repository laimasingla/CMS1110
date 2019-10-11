
package com.cg.ibs.cardmanagement.dao;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import com.cg.ibs.cardmanagement.bean.CaseIdBean;
import com.cg.ibs.cardmanagement.bean.CreditCardBean;
import com.cg.ibs.cardmanagement.bean.CreditCardTransaction;
import com.cg.ibs.cardmanagement.bean.DebitCardBean;
import com.cg.ibs.cardmanagement.bean.DebitCardTransaction;

public interface CustomerDao {
	
	void  newDebitCard(CaseIdBean caseIdObj,BigInteger accountNumber);
	void newCreditCard(CaseIdBean caseIdObjId);
	List <DebitCardBean> viewAllDebitCards();
	
	BigInteger getAccountNumber(BigInteger debitCardNumber);
	String getdebitCardType(BigInteger debitCardNumber);
	String requestDebitCardUpgrade(CaseIdBean caseIdObj, BigInteger debitCardNumber);
	List<CreditCardBean> viewAllCreditCards();
	void requestDebitCardLost(CaseIdBean caseIdObj, BigInteger debitCardNumber);
	void requestCreditCardLost(CaseIdBean caseIdObj, BigInteger creditCardNumber);
	boolean verifyAccountNumber(BigInteger accountNumber);
	boolean verifyDebitCardNumber(BigInteger debitCardNumber);
	boolean verifyCreditCardNumber(BigInteger creditCardNumber);
	void setNewDebitPin(BigInteger debitCardNumber, int newPin);
	int getDebitCardPin(BigInteger debitCardNumber);
	void setNewCreditPin(BigInteger creditCardNumber, int newPin);
	int getCreditCardPin(BigInteger creditCardNumber);

	String getcreditCardType(BigInteger creditCardNumber);
	boolean verifyCreditTransactionId(String transactionId);
	void raiseDebitMismatchTicket(CaseIdBean caseIdObj,String transactionId);
	void raiseCreditMismatchTicket(CaseIdBean caseIdObj, String transactionId);
	
	List<CreditCardTransaction> getCreditTrans(int days, BigInteger creditCardNumber);
	List<DebitCardTransaction> getDebitTrans(int dys, BigInteger debitCardNumber);
	
	void requestCreditCardUpgrade(CaseIdBean caseIdObj, BigInteger creditCardNumber);
	
	boolean verifyDebitTransactionId(String transactionId);
	String getCustomerReferenceId(CaseIdBean caseIdObj, String customerReferenceId);
}
