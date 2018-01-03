package javastory.budgetsh.stage1.step3.ui.console;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javastory.budgetsh.stage1.step1.entity.account.AccountYearlyDue;
import javastory.budgetsh.stage1.step1.entity.account.DuesType;
import javastory.budgetsh.stage1.step1.entity.account.MonthlyDue;
import javastory.budgetsh.stage1.step1.share.IdName;
import javastory.budgetsh.stage1.step3.storage.AccountMapStore;
import javastory.budgetsh.stage1.util.ConsoleUtil;
import javastory.budgetsh.stage1.util.Narrator;
import javastory.budgetsh.stage1.util.TalkingAt;

public class AccountMonthlyConsole {
	//
	private Narrator narrator;
	private ConsoleUtil consoleUtil;

	private AccountMapStore accountMapStore;

	public AccountMonthlyConsole() {
		//
		narrator = new Narrator(this, TalkingAt.Left);
		consoleUtil = new ConsoleUtil(narrator);

		accountMapStore = new AccountMapStore();
	}

	public void add(AccountYearlyDue currentYearlyDue) {
		//
		narrator.sayln("current YearlyDue -->" + currentYearlyDue.toString());
		MonthlyDue targetMonthlyDue;

		int month = Integer.parseInt(consoleUtil.getValueOf("\n > Month"));

		if (currentYearlyDue.getMonthlyDues() != null) {
			for (MonthlyDue monthlyDue : currentYearlyDue.getMonthlyDues()) {
				if (monthlyDue.getMonth() == month) {
					narrator.sayln("\n > already exist month -->" + month);
				}
			}
		}

		double amount = Double.parseDouble(consoleUtil.getValueOf("\n> amount"));
		String travelId = consoleUtil.getValueOf("\n > Travel Id");
		String travelName = consoleUtil.getValueOf("\n > Travel Name");

		targetMonthlyDue = new MonthlyDue(month, amount, new IdName(travelId, travelName));

		if (currentYearlyDue==null || currentYearlyDue.getMonthlyDues()==null || targetMonthlyDue==null) {
			narrator.say("null");
		}
		currentYearlyDue.addMonthlyDue(month, amount, travelId, travelName);

	}

	public void remove(AccountYearlyDue currentYearlyDue) {
		//
		if (currentYearlyDue == null) {
			return;
		}
		Collection<MonthlyDue> monthlyDueList = findAll(currentYearlyDue);
		int month = Integer.parseInt(consoleUtil.getValueOf("\n > Month"));
		
		for (MonthlyDue monthlyDue : monthlyDueList) {
			if (monthlyDue.getMonth() == month) {
				int index = currentYearlyDue.getMonthlyDues().indexOf(monthlyDue);
				currentYearlyDue.getMonthlyDues().remove(index);
			}
		}
		accountMapStore.update(currentYearlyDue);

	}

	public void modify(AccountYearlyDue currentYearlyDue) {
		//
		if (currentYearlyDue == null) {
			return;
		}

		int month = Integer.parseInt(consoleUtil.getValueOf("\n > Month"));
		int index;
		for (MonthlyDue monthlyDue : currentYearlyDue.getMonthlyDues()) {
			if (monthlyDue.getMonth() == month) {
				index = currentYearlyDue.getMonthlyDues().indexOf(monthlyDue);
				currentYearlyDue.getMonthlyDues().remove(index);

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

				currentYearlyDue.getMonthlyDues().add(monthlyDue);

				accountMapStore.update(currentYearlyDue);
				narrator.sayln("Modified monthlyDue" + monthlyDue.toString());
				break;
			}
		}

	}

	public Collection<MonthlyDue> findAll(AccountYearlyDue currentYearlyDue) {
		//
		Collection<MonthlyDue> monthlyDueList = new ArrayList<>();
		if (currentYearlyDue == null) {
			return null;
		}
		List<MonthlyDue> foundMonthlyDueList = accountMapStore.findAllMonth(currentYearlyDue.getMember().getId());

		for (MonthlyDue monthlyDue : foundMonthlyDueList) {
			monthlyDueList.add(monthlyDue);
			narrator.sayln(monthlyDue.toString());
		}
		return monthlyDueList;
	}

}
