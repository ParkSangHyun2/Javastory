package javastory.budgetsh.stage3.budget.ui.console;

import javastory.budgetsh.stage3.budget.entity.account.AccountYearlyDue;
import javastory.budgetsh.stage3.budget.logic.ServiceLogicLycler;
import javastory.budgetsh.stage3.budget.service.AccountYearlyService;
import javastory.budgetsh.stage3.budget.share.IdName;
import javastory.budgetsh.stage3.budget.util.ConsoleUtil;
import javastory.budgetsh.stage3.budget.util.Narrator;
import javastory.budgetsh.stage3.budget.util.TalkingAt;

public class AccountConsole {
	//
	private AccountYearlyService accountYearlyService;

	private Narrator narrator;
	private ConsoleUtil consoleUtil;

	public AccountConsole() {
		//
		accountYearlyService = ServiceLogicLycler.shareInstance().createYearlyService();

		narrator = new Narrator(this, TalkingAt.Left);
		consoleUtil = new ConsoleUtil(narrator);
	}

	public AccountYearlyDue register() {
		//
		AccountYearlyDue yearlyDue;

		String account = consoleUtil.getValueOf("\n Account(0.Account Menu)");
		if (account.equals("") || account.equals("0") || account == null) {
			return null;
		}

		if (accountYearlyService.exist(account)) {
			narrator.sayln("\n  >Already exist Account Number--> " + account);
		}

		String name = consoleUtil.getValueOf("\n Name");
		int year = Integer.parseInt(consoleUtil.getValueOf("\n > Year"));

		yearlyDue = new AccountYearlyDue(new IdName(account, name), year);

		accountYearlyService.regist(yearlyDue);
		narrator.sayln(yearlyDue.toString());

		return yearlyDue;
	}

	public AccountYearlyDue find() {
		//
		AccountYearlyDue yearlyDue = new AccountYearlyDue();

		String account = consoleUtil.getValueOf("\n Account(0.Account Menu)");
		if (account.equals("") || account.equals("0") || account == null) {
			return null;
		}

		if (!accountYearlyService.exist(account)) {
			narrator.sayln("\n > No yearlyDue in storage");
			return null;
		}

		yearlyDue = accountYearlyService.retrieve(account);

		narrator.sayln(yearlyDue.toString());

		return yearlyDue;
	}

	public void modify() {
		//
		AccountYearlyDue yearlyDue = new AccountYearlyDue();

		String account = consoleUtil.getValueOf("\n Account(0.Account Menu)");
		if (account.equals("") || account.equals("0") || account == null) {
			return;
		}

		if (!accountYearlyService.exist(account)) {
			narrator.sayln("\n > No yearlyDue in storage");
			return;
		}

		String name = consoleUtil.getValueOf("\n Name(Enter. no change)");
		if (name.equals("0")) {
			return;
		}

		int year = Integer.parseInt(consoleUtil.getValueOf("\n Year(Enter. no change)"));
		if (year == 0) {
			return;
		}

		yearlyDue = new AccountYearlyDue(new IdName(account, name), year);

		narrator.sayln("> modified :" + yearlyDue.toString());
		accountYearlyService.update(yearlyDue);
	}

	public void remove() {
		//
		String account = consoleUtil.getValueOf("\n Account(0.Account Menu)");
		if (account.equals("") || account.equals("0") || account == null) {
			return;
		}

		if (!accountYearlyService.exist(account)) {
			narrator.sayln("\n > No yearlyDue in storage");
			return;
		}

		accountYearlyService.remove(account);
	}
}
