package javastory.budgetsh.stage4.share.domain.budget.budget.tran;

public class Revenue implements TranItem{
	//
	private double amount;
	private double vat;
	
	public Revenue() {
		//
		
	}
	
	public Revenue(double amount, double vat) {
		//
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
	
	public static Revenue getSample() {
		//
		Revenue revenue = new Revenue(13270,2500);
		return revenue;
	}
	
	public TranType getType() {
		//
		return TranType.Revenue;
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
