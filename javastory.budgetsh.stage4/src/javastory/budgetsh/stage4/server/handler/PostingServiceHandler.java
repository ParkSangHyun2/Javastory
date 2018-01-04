package javastory.budgetsh.stage4.server.handler;

import java.util.List;

import com.google.gson.Gson;

import javastory.budgetsh.stage4.message.RequestMessage;
import javastory.budgetsh.stage4.message.ResponseMessage;
import javastory.budgetsh.stage4.server.club.logic.ClubServiceLogicLycler;
import javastory.budgetsh.stage4.server.club.service.PostingService;
import javastory.budgetsh.stage4.server.club.service.dto.PostingDto;

public class PostingServiceHandler implements ServiceHandler {
	//
	PostingService postingService;
	
	public PostingServiceHandler() {
		//
		postingService = ClubServiceLogicLycler.shareInstance().createPostingService();
	}
	
	/*
	 * 	public String register(String boardId, PostingDto posting); 
	public PostingDto find(String postingTitle); 
	public List<PostingDto> findByBoardId(String boardId);
	public void modify(PostingDto newPosting);
	public void remove(String postingId);
	public void removeAllIn(String boardId);
	(non-Javadoc)
	 * @see javastory.budgetsh.stage4.server.handler.ServiceHandler#handle(javastory.budgetsh.stage4.message.RequestMessage)
	 */
	@Override
	public ResponseMessage handle(RequestMessage request) {
		//
		String serviceName = request.getServiceName();
		String responseValue = null;
		String boardId, postingTitle, postingId;
		PostingDto postingDto;
		
		switch(serviceName) {
		case "register":
			boardId = request.getValues()[0];
			postingDto = (new Gson()).fromJson(request.getValues()[1], PostingDto.class);
			String postingName = postingService.register(boardId, postingDto);
			responseValue = postingName;
			break;
			
		case "find":
			postingTitle = request.getValue();
			postingDto = postingService.find(postingTitle);
			responseValue = (new Gson()).toJson(postingDto);
			break;
			
		case "findByBoardId":
			boardId = request.getValue();
			List<PostingDto> foundPostingDto = postingService.findByBoardId(boardId);
			responseValue = (new Gson()).toJson(foundPostingDto);
			break;
			
		case "modify":
			postingDto = (new Gson()).fromJson(request.getValue(), PostingDto.class);
			postingService.modify(postingDto);
			responseValue = "success";
			break;
			
		case "remove":
			postingId = request.getValue();
			postingService.remove(postingId);
			responseValue = "success";
			break;
			
		case "removeAllIn":
			boardId = request.getValue();
			postingService.removeAllIn(boardId);
			responseValue = "success";
			break;
		}
		return new ResponseMessage(serviceName, responseValue);
	}

}
