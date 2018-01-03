package javastory.budgetsh.stage4.server.budget.logic;

import java.util.Collection;

import javastory.budgetsh.stage4.server.budget.da.file.StoreFileLycler;
import javastory.budgetsh.stage4.server.budget.entity.budget.Transaction;
import javastory.budgetsh.stage4.server.budget.service.TransactionService;
import javastory.budgetsh.stage4.server.budget.store.CashBookStore;
import javastory.budgetsh.stage4.server.budget.store.TransactionStore;
import javastory.budgetsh.stage4.server.budget.util.FailureException;
import javastory.budgetsh.stage4.server.budget.util.NoSuchTransactionException;

public class TransactionServiceLogic implements TransactionService{
	//
	private CashBookStore cashbookStore;
	private TransactionStore transactionStore;
	
	public TransactionServiceLogic() {
		//
		cashbookStore = StoreFileLycler.shareInstance().createCashBookStore();
		transactionStore = StoreFileLycler.shareInstance().createTransactionStore();
	}
	@Override
	public void remove(String id) {
		//
		transactionStore.remove(id);
		if(exist(id)) {
			throw new FailureException("Failed to remove transaction");
		}
	}

	@Override
	public void update(Transaction transaction) {
		//
		if(transaction == null) {
			throw new NoSuchTransactionException("no such transaction");
		}
		transactionStore.update(transaction);
	}

	@Override
	public boolean exist(String id) {
		//
		if(transactionStore.exist(id)) {
			return true;
		}
		return false;
	}

	@Override
	public Transaction retrieve(String id, String cashbookId) {
		//
		Transaction foundTransaction;
		foundTransaction = transactionStore.retrieve(id,cashbookId);//return transactino
		return foundTransaction;
	}

	@Override
	public boolean register(Transaction transaction) {
		//
		if(transactionStore.register(transaction)) {
			if(!(exist(transaction.getId()))) {
				System.out.println(transaction.getId());
				throw new FailureException("Failed to Register.");
			}
			return true;
		}
		return false;
	}

	@Override
	public Collection<Transaction> retrieveAll(String cashBookid) {
		// 
		return transactionStore.retrieveAll(cashBookid);
	}

}
