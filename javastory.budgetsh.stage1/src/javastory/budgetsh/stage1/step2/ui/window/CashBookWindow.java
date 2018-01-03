package javastory.budgetsh.stage1.step2.ui.window;

import javastory.budgetsh.stage1.util.ConsoleUtil;
import javastory.budgetsh.stage1.util.Narrator;
import javastory.budgetsh.stage1.util.TalkingAt;

public class CashBookWindow {
	//
	private ConsoleUtil consoleUtil;
	private Narrator narrator;
	
	public CashBookWindow() {
		//
		narrator = new Narrator(this, TalkingAt.Left);
		consoleUtil = new ConsoleUtil(narrator);
	}
	
	public void register() {
		//
		consoleUtil.getValueOf("It's register.");
	}
	
	public void find() {
		//
		narrator.sayln("It's find menu");
	}
	
	public void modify() {
		//
		narrator.sayln("It's modify menu");
	}
	
	public void remove() {
		//
		narrator.sayln("It's remove menu");
	}
	
}
