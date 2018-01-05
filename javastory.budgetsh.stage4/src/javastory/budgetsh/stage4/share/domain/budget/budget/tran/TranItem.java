package javastory.budgetsh.stage4.share.domain.budget.budget.tran;

import java.io.Serializable;

public interface TranItem extends Serializable{
	//
	public TranType getType();
	public double getAmount();
	public double getVat();
}
