/*
S * COPYRIGHT (c) NEXTREE Consulting 2016
 * This software is the proprietary of NEXTREE Consulting CO.  
 * 
 * @author <a href="mailto:eykim@nextree.co.kr">Kim, Eunyoung</a>
 * @since 2016. 7. 12.
 */
package javastory.budgetsh.stage4.client.ui.menu.club;

import java.util.Scanner;

import javastory.budgetsh.stage4.client.ui.console.club.ClubMembershipConsole;
import javastory.budgetsh.stage4.share.util.Narrator;
import javastory.budgetsh.stage4.share.util.TalkingAt;

public class ClubMembershipMenu {
	//
	private ClubMembershipConsole clubMembershipConsole;

	private Scanner scanner;
	private Narrator narrator; 

	public ClubMembershipMenu() {
		//
		this.clubMembershipConsole = new ClubMembershipConsole();  
		this.scanner = new Scanner(System.in); 
		this.narrator = new Narrator(this, TalkingAt.Left); 
	}

	public void show() {
		//
		int inputNumber = 0;

		while(true){
			displayMenu();
			inputNumber = selectMenu();

			switch (inputNumber) {
			//
			case 1:
				clubMembershipConsole.findClub(); 
				break;
			case 2:
				clubMembershipConsole.add();
				break;
			case 3:
				clubMembershipConsole.find();
				break;
			case 4:
				clubMembershipConsole.modify();
				break;
			case 5:
				clubMembershipConsole.remove();
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
		if (clubMembershipConsole.hasCurrentClub()) {
			narrator.sayln(" Membership menu for[" + clubMembershipConsole.requestCurrentClubName() + "]");
		} else {
			narrator.sayln(" Membership menu ");
		}
		narrator.sayln("..............................");
		narrator.sayln(" 1. Find a club");
		narrator.sayln(" 2. Add a membership");
		narrator.sayln(" 3. Find a membership");
		narrator.sayln(" 4. Modify a membership");
		narrator.sayln(" 5. Remove a membership");
		narrator.sayln("..............................");
		narrator.sayln(" 0. Previous");
		narrator.sayln("..............................");
	}

	private int selectMenu() {
		//
		System.out.print("Select: ");
		int menuNumber = scanner.nextInt();

		if (menuNumber >= 0 && menuNumber <= 5) {
			scanner.nextLine();
			return menuNumber;
		} else {
			narrator.sayln("It's a invalid number --> " + menuNumber);
			return -1;
		}
	}
}