package javastory.budgetsh.stage4.server.budget.store;

import java.util.Collection;

import javastory.budgetsh.stage4.server.budget.entity.budget.Transaction;

public interface TransactionStore {
	//
	void remove(String id);
	void update(Transaction transaction);
	boolean exist(String id);
	Transaction retrieve(String id, String id2);
	boolean register(Transaction transaction);
	Collection<Transaction> retrieveAll(String id);

}
