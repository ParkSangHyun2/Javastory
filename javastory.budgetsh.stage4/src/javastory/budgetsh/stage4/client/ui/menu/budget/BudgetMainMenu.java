package javastory.budgetsh.stage4.client.ui.menu.budget;

import java.util.Scanner;

import javastory.budgetsh.stage4.share.domain.club.club.TravelClub;
import javastory.budgetsh.stage4.share.util.Narrator;
import javastory.budgetsh.stage4.share.util.TalkingAt;



public class BudgetMainMenu {
	//
	private AccountMenu accountMenu;
	private CashBookMenu cashbookMenu;
	private TravelClub travelClub;
	
	private Scanner scanner;
	private Narrator narrator;
	
	public BudgetMainMenu() {
		//
		accountMenu = new AccountMenu();
		cashbookMenu = new CashBookMenu();
		
		scanner = new Scanner(System.in);
		narrator = new Narrator(this, TalkingAt.Left);
	}
	
	public void show(TravelClub travelClub) {
		//
		int inputNumber =0;
		
		while(true) {
			//
			displayMenu();
			inputNumber = selectNumber();
			
			switch(inputNumber) {
			//
			case 1:
				cashbookMenu.show(travelClub);
				break;
			case 2:
				accountMenu.show();
				break;
			case 0:
				exit();
				break;
				
			default:
				narrator.sayln("Choose again!");
			}
		}
	}
	
	public void displayMenu() {
		//
		narrator.sayln("------------------------------");
		narrator.sayln(" Main Menu");
		narrator.sayln("------------------------------");
		narrator.sayln(" 1. CashBook menu");
		narrator.sayln(" 2. Account Menu");
		narrator.sayln("------------------------------");
		narrator.sayln(" 0. exit");
	}
	
	public int selectNumber() {
		//
		int inputNumber = 0;
		inputNumber = scanner.nextInt();
		
		if (inputNumber >= 0 && inputNumber <= 2) {
			scanner.nextLine();
			return inputNumber;
		} else {
			narrator.sayln("It's InvalidNumber -->" + inputNumber);
			return -1;
		}
		
	}
	
	public void exit() {
		//
		narrator.sayln("Exit...");
		System.exit(0);
	}
}
