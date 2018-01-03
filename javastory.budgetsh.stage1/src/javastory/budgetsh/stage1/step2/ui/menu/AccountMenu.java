package javastory.budgetsh.stage1.step2.ui.menu;

import java.util.Scanner;

import javastory.budgetsh.stage1.step2.ui.window.AccountWindow;
import javastory.budgetsh.stage1.util.Narrator;
import javastory.budgetsh.stage1.util.TalkingAt;

public class AccountMenu {
	//
	private AccountWindow accountWindow;
	
	private Scanner scanner;
	private Narrator narrator;
	
	public AccountMenu() {
		//
		scanner = new Scanner(System.in);
		narrator = new Narrator(this, TalkingAt.Left);
		
		accountWindow = new AccountWindow();
	}
	
	public void show() {
		//
		int inputNumber = 0;
		
		while(true) {
			//
			displayMenu();
			inputNumber = selectMenu(); 
			
			switch (inputNumber) {
			//
			case 1:
				accountWindow.register();
				break;
			case 2:
				accountWindow.find();
				break;
			case 3:
				accountWindow.modify();
				break;
			case 4:
				accountWindow.remove();
				break;
			case 0:
				return;
				
			default:
				narrator.sayln("choose again!");
				continue;
			}
		}
	}
	
	public void displayMenu() {
		//
		narrator.sayln("------------------------------");
		narrator.sayln(" Account Menu");
		narrator.sayln("------------------------------");
		narrator.sayln(" 1. register");
		narrator.sayln(" 2. find");
		narrator.sayln(" 3. modify");
		narrator.sayln(" 4. remove");
		narrator.sayln("------------------------------");
		narrator.sayln(" 0. Previous Menu");
	}
	
	public int selectMenu() {
		//
		narrator.say("Select : ");
		int menuNumber = scanner.nextInt();
		
		if (menuNumber >= 0 && menuNumber <= 4) {
			scanner.nextLine();
			return menuNumber;
		} else {
			narrator.sayln(" It's invalid Number -->" + menuNumber);
		}
		return menuNumber;
	}
}
