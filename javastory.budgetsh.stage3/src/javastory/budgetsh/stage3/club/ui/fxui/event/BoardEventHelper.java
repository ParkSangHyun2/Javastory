package javastory.budgetsh.stage3.club.ui.fxui.event;

import java.util.ArrayList;
import java.util.List;

import javastory.budgetsh.stage3.club.logic.ServiceLogicLycler;
import javastory.budgetsh.stage3.club.service.BoardService;
import javastory.budgetsh.stage3.club.service.ClubService;
import javastory.budgetsh.stage3.club.service.PostingService;
import javastory.budgetsh.stage3.club.service.dto.BoardDto;
import javastory.budgetsh.stage3.club.service.dto.PostingDto;

public class BoardEventHelper {
	//
	private BoardService boardService = ServiceLogicLycler.shareInstance().createBoardService();
	private ClubService clubService = ServiceLogicLycler.shareInstance().createClubService();
	private PostingService postingService = ServiceLogicLycler.shareInstance().createPostingService();
	
	public String createBoard(String clubName, String boardName, String adminEmail) {
		//
		String clubId = clubService.findClubByName(clubName).getUsid();
		BoardDto boardDto = new BoardDto(clubId, boardName, adminEmail);
		String foundBoardName = boardService.register(boardDto);

		return foundBoardName;
	}

	public String loadBoard(String clubName) {
		//
		BoardDto foundBoard = boardService.findByClubName(clubName);
		if(foundBoard == null) {
			return null;
		}
		return foundBoard.getId();
	}

	public ArrayList<PostingDto> retrievePostings(String boardName) {
		//
		BoardDto foundBoard = boardService.findByClubName(boardName);
		List<PostingDto> foundList = postingService.findByBoardId(foundBoard.getId());
		if(foundList == null) {
			return null;
		}
		return (ArrayList<PostingDto>) foundList;
	}

	public String registerPosting(String title, String email, String content ,String clubName) {
		//
		BoardDto foundBoard = (BoardDto) boardService.findByClubName(clubName);
		
		PostingDto postingDto = new PostingDto(title, email, content);
		return postingService.register(foundBoard.getId(), postingDto);
	}

	public void update(PostingDto clickedPostingDto) {
		//
		postingService.modify(clickedPostingDto);
	}

}
