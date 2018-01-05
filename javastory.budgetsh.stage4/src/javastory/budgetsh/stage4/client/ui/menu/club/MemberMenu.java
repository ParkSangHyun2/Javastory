/*
 * COPYRIGHT (c) NEXTREE Consulting 2016
 * This software is the proprietary of NEXTREE Consulting CO.  
 * 
 * @author <a href="mailto:eykim@nextree.co.kr">Kim, Eunyoung</a>
 * @since 2016. 7. 12.
 */
package javastory.budgetsh.stage4.client.ui.menu.club;

import java.util.Scanner;

import javastory.budgetsh.stage4.client.ui.console.club.MemberConsole;
import javastory.budgetsh.stage4.share.util.Narrator;
import javastory.budgetsh.stage4.share.util.TalkingAt;

public class MemberMenu {
	//
	private MemberConsole memberConsole; 
	
	private Scanner scanner;
	private Narrator narrator; 

	public MemberMenu() {
		//
		this.memberConsole = new MemberConsole();

		this.scanner = new Scanner(System.in);
		this.narrator = new Narrator(this, TalkingAt.Left); 
	}

	public void show() {
		//
		int inputNumber = 0;

		while (true) {
			displayMenu();
			inputNumber = selectMenu();

			switch (inputNumber) {
			//
			case 1:
				memberConsole.register(); 
				break;
			case 2:
				memberConsole.find();
				break;
			case 3:
				memberConsole.modify(); 
				break;
			case 4:
				memberConsole.remove(); 
				break;
			case 0:
				return;

			default:
				narrator.sayln("Choose again!");
			}
		}
	}

	private void displayMenu() {
		//
		narrator.sayln("");
		narrator.sayln("..............................");
		narrator.sayln(" Member menu ");
		narrator.sayln("..............................");
		narrator.sayln(" 1. Register");
		narrator.sayln(" 2. Find");
		narrator.sayln(" 3. Modify");
		narrator.sayln(" 4. Remove");
		narrator.sayln("..............................");
		narrator.sayln(" 0. Previous");
		narrator.sayln("..............................");
	}

	private int selectMenu() {
		//
		System.out.print("Select: ");
		int menuNumber = scanner.nextInt();

		if (menuNumber >= 0 && menuNumber <= 4) {
			scanner.nextLine();
			return menuNumber;
		} else {
			narrator.sayln("It's a invalid number --> " + menuNumber);
			return -1;
		}
	}
}