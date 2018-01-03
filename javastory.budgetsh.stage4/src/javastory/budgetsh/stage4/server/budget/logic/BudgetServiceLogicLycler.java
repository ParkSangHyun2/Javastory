package javastory.budgetsh.stage4.server.budget.logic;

import javastory.budgetsh.stage4.server.budget.service.AccountMonthlyService;
import javastory.budgetsh.stage4.server.budget.service.AccountYearlyService;
import javastory.budgetsh.stage4.server.budget.service.CashBookService;
import javastory.budgetsh.stage4.server.budget.service.BudgetServiceLycler;
import javastory.budgetsh.stage4.server.budget.service.TransactionService;

public class BudgetServiceLogicLycler implements BudgetServiceLycler {
	//
	private static BudgetServiceLycler lycler;

	private AccountMonthlyService accountMonthlyService;
	private AccountYearlyService accountYearlyService;
	private CashBookService cashBookService;
	private TransactionService transactionService;

	public BudgetServiceLogicLycler() {
		//
	}

	public synchronized static BudgetServiceLycler shareInstance() {
		//
		if (lycler == null) {
			lycler = new BudgetServiceLogicLycler();
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
