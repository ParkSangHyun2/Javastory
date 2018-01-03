package javastory.budgetsh.stage4.client.transfer.stub.budget;

import java.util.Collection;

import javastory.budgetsh.stage4.client.dto.budget.AccountYearlyDue;
import javastory.budgetsh.stage4.client.dto.budget.MonthlyDue;
import javastory.budgetsh.stage4.client.service.budget.AccountYearlyService;

public class AccountYearlyServiceStub implements AccountYearlyService{

	@Override
	public boolean exist(String account) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void remove(String account) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(AccountYearlyDue yearlyDue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AccountYearlyDue retrieve(String account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void regist(AccountYearlyDue yearlyDue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<MonthlyDue> retrieveMonthlyDue(String account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<AccountYearlyDue> retrieveAll(String account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addMonthlyDue(String year, String account, int month, String amount, String id, String name,
			String type) {
		// TODO Auto-generated method stub
		
	}

}
