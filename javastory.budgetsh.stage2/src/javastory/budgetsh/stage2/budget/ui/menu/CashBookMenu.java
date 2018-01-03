package javastory.budgetsh.stage2.budget.ui.menu;

import java.util.Scanner;

import javastory.budgetsh.stage2.budget.entity.budget.CashBook;
import javastory.budgetsh.stage2.budget.ui.console.CashBookConsole;
import javastory.budgetsh.stage2.budget.util.Narrator;
import javastory.budgetsh.stage2.budget.util.TalkingAt;
import javastory.budgetsh.stage2.club.entity.club.TravelClub;

public class CashBookMenu {
	//
	private TransactionMenu transactionMenu;
	private CashBookConsole cashbookConsole;
	
	private Scanner scanner;
	private Narrator narrator;
	
	private CashBook currentBook;
	
	public CashBookMenu() {
		//
		scanner = new Scanner(System.in);
		narrator = new Narrator(this, TalkingAt.Left);
		
		transactionMenu = new TransactionMenu();
		cashbookConsole = new CashBookConsole();
	}
	
	public void show(TravelClub travelClub) {
		//
		int inputNumber = 0;
		
		while(true) {
			//
			displayMenu();
			inputNumber = selectMenu(); 
			
			switch (inputNumber) {
			//
			case 1:
				currentBook = cashbookConsole.register(travelClub);
				break;
			case 2:
				currentBook = cashbookConsole.find();
				break;
			case 3:
				currentBook = cashbookConsole.modify();
				break;
			case 4:
				cashbookConsole.remove();
				break;
			case 5:
				transactionMenu.show(currentBook);
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
			narrator.sayln(" CashBook Menu [ " + currentBook.getTravel().getName()+" ]");
		}else {
			narrator.sayln(" CashBook Menu [	No selected ]");
		}
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
