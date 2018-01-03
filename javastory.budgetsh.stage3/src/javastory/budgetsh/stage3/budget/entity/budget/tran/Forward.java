package javastory.budgetsh.stage3.budget.entity.budget.tran;

public class Forward implements TranItem{
	//
	private double amount;
	private double vat;
	
	public Forward() {
		//
	}
	
	public Forward(double amount, double vat) {
		this.amount = amount;
		this.vat = vat;
	}
	
	public String toString() {
		//
		StringBuilder builder = new StringBuilder();
		
		builder.append("Amount: ").append(amount);
		builder.append(",Vat: ").append(vat);
		builder.append(",type: ").append(getType().toString());
		
		return builder.toString();
	}
	
	public static Forward getSample() {
		//
		Forward forward = new Forward(22000,1000);
		return forward;
	}
	
	public TranType getType() {
		//
		return TranType.Forward;
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
