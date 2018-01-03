/*
 * COPYRIGHT (c) NEXTREE Consulting 2016
 * This software is the proprietary of NEXTREE Consulting CO.  
 * 
 * @author <a href="mailto:eykim@nextree.co.kr">Kim, Eunyoung</a>
 * @since 2016. 7. 12.
 */
package javastory.budgetsh.stage4.client.ui.club.console;

import java.util.List;

import javastory.budgetsh.stage4.server.club.logic.ClubServiceLogicLycler;
import javastory.budgetsh.stage4.server.club.service.BoardService;
import javastory.budgetsh.stage4.server.club.service.PostingService;
import javastory.budgetsh.stage4.server.club.service.ClubServiceLycler;
import javastory.budgetsh.stage4.server.club.service.dto.BoardDto;
import javastory.budgetsh.stage4.server.club.service.dto.PostingDto;
import javastory.budgetsh.stage4.server.club.util.ConsoleUtil;
import javastory.budgetsh.stage4.server.club.util.Narrator;
import javastory.budgetsh.stage4.server.club.util.NoSuchBoardException;
import javastory.budgetsh.stage4.server.club.util.NoSuchClubException;
import javastory.budgetsh.stage4.server.club.util.NoSuchPostingException;
import javastory.budgetsh.stage4.server.club.util.TalkingAt;

public class PostingConsole {
	//
	private BoardDto currentBoard;

	private BoardService boardService;
	private PostingService postingService;

	private ConsoleUtil consoleUtil;
	private Narrator narrator;

	public PostingConsole() {
		//
		ClubServiceLycler serviceFactory = ClubServiceLogicLycler.shareInstance();
		this.boardService = serviceFactory.createBoardService();
		this.postingService = serviceFactory.createPostingService();

		this.narrator = new Narrator(this, TalkingAt.Left);
		this.consoleUtil = new ConsoleUtil(narrator);
	}

	public boolean hasCurrentBoard() {
		//
		if (currentBoard != null) {
			return true;
		}

		return false;
	}

	public String requestCurrentBoardName() {
		//
		String clubName = null;

		if (hasCurrentBoard()) {
			clubName = currentBoard.getName();
		}

		return clubName;
	}

	public void findBoard() {
		//
		BoardDto boardFound = null;
		while (true) {
			String clubName = consoleUtil.getValueOf("\n club name to find a board(0.Posting menu) ");
			if (clubName.equals("0")) {
				break;
			}
			try {
				boardFound = boardService.findByClubName(clubName);
				narrator.sayln("\t > Found board: " + boardFound);
				break;
			} catch (NoSuchClubException e) {
				narrator.sayln(e.getMessage());
			}
			boardFound = null;
		}
		this.currentBoard = boardFound;
	}

	public void register() {
		//
		if (!hasCurrentBoard()) {
			//
			narrator.sayln("No target board yet. Find target board first.");
			return;
		}

		while (true) {
			String title = consoleUtil.getValueOf("\n posting title(0.Posting menu)");
			if (title.equals("0")) {
				return;
			}
			String writerEmail = consoleUtil.getValueOf("\n posting writerEmail.");
			String contents = consoleUtil.getValueOf("\n posting contents.");

			try {
				PostingDto postingDto = new PostingDto(title, writerEmail, contents);
				postingService.register(currentBoard.getId(), postingDto);
				narrator.sayln("Register a posting -->" + postingDto);

			} catch (NoSuchBoardException e) {
				narrator.sayln(e.getMessage());
			}
		}

	}

	public void findByBoardId() {
		//
		if (!hasCurrentBoard()) {
			//
			narrator.sayln("No target club yet. Find target club first.");
			return;
		}

		try {
			List<PostingDto> postings = postingService.findByBoardId(currentBoard.getId());
			int index = 0;
			for (PostingDto postingDto : postings) {
				narrator.sayln(String.format("[%d] ", index) + postingDto);
				index++;
			}
		} catch (NoSuchBoardException e) {
			narrator.sayln(e.getMessage());
		}
	}

	public void find() {
		//
		if (!hasCurrentBoard()) {
			//
			narrator.sayln("No target club yet. Find target club first.");
			return;
		}

		PostingDto postingDto = null;
		while (true) {
			String postingId = consoleUtil.getValueOf("\n posting id to find (0.Posting menu) ");
			if (postingId.equals("0")) {
				break;
			}

			try {
				postingDto = postingService.find(postingId);
				narrator.sayln("\t > Found posting : " + postingDto);
			} catch (NoSuchPostingException e) {
				narrator.sayln(e.getMessage());
			}
		}
	}

	public PostingDto findOne() {
		//
		if (!hasCurrentBoard()) {
			//
			narrator.sayln("No target club yet. Find target club first.");
			return null;
		}

		PostingDto postingDto = null;
		while (true) {
			String postingId = consoleUtil.getValueOf("\n posting id to find (0.Posting menu) ");
			if (postingId.equals("0")) {
				break;
			}

			try {
				postingDto = postingService.find(postingId);
				narrator.sayln("\t > Found posting : " + postingDto);
				break;
			} catch (NoSuchPostingException e) {
				narrator.sayln(e.getMessage());
			}
			postingDto = null;
		}
		return postingDto;
	}

	public void modify() {
		//
		PostingDto targetPosting = findOne();
		if (targetPosting == null) {
			return;
		}

		String newTitle = consoleUtil.getValueOf("\n new posting title(0.Posting menu, Enter. no change)");
		if (newTitle.equals("0")) {
			return;
		}
		if (!newTitle.isEmpty()) {
			targetPosting.setTitle(newTitle);
		}

		String contents = consoleUtil.getValueOf("\n new posting contents(Enter. no change))");
		if (!contents.isEmpty()) {
			targetPosting.setContents(contents);
		}
		
		try {
			postingService.modify(targetPosting);
			narrator.sayln("\n Modified Posting : " + targetPosting);
		} catch (NoSuchPostingException e) {
			narrator.sayln(e.getMessage());
		}

	}

	public void remove() {
		//
		PostingDto targetPosting = findOne();
		if (targetPosting == null) {
			return;
		}

		String confirmStr = consoleUtil.getValueOf("Remove this posting in the board? (Y:yes, N:no)");
		if (confirmStr.toLowerCase().equals("y") || confirmStr.toLowerCase().equals("yes")) {
			//
			narrator.sayln("Removing a posting -->" + targetPosting.getTitle());
			postingService.remove(targetPosting.getUsid());
		} else {
			narrator.sayln("Remove cancelled, the posting is safe. --> " + targetPosting.getTitle());
		}
	}

}