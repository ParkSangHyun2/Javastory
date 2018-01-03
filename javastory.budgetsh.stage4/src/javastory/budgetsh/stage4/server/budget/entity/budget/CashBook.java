package javastory.budgetsh.stage4.server.budget.entity.budget;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import javastory.budgetsh.stage4.server.budget.entity.travel.Travel;
import javastory.budgetsh.stage4.server.budget.share.DatePair;
import javastory.budgetsh.stage4.server.budget.share.Entity;
import javastory.budgetsh.stage4.server.budget.share.IdName;
import javastory.budgetsh.stage4.server.budget.share.Socialian;

public class CashBook extends Entity {
	//
	private IdName travel; 			// travelId = clubId.000(sequence)
	private IdName club; 
	private Socialian leader;
	private String bankAccount; // key
	private String currencyCode;
	private double monthlyDue;
	private double budgetGoal; 
	private DatePair term;
	private String memo;
	private long time;

	@SuppressWarnings("unused")
	transient private List<Transaction> transactions; 
	
	public CashBook(Travel travel, double monthlyDue, double budgetGoal, DatePair term) {
		// 
		super();
		this.travel = travel.getIdName();
		this.club = travel.getClub(); 
		this.leader = travel.getLeader(); 
		this.currencyCode = Currency.getInstance(Locale.getDefault()).getCurrencyCode(); 
		this.monthlyDue = monthlyDue; 
		this.budgetGoal = budgetGoal; 
		this.term = term; 
		this.time = System.currentTimeMillis();
	}
	
	public CashBook(Travel travel, double monthlyDue, double budgetGoal, DatePair term,String memo,String account) {
		// 
		super(account);
		this.bankAccount = account;
		this.travel = travel.getIdName();
		this.travel = new IdName(travel.getId(),travel.getTitle());
		this.club = travel.getClub(); 
		this.leader = travel.getLeader(); 
		this.currencyCode = Currency.getInstance(Locale.getDefault()).getCurrencyCode(); 
		this.monthlyDue = monthlyDue; 
		this.budgetGoal = budgetGoal; 
		this.term = term;
		this.memo = memo;
		this.time = System.currentTimeMillis(); 
	}

	public CashBook() {
		//
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("travel:").append(travel)
		.append(", leader:").append(leader)
		.append(", currencyCode:").append(currencyCode)
		.append(", monthlyDue:").append(monthlyDue)
		.append(", budgetGoal:").append(budgetGoal)
		.append(", term:").append(term)
		.append(", memo:").append(memo)
		.append(", time:").append(time);
		
		return builder.toString();
	}

	public static CashBook getSample() {
		// 
		Travel travel = Travel.getSample(); 
		double monthlyDue = 100000; 
		double budgetGoal = 5000000; 
		DatePair term = new DatePair(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE), travel.getTerm().getStartDate()); 
		
		CashBook sample = new CashBook(travel, monthlyDue, budgetGoal, term); 
		
		return  sample; 
	}

	public IdName getTravel() {
		return travel;
	}

	public void setTravel(IdName travel) {
		this.travel = travel;
	}

	public Socialian getLeader() {
		return leader;
	}

	public void setLeader(Socialian leader) {
		this.leader = leader;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public double getMonthlyDue() {
		return monthlyDue;
	}

	public void setMonthlyDue(double monthlyDue) {
		this.monthlyDue = monthlyDue;
	}

	public double getBudgetGoal() {
		return budgetGoal;
	}

	public void setBudgetGoal(double budgetGoal) {
		this.budgetGoal = budgetGoal;
	}

	public DatePair getTerm() {
		return term;
	}

	public void setTerm(DatePair term) {
		this.term = term;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public IdName getClub() {
		return club;
	}

	public void setClub(IdName club) {
		this.club = club;
	}
	
	public static void main(String[] args) {
		// 
		System.out.println(getSample());
	}

}
