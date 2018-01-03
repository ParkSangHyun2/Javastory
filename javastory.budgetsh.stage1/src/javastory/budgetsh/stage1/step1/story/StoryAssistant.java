package javastory.budgetsh.stage1.step1.story;

import javastory.budgetsh.stage1.step1.entity.budget.CashBook;
import javastory.budgetsh.stage1.step1.entity.travel.Travel;
import javastory.budgetsh.stage1.step1.share.DatePair;
import javastory.budgetsh.stage1.step1.share.IdName;
import javastory.budgetsh.stage1.step1.share.Socialian;
import javastory.budgetsh.stage1.util.Narrator;
import javastory.budgetsh.stage1.util.TalkingAt;

public class StoryAssistant {
	//
	private Narrator narrator;
	public StoryAssistant() {
		//
		narrator = new Narrator(this, TalkingAt.Left);
	}
	
	private void showCashBookDemo() {
		//
		System.out.println(new CashBook(Travel.getSample(),30000,3000000,DatePair.getSample()));
	}
	
	private void showTravelDemo() {
		//
		System.out.println("TravelDemo");
		System.out.print(new Travel(IdName.getSample(),"JavaTravel",Socialian.getSample(),DatePair.getSample()));
	}
	
	public static void main(String[] args) {
		StoryAssistant storyAssistant = new StoryAssistant();
		storyAssistant.showCashBookDemo();
		storyAssistant.showTravelDemo();
	}
}
