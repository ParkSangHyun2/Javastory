package javastory.budgetsh.stage4.client.transfer.stub.budget;

import java.util.ArrayList;

import javastory.budgetsh.stage4.client.dto.budget.CashBook;
import javastory.budgetsh.stage4.client.service.budget.CashBookService;

public class CashBookServiceStub implements CashBookService{

	@Override
	public boolean exist(String bankAccount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean regist(CashBook cashbook) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CashBook retrieve(String account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(CashBook foundCashbook) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(CashBook foundCashbook) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<CashBook> retrieveAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
