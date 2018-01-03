package javastory.budgetsh.stage2.budget.logic;

import javastory.budgetsh.stage2.budget.service.AccountMonthlyService;
import javastory.budgetsh.stage2.budget.service.AccountYearlyService;
import javastory.budgetsh.stage2.budget.service.CashBookService;
import javastory.budgetsh.stage2.budget.service.ServiceLycler;
import javastory.budgetsh.stage2.budget.service.TransactionService;

public class ServiceLogicLycler implements ServiceLycler {
	//
	private static ServiceLycler lycler;

	private AccountMonthlyService accountMonthlyService;
	private AccountYearlyService accountYearlyService;
	private CashBookService cashBookService;
	private TransactionService transactionService;

	public ServiceLogicLycler() {
		//
	}

	public synchronized static ServiceLycler shareInstance() {
		//
		if (lycler == null) {
			lycler = new ServiceLogicLycler();
		}

		return lycler;
	}

	@Override
	public AccountMonthlyService createMonthlyService() {
		//
		if (accountMonthlyService == null) {
			accountMonthlyService = new AccountMonthlyServiceLogic();
		}
		return accountMonthlyService;
	}

	@Override
	public AccountYearlyService createYearlyService() {
		// 
		if (accountYearlyService == null) {
			accountYearlyService = new AccountYearlyServiceLogic();
		}
		return accountYearlyService;
	}

	@Override
	public CashBookService createCashBookService() {
		//
		if (cashBookService == null) {
			cashBookService = new CashBookServiceLogic();
		}
		return cashBookService;
	}

	@Override
	public TransactionService createTransactionService() {
		// 
		if (transactionService == null) {
			transactionService = new TransactionServiceLogic();
		}
		return transactionService;
	}

}
