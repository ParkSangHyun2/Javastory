/*
 * COPYRIGHT (c) NEXTREE Consulting 2014
 * This software is the proprietary of NEXTREE Consulting CO.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2014. 6. 10.
 */

package javastory.budgetsh.stage3;

import javastory.budgetsh.stage3.club.ui.menu.MainMenu;

public class StoryAssistant {
	//
	private void startStory() {
		// 
		MainMenu mainMenu = new MainMenu();
		mainMenu.show();
	}
	
	public static void main(String[] args) {
		//
		StoryAssistant assistant = new StoryAssistant(); 
		assistant.startStory(); 
	}
}