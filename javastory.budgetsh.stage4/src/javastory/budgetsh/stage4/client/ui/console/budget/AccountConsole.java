package javastory.budgetsh.stage4.client.ui.console.budget;

import javastory.budgetsh.stage4.client.transfer.stub.budget.ServiceLogicLycler;
import javastory.budgetsh.stage4.share.domain.budget.account.AccountYearlyDue;
import javastory.budgetsh.stage4.share.domain.share.IdName;
import javastory.budgetsh.stage4.share.service.budget.AccountYearlyService;
import javastory.budgetsh.stage4.share.util.ConsoleUtil;
import javastory.budgetsh.stage4.share.util.Narrator;
import javastory.budgetsh.stage4.share.util.TalkingAt;

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
