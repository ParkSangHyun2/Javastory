package javastory.budgetsh.stage1.step2.ui.window;

import javastory.budgetsh.stage1.util.ConsoleUtil;
import javastory.budgetsh.stage1.util.Narrator;
import javastory.budgetsh.stage1.util.TalkingAt;

public class AccountWindow {
	//
	private ConsoleUtil consoleUtil;
	private Narrator narrator;

	public AccountWindow() {
		//
		narrator = new Narrator(this, TalkingAt.Left);
		consoleUtil = new ConsoleUtil(narrator);
	}

	public void register() {
		//
		consoleUtil.getValueOf("It's register menu");
	}

	public void find() {
		//
		consoleUtil.getValueOf("It's find menu");
	}

	public void modify() {
		//
		consoleUtil.getValueOf("It's modify menu");
	}

	public void remove() {
		//
		consoleUtil.getValueOf("It's remove menu");
	}
}
