package javastory.budgetsh.stage1.step3.ui.console;

import javastory.budgetsh.stage1.step1.entity.account.AccountYearlyDue;
import javastory.budgetsh.stage1.step1.share.IdName;
import javastory.budgetsh.stage1.step3.storage.AccountMapStore;
import javastory.budgetsh.stage1.util.ConsoleUtil;
import javastory.budgetsh.stage1.util.Narrator;
import javastory.budgetsh.stage1.util.TalkingAt;

public class AccountConsole {
	//
	private AccountMapStore accountMapStore;
	
	private Narrator narrator;
	private ConsoleUtil consoleUtil;
	
	public AccountConsole() {
		//
		accountMapStore = new AccountMapStore();
		
		narrator = new Narrator(this, TalkingAt.Left);
		consoleUtil = new ConsoleUtil(narrator);
	}
	
	public AccountYearlyDue register() {
		//
		AccountYearlyDue yearlyDue;
		
		String account = consoleUtil.getValueOf("\n Account(0.Account Menu)");
		if(account.equals("") || account.equals("0")|| account == null) {
			return null;
		}
		
		if(accountMapStore.exist(account)) {
			narrator.sayln("\n  >Already exist Account Number--> " + account);
		}
		
		String name = consoleUtil.getValueOf("\n Name");
		int year = Integer.parseInt(consoleUtil.getValueOf("\n > Year"));
		
		yearlyDue = new AccountYearlyDue(new IdName(account,name), year);
		
		accountMapStore.regist(yearlyDue);
		narrator.sayln(yearlyDue.toString());
		
		return yearlyDue;
	}

	public AccountYearlyDue find() {
		//
		AccountYearlyDue yearlyDue = new AccountYearlyDue();
		
		String account = consoleUtil.getValueOf("\n Account(0.Account Menu)");
		if(account.equals("") || account.equals("0")|| account == null) {
			return null;
		}
		
		if(!accountMapStore.exist(account)) {
			narrator.sayln("\n > No yearlyDue in storage");
			return null;
		}
		
		yearlyDue =accountMapStore.retrieve(account);
		
		narrator.sayln(yearlyDue.toString());
		
		return yearlyDue;
	}

	public void modify() {
		//
		AccountYearlyDue yearlyDue= new AccountYearlyDue();;
		
		String account = consoleUtil.getValueOf("\n Account(0.Account Menu)");
		if(account.equals("") || account.equals("0")|| account == null) {
			return;
		}
		
		if(!accountMapStore.exist(account)) {
			narrator.sayln("\n > No yearlyDue in storage");
			return;
		}
		
		String name = consoleUtil.getValueOf("\n Name(Enter. no change)");
		if(name.equals("0")) {
			return;
		}
		
		int year = Integer.parseInt(consoleUtil.getValueOf("\n Year(Enter. no change)"));
		if(year == 0) {
			return;
		}
		
		yearlyDue = new AccountYearlyDue(new IdName(account,name),year);
		
		narrator.sayln("> modified :" + yearlyDue.toString());
		accountMapStore.update(yearlyDue);
	}

	public void remove() {
		//
		String account = consoleUtil.getValueOf("\n Account(0.Account Menu)");
		if(account.equals("") || account.equals("0")|| account == null) {
			return;
		}
		
		if(!accountMapStore.exist(account)) {
			narrator.sayln("\n > No yearlyDue in storage");
			return;
		}
		
		accountMapStore.remove(account);
	}
}
