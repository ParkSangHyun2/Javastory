package javastory.budgetsh.stage4.client.ui.budget.console;

import javastory.budgetsh.stage4.client.dto.budget.CashBook;
import javastory.budgetsh.stage4.client.dto.budget.Travel;
import javastory.budgetsh.stage4.client.dto.club.share.TravelClub;
import javastory.budgetsh.stage4.client.dto.share.DatePair;
import javastory.budgetsh.stage4.client.dto.share.IdName;
import javastory.budgetsh.stage4.client.dto.share.Socialian;
import javastory.budgetsh.stage4.client.service.budget.CashBookService;
import javastory.budgetsh.stage4.client.transfer.stub.budget.ServiceLogicLycler;
import javastory.budgetsh.stage4.client.util.ConsoleUtil;
import javastory.budgetsh.stage4.client.util.Narrator;
import javastory.budgetsh.stage4.client.util.TalkingAt;


public class CashBookConsole {
	//
	private ConsoleUtil consoleUtil;
	private Narrator narrator;

	private CashBookService cashbookService;

	public CashBookConsole() {
		//
		narrator = new Narrator(this, TalkingAt.Left);
		consoleUtil = new ConsoleUtil(narrator);

		cashbookService = ServiceLogicLycler.shareInstance().createCashBookService();
	}

	public CashBook register(TravelClub travelClub) {
		//mofify
		CashBook cashbook = null;
		Travel travel;
		Socialian socialian;

		while (true) {
			//
			String bankAccount = consoleUtil.getValueOf("\n Cashbook Bank Account(0.cashbook menu)");
			if (bankAccount.equals("0") || bankAccount.equals("") || bankAccount == null) {
				break;
			}
			if (cashbookService.exist(bankAccount)) {
				narrator.sayln("already exist this Account. -->" + bankAccount);
				continue;
			}

			String clubName = travelClub.getName();
			String travelName = clubName+"001";
			travel = new Travel(travelName);
			
			travel.setClub(new IdName(travel.getId(), clubName));

			String leaderSocialId = consoleUtil.getValueOf("\n SocialId");
			String leaderFirstName = consoleUtil.getValueOf("\n First Name");
			String leaderFamilyName = consoleUtil.getValueOf("\n FamilyName");
			String leaderEmail = consoleUtil.getValueOf("\n Leader Email");
			String leaderPhone = consoleUtil.getValueOf("\n Leader Phone Number");
			socialian = new Socialian(leaderSocialId, leaderFirstName, leaderFamilyName, leaderEmail);
			socialian.setPhone(leaderPhone);

			double monthlyDue = Double.parseDouble(consoleUtil.getValueOf("\n monthlyDue"));
			double budgetGoal = Double.parseDouble(consoleUtil.getValueOf("\n Budget Goal"));
			String startDate = consoleUtil.getValueOf("\n startDate(ex >2017-03-23)");
			String endDate = consoleUtil.getValueOf("\n End Date(ex > 2017-04-04)");

			cashbook = new CashBook(travel, monthlyDue, budgetGoal, new DatePair(startDate, endDate));
			cashbook.setBankAccount(bankAccount);
			cashbook.setClub(travel.getClub());
			cashbook.setLeader(socialian);

			cashbookService.regist(cashbook);
			narrator.sayln("Regist -->" + cashbook);
			break;
		}
		return cashbook;

	}

	public CashBook find() {
		//
		CashBook cashbook = null;

		String account = consoleUtil.getValueOf("\n Cashbook Account(0.cashbook Menu)");
		if (account.equals("0") || account.equals("") || account == null) {
			return null;
		}

		if (cashbookService.exist(account)) {
			cashbook = cashbookService.retrieve(account);
			narrator.sayln("\n > Found Cashbook" + cashbook);
			return cashbook;
		} else {
			narrator.sayln(" > No cashbook in storage -->" + account);
		}

		return cashbook;
	}

	public CashBook modify() {
		//
		CashBook foundCashbook = find();
		String temp;

		double monthlyDue;
		double budgetGoal;
		String endDate;

		if (foundCashbook != null) {
			if (!(temp = consoleUtil.getValueOf("\n >monthly Due (Enter. no change):")).equals("")) {
				monthlyDue = Double.parseDouble(temp);
			} else {
				monthlyDue = foundCashbook.getMonthlyDue();
			}
			if (!(temp = consoleUtil.getValueOf("\n >budgetGoal (Enter. no change):")).equals("")) {
				budgetGoal = Double.parseDouble(temp);
			} else {
				budgetGoal = foundCashbook.getBudgetGoal();
			}

			endDate = consoleUtil.getValueOf("\n > End Date (Enter. no change): ");
			if (endDate.equals("")) {
				endDate = foundCashbook.getTerm().getEndDate();
			}

			foundCashbook.setTerm(new DatePair(foundCashbook.getTerm().getStartDate(), endDate));
			foundCashbook.setBudgetGoal(budgetGoal);
			foundCashbook.setMonthlyDue(monthlyDue);

			cashbookService.update(foundCashbook);

		} else {
			narrator.sayln("\n >No cashbook in the storage");
		}

		return foundCashbook;
	}

	public void remove() {
		//
		CashBook foundCashbook;

		String bankAccount = consoleUtil.getValueOf("\n Cashbook Bank Account(0.cashbook menu)");
		if (bankAccount.equals("0") || bankAccount.equals("") || bankAccount == null) {
			return;
		}
		if (cashbookService.exist(bankAccount)) {
			foundCashbook = cashbookService.retrieve(bankAccount);
			cashbookService.remove(foundCashbook);
			narrator.sayln("removed -->" + foundCashbook.getBankAccount());
		}
	}

}
