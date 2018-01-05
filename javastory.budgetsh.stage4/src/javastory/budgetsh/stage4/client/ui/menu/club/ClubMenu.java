/*
 * COPYRIGHT (c) NEXTREE Consulting 2016
 * This software is the proprietary of NEXTREE Consulting CO.  
 * 
 * @author <a href="mailto:eykim@nextree.co.kr">Kim, Eunyoung</a>
 * @since 2016. 7. 12.
 */
package javastory.budgetsh.stage4.client.ui.menu.club;

import java.util.Scanner;

import javastory.budgetsh.stage4.client.ui.console.club.ClubConsole;
import javastory.budgetsh.stage4.client.ui.menu.budget.BudgetMainMenu;
import javastory.budgetsh.stage4.share.domain.club.dto.TravelClubDto;
import javastory.budgetsh.stage4.share.util.Narrator;
import javastory.budgetsh.stage4.share.util.TalkingAt;

public class ClubMenu {
	//
	private ClubMembershipMenu clubMembershipMenu;
	private ClubConsole clubConsole;
	private BudgetMainMenu budgetMenu;
	private TravelClubDto currentClub;

	private Scanner scanner;
	private Narrator narrator;

	public ClubMenu() {
		//
		this.clubMembershipMenu = new ClubMembershipMenu();
		this.clubConsole = new ClubConsole();
		this.budgetMenu = new BudgetMainMenu();

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
				clubConsole.register();
				break;
			case 2:
				currentClub = clubConsole.find();
				break;
			case 3:
				clubConsole.modify();
				break;
			case 4:
				clubConsole.remove();
				break;
			case 5:
				clubMembershipMenu.show();
				break;
			case 6:
				if (currentClub == null) {
					narrator.sayln("select TravelClub.");
					continue;
				}
				budgetMenu.show(currentClub.toTravelClub());
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
		if (currentClub != null) {
			narrator.sayln(" Club menu [" + currentClub.getName() + "]");
		} else {
			narrator.sayln(" Club menu [No Selected]");
		}
		narrator.sayln("..............................");
		narrator.sayln(" 1. Register");
		narrator.sayln(" 2. Find");
		narrator.sayln(" 3. Modify");
		narrator.sayln(" 4. Remove");
		narrator.sayln("..............................");
		narrator.sayln(" 5. Membership menu");
		narrator.sayln("..............................");
		narrator.sayln(" 6. Budget Menu");
		narrator.sayln("..............................");
		narrator.sayln(" 0. Previous");
		narrator.sayln("..............................");
	}

	private int selectMenu() {
		//
		System.out.print("Select: ");
		int menuNumber = scanner.nextInt();

		if (menuNumber >= 0 && menuNumber <= 6) {
			scanner.nextLine();
			return menuNumber;
		} else {
			narrator.sayln("It's a invalid number --> " + menuNumber);
			return -1;
		}
	}

}