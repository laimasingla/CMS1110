package com.cg.ibs.cardmanagement.dao;

import java.math.BigInteger;
import java.util.List;

import com.cg.ibs.cardmanagement.bean.CaseIdBean;
import com.cg.ibs.cardmanagement.bean.CreditCardTransaction;
import com.cg.ibs.cardmanagement.bean.DebitCardTransaction;

public interface BankDao {

	List<CaseIdBean> viewAllQueries();

	List<CreditCardTransaction> getCreditTrans(int days, BigInteger creditCardNumber);

	List<DebitCardTransaction> getDebitTrans(int dys, BigInteger debitCardNumber);

	boolean verifyCreditCardNumber(BigInteger creditCardNumber);

	boolean verifyDebitCardNumber(BigInteger debitCardNumber);

	boolean verifyQueryId(String queryId);

	void setQueryStatus(String queryId, String newStatus);

}
