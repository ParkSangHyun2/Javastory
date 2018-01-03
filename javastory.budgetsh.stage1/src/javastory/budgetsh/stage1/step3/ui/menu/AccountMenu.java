package javastory.budgetsh.stage1.step3.ui.menu;

import java.util.Scanner;

import javastory.budgetsh.stage1.step1.entity.account.AccountYearlyDue;
import javastory.budgetsh.stage1.step3.ui.console.AccountConsole;
import javastory.budgetsh.stage1.step3.ui.console.AccountMonthlyConsole;
import javastory.budgetsh.stage1.util.Narrator;
import javastory.budgetsh.stage1.util.TalkingAt;

public class AccountMenu {
	//
	private AccountConsole accountConsole;
	private AccountYearlyDue currentYearlyDue;
	private AccountMonthlyConsole accountMonthlyConsole;
	
	private Scanner scanner;
	private Narrator narrator;
	
	public AccountMenu() {
		//
		scanner = new Scanner(System.in);
		narrator = new Narrator(this, TalkingAt.Left);
		
		accountConsole = new AccountConsole();
		accountMonthlyConsole = new AccountMonthlyConsole();
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
				currentYearlyDue = accountConsole.register();
				break;
			case 2:
				currentYearlyDue = accountConsole.find();
				break;
			case 3:
				accountConsole.modify();
				break;
			case 4:
				accountConsole.remove();
				break;
			case 5:
				accountMonthlyConsole.add(currentYearlyDue);
				break;
			case 6:
				accountMonthlyConsole.remove(currentYearlyDue);
				break;
			case 7:
				accountMonthlyConsole.modify(currentYearlyDue);
				break;
			case 8:
				accountMonthlyConsole.findAll(currentYearlyDue);
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
		if (currentYearlyDue ==null) {
			narrator.sayln(" Account Menu [ No selected ]");
		} else {
			narrator.sayln(" Account Menu [ " +currentYearlyDue.getMember().getName()+ " ]");
		}
		
		narrator.sayln("------------------------------");
		narrator.sayln(" 1. register");
		narrator.sayln(" 2. find");
		narrator.sayln(" 3. modify");
		narrator.sayln(" 4. remove");
		narrator.sayln("------------------------------");
		narrator.sayln(" 5. add monthly Due");
		narrator.sayln(" 6. remove monthly Due");
		narrator.sayln(" 7. modify monthly Due");
		narrator.sayln(" 8. findAll monthly Due");
		narrator.sayln("------------------------------");
		narrator.sayln(" 0. Previous Menu");
	}
	
	public int selectMenu() {
		//
		narrator.say("Select : ");
		int menuNumber = scanner.nextInt();
		
		if (menuNumber >= 0 && menuNumber <= 8) {
			scanner.nextLine();
			return menuNumber;
		} else {
			narrator.sayln(" It's invalid Number -->" + menuNumber);
		}
		return menuNumber;
	}
}
