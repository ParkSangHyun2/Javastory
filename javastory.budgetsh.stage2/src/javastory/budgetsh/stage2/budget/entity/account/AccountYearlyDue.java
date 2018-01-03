package javastory.budgetsh.stage2.budget.entity.account;

import java.util.ArrayList;
import java.util.List;

import javastory.budgetsh.stage2.budget.share.IdName;

public class AccountYearlyDue {
	//
	private IdName member;
	private int year;
	private List<MonthlyDue> monthlyDues;
	
	public AccountYearlyDue() {
		//
		monthlyDues = new ArrayList<>();
	}
	
	public AccountYearlyDue(String name) {
		//
		this();
		member.setName(name);
	}
	
	public AccountYearlyDue(IdName member, int year) {
		//
		this();
		this.member = member;
		this.year = year;
	}
	
	public String toString() {
		//
		StringBuilder builder = new StringBuilder();
		builder.append("[ Member Id / Name : ").append(member.getId()).append(" / ").append(member.getName());
		builder.append(", year : ").append(year);
		
		return builder.toString();
	}
	
	public static AccountYearlyDue getSample() {
		//
		AccountYearlyDue yearlyDue= new AccountYearlyDue(new IdName("id001","park"),2017);
		
		return yearlyDue;
	}
	
	public void addMonthlyDue(int month, double amount,String id, String name) {
		// modify
		monthlyDues.add(new MonthlyDue(month,amount, new IdName(id,name)));
	}
	
	public IdName getMember() {
		//
		return member;
	}
	
	public void setMember(IdName member) {
		//
		this.member = member;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public List<MonthlyDue> getMonthlyDues(){
		return monthlyDues;
	}
	
	public void setMonthlyDues(List<MonthlyDue> monthlyDues) {
		//
		this.monthlyDues = monthlyDues;
	}
	
	public static void main(String[] args) {
		//
		System.out.println(getSample());
	}
}
