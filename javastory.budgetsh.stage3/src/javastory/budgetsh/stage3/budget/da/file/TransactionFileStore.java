package javastory.budgetsh.stage3.budget.da.file;

import java.util.Collection;

import javastory.budgetsh.stage3.budget.da.file.io.TransactionFile;
import javastory.budgetsh.stage3.budget.entity.budget.Transaction;
import javastory.budgetsh.stage3.budget.store.TransactionStore;

public class TransactionFileStore implements TransactionStore {
	//
	private TransactionFile transactionFile;

	public TransactionFileStore() {
		//
		transactionFile = new TransactionFile();
	}

	public boolean register(Transaction transaction) {
		//
		transactionFile.write(transaction);
		return true;
	}

	public boolean exist(String id) {
		//
		if(transactionFile.exist(id)) {
			return true;
		}
		return false;
	}

	public Transaction retrieve(String id, String cashbookId) {
		//
		return transactionFile.read(id, cashbookId);
	}

	public Collection<Transaction> retrieveAll(String cashbookId) {
		//
		return transactionFile.retrieveAll(cashbookId);
	}

	public void update(Transaction transaction) {
		//
		transactionFile.update(transaction);
	}

	public void remove(String id) {
		//
		transactionFile.remove(id);
	}
}
