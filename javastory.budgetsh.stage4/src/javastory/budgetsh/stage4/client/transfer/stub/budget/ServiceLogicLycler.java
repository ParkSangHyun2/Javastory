package javastory.budgetsh.stage4.client.transfer.stub.budget;

import javastory.budgetsh.stage4.share.service.budget.AccountMonthlyService;
import javastory.budgetsh.stage4.share.service.budget.AccountYearlyService;
import javastory.budgetsh.stage4.share.service.budget.BudgetServiceLycler;
import javastory.budgetsh.stage4.share.service.budget.CashBookService;
import javastory.budgetsh.stage4.share.service.budget.TransactionService;

public class ServiceLogicLycler implements BudgetServiceLycler {
	//
	private static BudgetServiceLycler lycler;

	private AccountMonthlyService accountMonthlyService;
	private AccountYearlyService accountYearlyService;
	private CashBookService cashBookService;
	private TransactionService transactionService;

	public ServiceLogicLycler() {
		//
	}

	public synchronized static BudgetServiceLycler shareInstance() {
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
			accountMonthlyService = new AccountMonthlyServiceStub();
		}
		return accountMonthlyService;
	}

	@Override
	public AccountYearlyService createYearlyService() {
		// 
		if (accountYearlyService == null) {
			accountYearlyService = new AccountYearlyServiceStub();
		}
		return accountYearlyService;
	}

	@Override
	public CashBookService createCashBookService() {
		//
		if (cashBookService == null) {
			cashBookService = new CashBookServiceStub();
		}
		return cashBookService;
	}

	@Override
	public TransactionService createTransactionService() {
		// 
		if (transactionService == null) {
			transactionService = new TransactionServiceStub();
		}
		return transactionService;
	}

}
