/*
 * COPYRIGHT (c) NEXTREE Consulting 2016
 * This software is the proprietary of NEXTREE Consulting CO.  
 * 
 * @author <a href="mailto:eykim@nextree.co.kr">Kim, Eunyoung</a>
 * @since 2016. 7. 12.
 */
package javastory.budgetsh.stage2.club.ui.menu;

import java.util.Scanner;

import javastory.budgetsh.stage2.club.ui.console.BoardConsole;
import javastory.budgetsh.stage2.club.util.Narrator;
import javastory.budgetsh.stage2.club.util.TalkingAt;

public class BoardMenu {
	//
	private BoardConsole boardConsole;
	private PostingMenu postingMenu;

	private Scanner scanner;
	private Narrator narrator;

	public BoardMenu() {
		//
		this.boardConsole = new BoardConsole();
		this.postingMenu = new PostingMenu();

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
				boardConsole.register();
				break;
			case 2:
				boardConsole.findByName();
				break;
			case 3:
				boardConsole.modify();
				break;
			case 4:
				boardConsole.remove();
				break;
			case 5:
				postingMenu.show();
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
		narrator.sayln(" Board menu ");
		narrator.sayln("..............................");
		narrator.sayln(" 1. Register a board");
		narrator.sayln(" 2. Find boards by name");
		narrator.sayln(" 3. Modify a board");
		narrator.sayln(" 4. Remove a board");
		narrator.sayln("..............................");
		narrator.sayln(" 5. Posting Menu");
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