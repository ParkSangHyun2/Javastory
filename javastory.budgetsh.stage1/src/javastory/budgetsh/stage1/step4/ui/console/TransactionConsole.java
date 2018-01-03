package javastory.budgetsh.stage1.step4.ui.console;

import java.util.Collection;

import javastory.budgetsh.stage1.step1.entity.budget.CashBook;
import javastory.budgetsh.stage1.step1.entity.budget.Transaction;
import javastory.budgetsh.stage1.step1.entity.budget.tran.Expense;
import javastory.budgetsh.stage1.step1.entity.budget.tran.Forward;
import javastory.budgetsh.stage1.step1.entity.budget.tran.Revenue;
import javastory.budgetsh.stage1.step1.entity.budget.tran.TranItem;
import javastory.budgetsh.stage1.step1.share.IdName;
import javastory.budgetsh.stage1.step4.logic.ServiceLogicLycler;
import javastory.budgetsh.stage1.step4.service.CashBookService;
import javastory.budgetsh.stage1.step4.service.TransactionService;
import javastory.budgetsh.stage1.util.ConsoleUtil;
import javastory.budgetsh.stage1.util.Narrator;
import javastory.budgetsh.stage1.util.TalkingAt;

public class TransactionConsole {
	//
	private TransactionService transactionService;
	private CashBookService cashbookService;

	private Narrator narrator;
	private ConsoleUtil consoleUtil;

	public TransactionConsole() {
		//
		transactionService = ServiceLogicLycler.shareInstance().createTransactionService();
		cashbookService = ServiceLogicLycler.shareInstance().createCashBookService();

		narrator = new Narrator(this, TalkingAt.Left);
		consoleUtil = new ConsoleUtil(narrator);
	}

	public void register(CashBook currentBook) {
		//
		Transaction transaction;
		TranItem item;

		if (currentBook == null) {
			narrator.sayln("\n select Cashbook first!");
			return;
		}
		String title = consoleUtil.getValueOf("\n Transaction title");
		IdName account = new IdName(currentBook.getBankAccount(),
				currentBook.getLeader().getFamilyName() + currentBook.getLeader().getFirstName());
		while (true) {
			String tranType = consoleUtil.getValueOf("\n transaction type(Expense | Forward | Revenue)");
			if (tranType.equals("Expense")) {
				double amount = Double.parseDouble(consoleUtil.getValueOf("\n Amount"));
				double vat = Double.parseDouble(consoleUtil.getValueOf("\n Vat"));
				item = new Expense(amount, vat);
				break;
			}
			if (tranType.equals("Forward")) {
				double amount = Double.parseDouble(consoleUtil.getValueOf("\n Amount"));
				double vat = Double.parseDouble(consoleUtil.getValueOf("\n Vat"));
				item = new Forward(amount, vat);
				break;
			}
			if (tranType.equals("Revenue")) {
				double amount = Double.parseDouble(consoleUtil.getValueOf("\n Amount"));
				double vat = Double.parseDouble(consoleUtil.getValueOf("\n Vat"));
				item = new Revenue(amount, vat);
				break;
			}
		}
		String tranId = consoleUtil.getValueOf("\n set Transaction Id");

		transaction = new Transaction(currentBook.getId(), title, account, item, tranId);

		if (transactionService.register(transaction)) {
			narrator.sayln("\n Registered -->" + transaction.getTitle());
		} else {
			narrator.sayln("\n Rejected -->" + transaction.getTitle());
		}
	}

	public Transaction find(CashBook currentBook) {
		//
		if (currentBook == null) {
			narrator.sayln("select currentBook First!");
			return null;
		}
		Transaction transaction;

		String id = consoleUtil.getValueOf("\n Search");
		if (!transactionService.exist(id)) {
			narrator.sayln("\n No Id in the Storage..");
			return null;
		}
		transaction = transactionService.retrieve(id, currentBook.getId());

		if (!(transaction == null)) {
			narrator.sayln(transaction.toString());
		} else {
			narrator.sayln("The transaction can not be found.");
		}
		return transaction;
	}

	public void findAll(CashBook currentBook) {
		//
		if (currentBook == null) {
			narrator.sayln("select currentBook First!");
			return;
		}

		narrator.sayln("\n<Find All Transaction>");
		Collection<Transaction> foundList = transactionService.retrieveAll(currentBook.getId());

		if (foundList.isEmpty()) {
			narrator.sayln(" ...No Transaction in List!");
		} else {
			for (Transaction transaction : foundList) {
				narrator.sayln(transaction.toString());
			}
		}
	}

	public void modify(CashBook currentBook) {
		//
		Transaction transaction;

		if (currentBook == null) {
			narrator.sayln("select currentBook First!");
			return;
		}

		transaction = find(currentBook);

		String title = consoleUtil.getValueOf("\n title(Enter. no change)");
		if (title.equals("")) {
			title = transaction.getTitle();
		}

		String account = consoleUtil.getValueOf("\n account(Enber. no change)");
		if (account.equals("")) {
			account = transaction.getAccount().getId();
		}

		String name = consoleUtil.getValueOf("\n account name(Enter. no change)");
		if (account.equals("")) {
			account = transaction.getAccount().getName();
		}

		String memo = consoleUtil.getValueOf("\n memo(Enter. no change)");
		if (account.equals("")) {
			account = transaction.getMemo();
		}

		transaction.setTitle(title);
		transaction.setAccount(new IdName(account, name));
		transaction.setMemo(memo);

		transactionService.update(transaction);

	}

	public void remove(CashBook currentBook) {
		//
		if (currentBook == null) {
			narrator.sayln("select currentBook First!");
			return;
		}
		String id = consoleUtil.getValueOf("\n Id >");

		transactionService.remove(id);

	}

	public CashBook selectBook() {
		//
		String account = consoleUtil.getValueOf("\n Cash book Account");
		if (!cashbookService.exist(account)) {
			narrator.sayln("No cashbook in storage -->" + account);
			return null;
		}
		CashBook foundCashBook = cashbookService.retrieve(account);
		narrator.sayln("Found -->" + foundCashBook.toString());
		return foundCashBook;
	}
}
