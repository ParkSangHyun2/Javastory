package javastory.budgetsh.stage2.budget.service;

import java.util.Collection;

import javastory.budgetsh.stage2.budget.entity.budget.Transaction;

public interface TransactionService {

	void remove(String id);

	void update(Transaction transaction);

	boolean exist(String id);

	Transaction retrieve(String id, String cashbookId);

	boolean register(Transaction transaction);

	Collection<Transaction> retrieveAll(String id);

}
