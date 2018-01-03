package javastory.budgetsh.stage4.server.budget.entity.budget.tran;

import java.io.Serializable;

public interface TranItem extends Serializable{
	//
	public TranType getType();
	public double getAmount();
	public double getVat();
}
