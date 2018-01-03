package javastory.budgetsh.stage2.budget.entity.budget.tran;

public class Expense implements TranItem {
	//
	private double amount;
	private double vat;
	
	public Expense() {
		//
	}
	
	public Expense(double amount, double vat) {
		this.amount = amount;
		this.vat = vat;
	}
	
	public String toString() {
		//
		StringBuilder builder = new StringBuilder();
		
		builder.append("[Amount : ").append(amount);
		builder.append(", Vat : ").append(vat);
		builder.append(", type : ").append(getType().toString());
		
		return builder.toString();
	}
	
	public static Expense getSample() {
		//
		Expense expense = new Expense(30000,1500);
		return expense;
	}
	
	public TranType getType() {
		//
		return TranType.Expense;
	}
	
	public double getAmount() {
		//
		return amount;
	}
	
	public void setAmount(double amount) {
		//
		this.amount = amount;
	}
	
	public double getVat() {
		//
		return vat;
	}
	
	public void setVat(double vat) {
		//
		this.vat = vat;
	}
	
	public static void main(String[] args) {
		//
		System.out.println(getSample());
	}
}
