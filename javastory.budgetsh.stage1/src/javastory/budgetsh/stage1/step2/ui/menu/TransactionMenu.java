package javastory.budgetsh.stage1.step2.ui.menu;

import java.util.Scanner;

import javastory.budgetsh.stage1.step2.ui.window.TransactionWindow;
import javastory.budgetsh.stage1.util.Narrator;
import javastory.budgetsh.stage1.util.TalkingAt;

public class TransactionMenu {
	//
	private TransactionWindow transactionWindow;
	
	private Scanner scanner;
	private Narrator narrator;
	
	public TransactionMenu() {
		//
		transactionWindow = new TransactionWindow();
		
		scanner = new Scanner(System.in);
		narrator = new Narrator(this, TalkingAt.Left);
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
				transactionWindow.register();
				break;
			case 2:
				transactionWindow.find();
				break;
			case 3:
				transactionWindow.findAll();
				break;
			case 4:
				transactionWindow.modify();
				break;
			case 5:
				transactionWindow.remove();
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
		narrator.sayln(" Transaction Menu");
		narrator.sayln("------------------------------");
		narrator.sayln(" 1. register");
		narrator.sayln(" 2. find");
		narrator.sayln(" 3. findAll");
		narrator.sayln(" 4. modify");
		narrator.sayln(" 5. remove");
		narrator.sayln("------------------------------");
		narrator.sayln(" 0. Previous Menu");
	}
	
	public int selectMenu() {
		//
		narrator.say("Select : ");
		int menuNumber = scanner.nextInt();
		
		if (menuNumber >= 0 && menuNumber <= 5) {
			scanner.nextLine();
			return menuNumber;
		} else {
			narrator.sayln(" It's invalid Number -->" + menuNumber);
		}
		return menuNumber;
	}
}
