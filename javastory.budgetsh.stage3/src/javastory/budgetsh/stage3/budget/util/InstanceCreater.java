package javastory.budgetsh.stage3.budget.util;

import java.lang.reflect.Type;

import com.google.gson.InstanceCreator;

import javastory.budgetsh.stage3.budget.entity.budget.tran.Expense;
import javastory.budgetsh.stage3.budget.entity.budget.tran.TranItem;

public class InstanceCreater implements InstanceCreator<TranItem>{

	@Override
	public TranItem createInstance(Type type) {
		//
		return new Expense();
	}

}
