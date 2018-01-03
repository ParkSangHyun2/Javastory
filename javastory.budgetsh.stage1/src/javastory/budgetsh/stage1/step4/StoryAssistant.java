package javastory.budgetsh.stage1.step4;

import javastory.budgetsh.stage1.step4.ui.menu.MainMenu;

public class StoryAssistant {
	public static void main(String[] args) {
		StoryAssistant storyAssistant = new StoryAssistant();
		storyAssistant.start();
	}
	
	private void start() {
		MainMenu menu = new MainMenu();
		menu.show();
	}
}
