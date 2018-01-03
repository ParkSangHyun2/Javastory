package javastory.budgetsh.stage3.budget.ui.console;

import java.util.ArrayList;
import java.util.Collection;

import javastory.budgetsh.stage3.budget.entity.account.AccountYearlyDue;
import javastory.budgetsh.stage3.budget.entity.account.DuesType;
import javastory.budgetsh.stage3.budget.entity.account.MonthlyDue;
import javastory.budgetsh.stage3.budget.logic.ServiceLogicLycler;
import javastory.budgetsh.stage3.budget.service.AccountMonthlyService;
import javastory.budgetsh.stage3.budget.share.IdName;
import javastory.budgetsh.stage3.budget.util.ConsoleUtil;
import javastory.budgetsh.stage3.budget.util.Narrator;
import javastory.budgetsh.stage3.budget.util.TalkingAt;

public class AccountMonthlyConsole {
	//
	private Narrator narrator;
	private ConsoleUtil consoleUtil;

	private AccountMonthlyService accountMonthlyService;

	public AccountMonthlyConsole() {
		//
		narrator = new Narrator(this, TalkingAt.Left);
		consoleUtil = new ConsoleUtil(narrator);

		accountMonthlyService = ServiceLogicLycler.shareInstance().createMonthlyService();
	}

	public void add(AccountYearlyDue currentYearlyDue) {
		//
		if (currentYearlyDue == null) {
			narrator.sayln("select YearlyDue first.");
			return;
		}
		narrator.sayln("current YearlyDue -->" + currentYearlyDue.toString());
		MonthlyDue targetMonthlyDue;
		int month;
		String yearlyDueAccount = currentYearlyDue.getMember().getId();

		while (true) {
			month = Integer.parseInt(consoleUtil.getValueOf("\n > Month"));
			if (month >= 1 && month <= 12) {
				break;
			} else {
				narrator.say("\n Invalid Month -->" + month);
			}
		}

		// if (currentYearlyDue.getMonthlyDues() != null) {
		// for (MonthlyDue monthlyDue : currentYearlyDue.getMonthlyDues()) {
		// if (monthlyDue.getMonth() == month) {
		// narrator.sayln("\n > already exist month -->" + month);
		// }
		// }
		// }
		if (accountMonthlyService.exist(month, yearlyDueAccount)) {
			narrator.sayln("\n > already exist month -->" + month);
			return;
		}

		double amount = Double.parseDouble(consoleUtil.getValueOf("\n> amount"));
		// String travelId = consoleUtil.getValueOf("\n > Travel Id");
		String travelId = currentYearlyDue.getMember().getId();
		String travelName = consoleUtil.getValueOf("\n > Travel Name");

		targetMonthlyDue = new MonthlyDue(month, amount, new IdName(travelId, travelName));

		if (currentYearlyDue == null || currentYearlyDue.getMonthlyDues() == null || targetMonthlyDue == null) {
			narrator.say("null");
		}

		// currentYearlyDue.addMonthlyDue(month, amount, travelId, travelName);
		// accountFileStore.update(currentYearlyDue);

		accountMonthlyService.register(targetMonthlyDue);

	}

	public void remove(AccountYearlyDue currentYearlyDue) {
		//
		if (currentYearlyDue == null) {
			narrator.sayln("select YearlyDue first.");
			return;
		}
		// Collection<MonthlyDue> monthlyDueList = findAll(currentYearlyDue);
		int month = Integer.parseInt(consoleUtil.getValueOf("\n > Month"));

		// for (MonthlyDue monthlyDue : monthlyDueList) {
		// if (monthlyDue.getMonth() == month) {
		// int index = currentYearlyDue.getMonthlyDues().indexOf(monthlyDue);
		// currentYearlyDue.getMonthlyDues().remove(index);
		// }
		// }
		String yearlyDueAccount = currentYearlyDue.getMember().getId();
		accountMonthlyService.remove(yearlyDueAccount, month);

	}

	public void modify(AccountYearlyDue currentYearlyDue) {
		//
		if (currentYearlyDue == null) {
			narrator.sayln("select YearlyDue first.");
			return;
		}
		int month;
		String yearlyDueAccount = currentYearlyDue.getMember().getId();
		String inMonth = consoleUtil.getValueOf("\n > Month");
		if (inMonth.equals("")) {
			return;
		} else {
			month = Integer.parseInt(inMonth);
		}

		MonthlyDue monthlyDue = accountMonthlyService.retrieve(yearlyDueAccount, month);

		Double amount = Double.parseDouble(consoleUtil.getValueOf("\n > Amount(Enter. no change)"));
		String type = consoleUtil.getValueOf("\n > Type(Regular | Danation | Fine)>");
		DuesType newType = DuesType.Regular;
		if (type.equals("Regular")) {
			newType = DuesType.Regular;
		}
		if (type.equals("Donation")) {
			newType = DuesType.Donation;
		}
		if (type.equals("Fine")) {
			newType = DuesType.Fine;
		}

		monthlyDue.setType(newType);
		monthlyDue.setAmount(amount);

		accountMonthlyService.update(yearlyDueAccount, monthlyDue);
		narrator.sayln("Modified monthlyDue" + monthlyDue.toString());
	}

	public Collection<MonthlyDue> findAll(AccountYearlyDue currentYearlyDue) {
		//
		Collection<MonthlyDue> monthlyDueList = new ArrayList<>();
		if (currentYearlyDue == null) {
			narrator.sayln("select YearlyDue first.");
			return null;
		}

		String yearlyDueAccount = currentYearlyDue.getMember().getId();
		monthlyDueList = accountMonthlyService.findAll(yearlyDueAccount);

		if (monthlyDueList == null) {
			narrator.sayln("\n <<No recode in your Account>>");
			return null;
		}
		for (MonthlyDue monthlyDue : monthlyDueList) {
			narrator.sayln(monthlyDue.toString());
		}

		return monthlyDueList;
	}

}
