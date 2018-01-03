package javastory.budgetsh.stage4.client.transfer.stub.budget;

import java.util.Collection;

import javastory.budgetsh.stage4.client.dto.budget.MonthlyDue;
import javastory.budgetsh.stage4.client.service.budget.AccountMonthlyService;

public class AccountMonthlyServiceStub implements AccountMonthlyService{

	@Override
	public boolean exist(int month, String yearlyDueAccount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void register(MonthlyDue targetMonthlyDue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String yearlyDueAccount, int month) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String yearlyDueAccount, MonthlyDue monthlyDue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<MonthlyDue> findAll(String yearlyDueAccount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonthlyDue retrieve(String yearlyDueAccount, int month) {
		// TODO Auto-generated method stub
		return null;
	}

}
