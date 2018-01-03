package javastory.budgetsh.stage4.client.ui.budget.menu;

import java.util.Scanner;

import javastory.budgetsh.stage4.client.dto.budget.CashBook;
import javastory.budgetsh.stage4.client.ui.budget.console.TransactionConsole;
import javastory.budgetsh.stage4.client.util.Narrator;
import javastory.budgetsh.stage4.client.util.TalkingAt;

public class TransactionMenu {
	//
	private TransactionConsole transactionConsole;
	private CashBook currentBook;
	
	private Scanner scanner;
	private Narrator narrator;
	
	public TransactionMenu() {
		//
		transactionConsole = new TransactionConsole();
		
		scanner = new Scanner(System.in);
		narrator = new Narrator(this, TalkingAt.Left);
	}
	
	public void show(CashBook currentBook) {
		//
		if(currentBook!=null) {
			this.currentBook = currentBook;
		}
		
		int inputNumber = 0;
		
		while(true) {
			//
			displayMenu();
			inputNumber = selectMenu(); 
			
			switch (inputNumber) {
			//
			case 1:
				transactionConsole.register(this.currentBook);
				break;
			case 2:
				transactionConsole.find(this.currentBook);
				break;
			case 3:
				transactionConsole.findAll(this.currentBook);
				break;
			case 4:
				transactionConsole.modify(this.currentBook);
				break;
			case 5:
				transactionConsole.remove(this.currentBook);
				break;
			case 6:
				this.currentBook = transactionConsole.selectBook();
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
		if (currentBook!=null) {
			narrator.sayln(" Transaction Menu [ " + currentBook.getTravel().getName()+" ]");
		}else {
			narrator.sayln(" Transaction Menu [ No selected ]");
		}
		narrator.sayln("------------------------------");
		narrator.sayln(" 1. register");
		narrator.sayln(" 2. find");
		narrator.sayln(" 3. findAll");
		narrator.sayln(" 4. modify");
		narrator.sayln(" 5. remove");
		narrator.sayln(" 6. select CashBook");
		narrator.sayln("------------------------------");
		narrator.sayln(" 0. Previous Menu");
	}
	
	public int selectMenu() {
		//
		narrator.say("Select : ");
		int menuNumber = scanner.nextInt();
		
		if (menuNumber >= 0 && menuNumber <= 6) {
			scanner.nextLine();
			return menuNumber;
		} else {
			narrator.sayln(" It's invalid Number -->" + menuNumber);
		}
		return menuNumber;
	}
}
