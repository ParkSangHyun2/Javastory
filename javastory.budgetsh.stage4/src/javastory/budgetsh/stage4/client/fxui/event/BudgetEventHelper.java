package javastory.budgetsh.stage4.client.fxui.event;

import java.util.ArrayList;

import javastory.budgetsh.stage4.client.dto.budget.CashBook;
import javastory.budgetsh.stage4.client.dto.budget.Transaction;
import javastory.budgetsh.stage4.client.dto.budget.Travel;
import javastory.budgetsh.stage4.client.dto.share.DatePair;
import javastory.budgetsh.stage4.client.dto.share.IdName;
import javastory.budgetsh.stage4.client.dto.share.Socialian;
import javastory.budgetsh.stage4.client.service.budget.CashBookService;
import javastory.budgetsh.stage4.client.service.budget.TransactionService;
import javastory.budgetsh.stage4.client.transfer.stub.budget.ServiceLogicLycler;


public class BudgetEventHelper {
	//
	private CashBookService cashbookService = ServiceLogicLycler.shareInstance().createCashBookService();
	private TransactionService transactionService = ServiceLogicLycler.shareInstance().createTransactionService();

	public ArrayList<CashBook> retrieveCashbook() {
		// 
		ArrayList<CashBook> cashbookList = cashbookService.retrieveAll();
		
		if(cashbookList == null) {
			return null;
		}
		return cashbookList;
	}
	//

	public boolean registCashbook(String travelName, String clubName, String leaderName, String account,
			String monthlyDue, String budgetGoal, String startDate, String endDate, String memo) {
		//
		CashBook cashbook = new CashBook(
				new Travel((new IdName(account, travelName)),travelName, new Socialian("","",leaderName,""), new DatePair(startDate, endDate)),
				Double.parseDouble(monthlyDue),
				Double.parseDouble(budgetGoal), 
				new DatePair(startDate,endDate),
				memo,
				account);
		if(cashbookService.regist(cashbook)) {
			return true;
		}
		return false;
	}

	public void remove(CashBook foundCashbook) {
		//
		cashbookService.remove(foundCashbook);
	}

	public ArrayList<Transaction> retrieveAllTransaction(String account) {
		//
		return (ArrayList<Transaction>) transactionService.retrieveAll(account);
	}

	public CashBook retrieveOneCashbook(String selectedCashbookName) {
		//
		ArrayList<CashBook> cashbookList = cashbookService.retrieveAll();
		for(CashBook cashbook : cashbookList) {
			if(cashbook.getClub().getName().equals(selectedCashbookName)) {
				return cashbook;
			}
		}
		return null;
	}

	public void addTransaction(Transaction transaction) {
		//
		transactionService.register(transaction);
	}

	public void removeTransaction(Transaction transaction) {
		// 
		transactionService.remove(transaction.getId());
	}

	public void update(String travelName, String clubName, String leaderName, String bankAccount, String monthlyDue,
			String budgetGoal, String startDate, String endDate, String memo) {
		//
		CashBook modifiedCashbook = new CashBook(
				new Travel((new IdName(bankAccount, travelName)),travelName, new Socialian("","",leaderName,""), new DatePair(startDate, endDate)),
				Double.parseDouble(monthlyDue),
				Double.parseDouble(budgetGoal), 
				new DatePair(startDate,endDate),
				memo,
				bankAccount);
		cashbookService.update(modifiedCashbook);
	}
	
}
