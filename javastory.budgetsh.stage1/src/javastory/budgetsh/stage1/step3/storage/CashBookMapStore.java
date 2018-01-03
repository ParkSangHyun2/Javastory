package javastory.budgetsh.stage1.step3.storage;

import java.util.Map;

import javastory.budgetsh.stage1.step1.entity.budget.CashBook;

public class CashBookMapStore {
	//
	private Map<String, CashBook> cashbookMap;
	
	public CashBookMapStore() {
		//
		this.cashbookMap = MapStorage.getInstance().getCashBookMap();
	}
	
	public boolean exist(String id) {
		//
		if (cashbookMap.get(id)==null) {
			return false;
		}
		
		return true;
	}

	public CashBook retrieve(String account) {
		// 
		CashBook cashbook = cashbookMap.get(account);
		
		return cashbook;
	}

	public void update(CashBook foundCashbook) {
		//
		cashbookMap.replace(foundCashbook.getBankAccount(), foundCashbook);
	}

	public void remove(CashBook foundCashbook) {
		//
		cashbookMap.remove(foundCashbook.getBankAccount());
	}

	public void regist(CashBook cashbook) {
		// 
		cashbookMap.put(cashbook.getBankAccount(), cashbook);
	}
	
	
	
	
}
