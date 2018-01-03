package javastory.budgetsh.stage3.budget.entity.budget.tran;

import java.io.Serializable;

public interface TranItem extends Serializable{
	//
	public TranType getType();
	public double getAmount();
	public double getVat();
}
