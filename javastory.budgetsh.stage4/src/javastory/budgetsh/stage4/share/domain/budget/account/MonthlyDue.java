package javastory.budgetsh.stage4.share.domain.budget.account;

import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;

import javastory.budgetsh.stage4.share.domain.share.IdName;

public class MonthlyDue {
	//
	private int month;
	private String currencyCode;
	private DuesType type;
	private double amount;
	private long time;
	private IdName travel;
	
	public MonthlyDue(int month, double amount, IdName travel) {
		//
		this.type = DuesType.Regular;
		this.currencyCode = Currency.getInstance(Locale.getDefault()).getCurrencyCode(); 
		this.month = month;
		this.amount = amount;
		this.travel = travel;
		this.time = System.currentTimeMillis();
	}
	
	public String toString() {
		//
		StringBuilder builder = new StringBuilder();
		builder.append("[Month : ").append(month);
		builder.append(", currencyCode : ").append(currencyCode);
		builder.append(", type : ").append(getType().toString());
		builder.append(", Amount : ").append(amount);
		builder.append(", Time : ").append(time);
		builder.append(", travel Name : ").append(travel.getName());
		
		return builder.toString();
	}
	
	public IdName getTravel() {
		return travel;
	}

	public void setTravel(IdName travel) {
		this.travel = travel;
	}

	public static MonthlyDue getSample() {
		//
		MonthlyDue monthlyDue = new MonthlyDue(10,3000000,new IdName().getSample());
		monthlyDue.setCurrencyCode("TP003");
		monthlyDue.setType(DuesType.Regular);
		monthlyDue.setTime(Calendar.getInstance().getTimeInMillis());
		
		return monthlyDue;
	}
	
	public int getMonth() {
		//
		return month;
	}
	
	public void setMonth(int month) {
		//
		this.month = month;
	}
	
	public double getAmount() {
		//
		return amount;
	}
	
	public void setAmount(double amount) {
		//
		this.amount  = amount;
	}
	
	public String getCurrencyCode() {
		//
		return currencyCode;
	}
	
	public void setCurrencyCode(String currencyCode) {
		//
		this.currencyCode = currencyCode;
	}
	
	public DuesType getType() {
		//
		return type;
	}
	
	public void setType(DuesType type) {
		//
		this.type = type;
	}
	
	public long getTime() {
		//
		return time;
	}
	
	public void setTime(long time) {
		//
		this.time = time;
	}
	
	public static void main(String[] args) {
		//
		System.out.println(getSample());
	}
}
