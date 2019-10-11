package com.cg.ibs.cardmanagement.service;

import java.math.BigInteger;
import java.util.List;

import com.cg.ibs.cardmanagement.bean.CaseIdBean;
import com.cg.ibs.cardmanagement.bean.CreditCardTransaction;
import com.cg.ibs.cardmanagement.bean.DebitCardTransaction;
import com.cg.ibs.cardmanagement.dao.BankDao;
import com.cg.ibs.cardmanagement.dao.CardManagementDaoImpl;
import com.cg.ibs.cardmanagement.exceptionhandling.IBSException;

public class BankServiceClassImpl implements BankService {

	BankDao bankDao = new CardManagementDaoImpl();
	CaseIdBean caseIdObj = new CaseIdBean();

	@Override
	public List<CaseIdBean> viewQueries() {

		List<CaseIdBean> caseBeans = bankDao.viewAllQueries();

		return bankDao.viewAllQueries();

	}
	public List<DebitCardTransaction> getDebitTransactions(int dys, BigInteger debitCardNumber) throws IBSException {
		boolean check = bankDao.verifyDebitCardNumber(debitCardNumber);
		if (check) {
			if (dys >= 1) {
				List<DebitCardTransaction> debitCardBeanTrns = bankDao.getDebitTrans(dys, debitCardNumber);
				if (debitCardBeanTrns.size() == 0)
					throw new IBSException("NO TRANSACTIONS");
				return bankDao.getDebitTrans(dys, debitCardNumber);
			} else {
				return null;
			}

		} else {
			return null;
		}
	}

	

	@Override
	public boolean verifyQueryId(String queryId) throws IBSException {

		boolean check = bankDao.verifyQueryId(queryId);
		if (check) {
			return true;
		} else {
		
				throw new IBSException("Invalid Query Id");
		}
	}

	@Override
	public void setQueryStatus(String queryId, String newStatus) {
	
		bankDao.setQueryStatus(queryId, newStatus);

	}
	
	public boolean verifyCreditCardNumber(BigInteger creditCardNumber) throws IBSException {

		boolean check1 = bankDao.verifyCreditCardNumber(creditCardNumber);
		if (!check1)
			throw new IBSException(" Credit Card Number does not exist");
		return (check1);
	}
	public boolean verifyDebitCardNumber(BigInteger debitCardNumber) throws IBSException {

		boolean check = bankDao.verifyDebitCardNumber(debitCardNumber);
		if (!check)
			throw new IBSException(" Debit Card Number does not exist");
		return (check);
	}
	@Override
	public List<CreditCardTransaction> getCreditTrans(int days, BigInteger creditCardNumber) throws IBSException {
		boolean check = bankDao.verifyCreditCardNumber(creditCardNumber);
		if (check) {
			if (days >= 1) {

				List<CreditCardTransaction> creditCardBeanTrns = bankDao.getCreditTrans(days, creditCardNumber);
				if (creditCardBeanTrns.size() == 0)
					throw new IBSException("NO TRANSACTIONS");
				return bankDao.getCreditTrans(days, creditCardNumber);
			} else {
				return null;
			}

		} else {
			return null;
		}
		
	}
	

}