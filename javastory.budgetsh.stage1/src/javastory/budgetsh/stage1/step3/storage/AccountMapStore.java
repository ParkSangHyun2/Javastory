package javastory.budgetsh.stage1.step3.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javastory.budgetsh.stage1.step1.entity.account.AccountYearlyDue;
import javastory.budgetsh.stage1.step1.entity.account.MonthlyDue;

public class AccountMapStore {
	//
	private Map<String, AccountYearlyDue> accountMap;
	
	public AccountMapStore() {
		//
		accountMap = MapStorage.getInstance().getAccountMap();
	}

	public boolean exist(String account) {
		// 
		AccountYearlyDue yearlyDue= accountMap.get(account);
		
		if(yearlyDue==null) {
			return false;
		}
		
		return true;
	}

	public void regist(AccountYearlyDue yearlyDue) {
		//
		if(yearlyDue ==null) {
			return;
		}
		String id = yearlyDue.getMember().getId();
		
		accountMap.put(id, yearlyDue);
	}

	public AccountYearlyDue retrieve(String account) {
		//
		
		AccountYearlyDue yearlyDue;
		yearlyDue = accountMap.get(account);
		
		if(!exist(account)) {
			return null;
		}
		return yearlyDue;
	}

	public void update(AccountYearlyDue yearlyDue) {
		//
		String account = yearlyDue.getMember().getId();
		
		accountMap.replace(account, yearlyDue);
	}

	public void remove(String account) {
		// 
		accountMap.remove(account);
	}

	public List<MonthlyDue> findAllMonth(String id) {
		// 
		List<MonthlyDue> list = new ArrayList<>();
		for(MonthlyDue month : retrieve(id).getMonthlyDues()) {
			list.add(month);
		}
		
		return list;
	}
	
	
}
