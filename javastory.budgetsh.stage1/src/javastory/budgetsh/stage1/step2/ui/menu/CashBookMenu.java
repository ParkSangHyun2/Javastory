package javastory.budgetsh.stage1.step2.ui.menu;

import java.util.Scanner;

import javastory.budgetsh.stage1.step2.ui.window.CashBookWindow;
import javastory.budgetsh.stage1.util.Narrator;
import javastory.budgetsh.stage1.util.TalkingAt;

public class CashBookMenu {
	//
	private TransactionMenu transactionMenu;
	private CashBookWindow cashbookWindow;
	
	private Scanner scanner;
	private Narrator narrator;
	
	public CashBookMenu() {
		//
		scanner = new Scanner(System.in);
		narrator = new Narrator(this, TalkingAt.Left);
		
		transactionMenu = new TransactionMenu();
		cashbookWindow = new CashBookWindow();
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
				cashbookWindow.register();
				break;
			case 2:
				cashbookWindow.find();
				break;
			case 3:
				cashbookWindow.modify();
				break;
			case 4:
				cashbookWindow.remove();
				break;
			case 5:
				transactionMenu.show();
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
		narrator.sayln(" CashBook Menu");
		narrator.sayln("------------------------------");
		narrator.sayln(" 1. register");
		narrator.sayln(" 2. find");
		narrator.sayln(" 3. modify");
		narrator.sayln(" 4. remove");
		narrator.sayln("------------------------------");
		narrator.sayln(" 5. Transaction Menu");
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
