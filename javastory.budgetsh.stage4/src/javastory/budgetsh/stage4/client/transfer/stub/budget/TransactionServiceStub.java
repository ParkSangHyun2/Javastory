package javastory.budgetsh.stage4.client.transfer.stub.budget;

import java.util.Collection;

import javastory.budgetsh.stage4.client.dto.budget.Transaction;
import javastory.budgetsh.stage4.client.service.budget.TransactionService;

public class TransactionServiceStub implements TransactionService{

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Transaction transaction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exist(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Transaction retrieve(String id, String cashbookId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean register(Transaction transaction) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Transaction> retrieveAll(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
