package javastory.budgetsh.stage1.step3.storage;

import java.util.Collection;
import java.util.Map;

import javastory.budgetsh.stage1.step1.entity.budget.CashBook;
import javastory.budgetsh.stage1.step1.entity.budget.Transaction;

public class TransactionMapStore {
	//
	private Map<String, CashBook> cashbookMap;
	private Map<String, Transaction> transactionMap;
	
	public TransactionMapStore() {
		//
		cashbookMap = MapStorage.getInstance().getCashBookMap();
		transactionMap = MapStorage.getInstance().getTransactionMap();
	}

	public boolean register(Transaction transaction) {
		// 
		if (exist(transaction.getId())) {
			return false;
		}
		transactionMap.put(transaction.getId(), transaction);
		return true;
	}
	
	public boolean exist(String id) {
		//
		if(transactionMap.get(id)!=null) {
			return true;
		}
		return false;
	}

	public Transaction retrieve(String id) {
		//
		Transaction transaction = transactionMap.get(id);
		return transaction;
	}

	public Collection<Transaction> retrieveAll() {
		//
		return transactionMap.values();
	}

	public void update(Transaction transaction) {
		//
		transactionMap.replace(transaction.getId(), transaction);
	}

	public void remove(String id) {
		//
		transactionMap.remove(id);
	}
}
