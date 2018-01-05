package javastory.budgetsh.stage4.server.react.handler;

import java.util.List;

import com.google.gson.Gson;

import javastory.budgetsh.stage4.server.logic.club.ClubServiceLogicLycler;
import javastory.budgetsh.stage4.share.domain.club.dto.PostingDto;
import javastory.budgetsh.stage4.share.service.club.PostingService;
import javastory.budgetsh.stage4.share.util.RequestMessage;
import javastory.budgetsh.stage4.share.util.ResponseMessage;

public class PostingServiceHandler implements ServiceHandler {
	//
	PostingService postingService;
	
	public PostingServiceHandler() {
		//
		postingService = ClubServiceLogicLycler.shareInstance().createPostingService();
	}

	@Override
	public ResponseMessage handle(RequestMessage request) {
		//
		String operation = request.getOperation();
		String responseValue = null;
		String boardId, postingTitle, postingId;
		PostingDto postingDto;
		
		switch(operation) {
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
		return new ResponseMessage(operation, responseValue);
	}

}
