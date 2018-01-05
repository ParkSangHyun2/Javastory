package javastory.budgetsh.stage4.client.fxui.event;

import java.util.ArrayList;

import javastory.budgetsh.stage4.client.transfer.stub.budget.ServiceLogicLycler;
import javastory.budgetsh.stage4.share.domain.budget.account.AccountYearlyDue;
import javastory.budgetsh.stage4.share.domain.budget.account.MonthlyDue;
import javastory.budgetsh.stage4.share.service.budget.AccountYearlyService;

public class YearlyDueEventHelper {
	//
	private AccountYearlyService accountYearlyService = ServiceLogicLycler.shareInstance().createYearlyService();

	public ArrayList<MonthlyDue> retrieveMonthyAll(String yearlyDueAccount, String year) {
		//
		AccountYearlyDue yearlyDue = null;
		ArrayList<AccountYearlyDue> yearlyDues = (ArrayList<AccountYearlyDue>) accountYearlyService
				.retrieveAll(yearlyDueAccount);
		if(yearlyDues == null) {
			return null;
		}
		for (AccountYearlyDue foundYearlyDue : yearlyDues) {
			if (foundYearlyDue.getYear().equals(year)) {
				yearlyDue = foundYearlyDue;
			}
		}
		return (ArrayList<MonthlyDue>) yearlyDue.getMonthlyDues();
	}

	public ArrayList<AccountYearlyDue> retrieveYearlyAll(String yearlyDueAccount) {
		//
		return (ArrayList<AccountYearlyDue>) accountYearlyService.retrieveAll(yearlyDueAccount);
	}

	public void remove(String account, MonthlyDue monthlyDue,String year) {
		//
		AccountYearlyDue yearlyDue = null;
		ArrayList<AccountYearlyDue> yearlyDues = (ArrayList<AccountYearlyDue>) accountYearlyService.retrieveAll(account);
		for (AccountYearlyDue foundYearlyDue : yearlyDues) {
			if (foundYearlyDue.getYear().equals(year)) {
				yearlyDue = foundYearlyDue;
			}
		}
		
		ArrayList<MonthlyDue> modifiedMonthlyDue = new ArrayList<>();
		for (MonthlyDue foundMonthlyDue : yearlyDue.getMonthlyDues()) {
			if (foundMonthlyDue.getMonth() != monthlyDue.getMonth()) {
				modifiedMonthlyDue.add(foundMonthlyDue);
			}
		}
		AccountYearlyDue modifiedYearlyDue = yearlyDue;
		modifiedYearlyDue.setMonthlyDues(modifiedMonthlyDue);
		accountYearlyService.update(modifiedYearlyDue);
	}

	public void regist(String year, String account, Integer month, String type, String amount, String clubName) {
		//
		accountYearlyService.addMonthlyDue(year, account, month, amount, "id", clubName, type);
	}
}
