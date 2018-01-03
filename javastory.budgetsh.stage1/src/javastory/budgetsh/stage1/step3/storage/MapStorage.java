package javastory.budgetsh.stage1.step3.storage;

import java.util.LinkedHashMap;
import java.util.Map;

import javastory.budgetsh.stage1.step1.entity.account.AccountYearlyDue;
import javastory.budgetsh.stage1.step1.entity.budget.CashBook;
import javastory.budgetsh.stage1.step1.entity.budget.Transaction;

public class MapStorage {
	//
	private Map<String, CashBook> cashbookMap;
	private Map<String, Transaction> transactionMap;
	private Map<String, AccountYearlyDue> accountMap;

	private static MapStorage mapStorage;
	
	public MapStorage() {
		// 
		this.cashbookMap = new LinkedHashMap<String, CashBook>();
		this.transactionMap = new LinkedHashMap<String, Transaction>();
		this.accountMap = new LinkedHashMap<String, AccountYearlyDue>();
	}
	
	public static MapStorage getInstance() {
		// 
		if (mapStorage == null) {
			mapStorage = new MapStorage(); 
		}
		
		return mapStorage; 
	}
	
	public Map<String,CashBook> getCashBookMap() {
		return cashbookMap; 
	}
	
	public Map<String, Transaction> getTransactionMap() {
		return transactionMap;
	}
	
	public Map<String, AccountYearlyDue> getAccountMap(){
		return accountMap;
	}
}
