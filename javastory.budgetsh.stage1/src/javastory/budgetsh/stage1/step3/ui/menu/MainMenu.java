package javastory.budgetsh.stage1.step3.ui.menu;

import java.util.Scanner;

import javastory.budgetsh.stage1.util.Narrator;
import javastory.budgetsh.stage1.util.TalkingAt;

public class MainMenu {
	//
	private AccountMenu accountMenu;
	private CashBookMenu cashbookMenu;
	
	private Scanner scanner;
	private Narrator narrator;
	
	public MainMenu() {
		//
		accountMenu = new AccountMenu();
		cashbookMenu = new CashBookMenu();
		
		scanner = new Scanner(System.in);
		narrator = new Narrator(this, TalkingAt.Left);
	}
	
	public void show() {
		//
		int inputNumber =0;
		
		while(true) {
			//
			displayMenu();
			inputNumber = selectNumber();
			
			switch(inputNumber) {
			//
			case 1:
				cashbookMenu.show();
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
