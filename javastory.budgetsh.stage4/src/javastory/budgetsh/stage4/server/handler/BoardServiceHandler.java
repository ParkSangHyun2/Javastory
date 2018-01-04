package javastory.budgetsh.stage4.server.handler;

import java.util.List;

import com.google.gson.Gson;

import javastory.budgetsh.stage4.message.RequestMessage;
import javastory.budgetsh.stage4.message.ResponseMessage;
import javastory.budgetsh.stage4.server.club.logic.ClubServiceLogicLycler;
import javastory.budgetsh.stage4.server.club.service.BoardService;
import javastory.budgetsh.stage4.server.club.service.dto.BoardDto;

public class BoardServiceHandler implements ServiceHandler {
	//
	private BoardService boardService;
	
	public BoardServiceHandler() {
		//
		boardService = ClubServiceLogicLycler.shareInstance().createBoardService();
	}
	/*
	 * 	public String register(BoardDto board); 
	public BoardDto find(String boardId);
	public List<BoardDto> findByName(String boardName);
	public BoardDto findByClubName(String clubName);
	public void modify(BoardDto board); 
	public void remove(String boardId);
	(non-Javadoc)
	 * @see javastory.budgetsh.stage4.server.handler.ServiceHandler#handle(javastory.budgetsh.stage4.message.RequestMessage)
	 */
	@Override
	public ResponseMessage handle(RequestMessage request) {
		//
		String serviceName = request.getServiceName();
		String responseValue = null;
		String boardId, boardName, clubName;
		BoardDto boardDto = null;
		
		switch(serviceName) {
		case "register":
			boardDto = (new Gson()).fromJson(request.getValue(), BoardDto.class);
			boardName = boardService.register(boardDto);
			responseValue = boardName;
			break;
			
		case "find":
			boardId = request.getValue();
			boardDto = boardService.find(boardId);
			responseValue = (new Gson()).toJson(boardDto);
			break;
			
		case "findByName":
			boardName = request.getValue();
			List<BoardDto> foundBoardList = boardService.findByName(boardName);
			responseValue = (new Gson()).toJson(foundBoardList);
			break;
			
		case "findByClubName":
			clubName = request.getValue();
			boardDto = boardService.findByClubName(clubName);
			responseValue = (new Gson()).toJson(boardDto);
			break;
			
		case "modify":
			boardDto = (new Gson()).fromJson(request.getValue(), BoardDto.class);
			boardService.modify(boardDto);
			responseValue = "Success";
			break;
			
		case "remove":
			boardId = request.getValue();
			boardService.remove(boardId);
			responseValue = "Success";
			break;
		}
		return new ResponseMessage(serviceName, responseValue);
	}

}
