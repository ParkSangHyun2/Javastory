package javastory.budgetsh.stage4.share.service.budget;

import java.util.Collection;

import javastory.budgetsh.stage4.share.domain.budget.budget.Transaction;

public interface TransactionService {

	public void remove(String id);

	public void update(Transaction transaction);

	public boolean exist(String id);

	public Transaction retrieve(String id, String cashbookId);

	public boolean register(Transaction transaction);

	public Collection<Transaction> retrieveAll(String id);

}
