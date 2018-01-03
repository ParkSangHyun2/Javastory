package javastory.budgetsh.stage2.budget;

import javastory.budgetsh.stage2.budget.ui.menu.BudgetMainMenu;
import javastory.budgetsh.stage2.club.entity.club.TravelClub;

public class StoryAssistant {
	//
	TravelClub travelClub = new TravelClub();
	
	public static void main(String[] args) {
		StoryAssistant storyAssistant = new StoryAssistant();
		storyAssistant.start();
	}
	
	private void start() {
		BudgetMainMenu menu = new BudgetMainMenu();
		menu.show(travelClub.getSample(true));
	}
}
