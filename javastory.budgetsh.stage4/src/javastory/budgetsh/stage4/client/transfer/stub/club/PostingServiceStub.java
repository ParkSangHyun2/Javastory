package javastory.budgetsh.stage4.client.transfer.stub.club;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javastory.budgetsh.stage4.client.transfer.SocketDispatcher;
import javastory.budgetsh.stage4.share.domain.club.dto.PostingDto;
import javastory.budgetsh.stage4.share.service.club.PostingService;
import javastory.budgetsh.stage4.share.util.RequestMessage;
import javastory.budgetsh.stage4.share.util.ResponseMessage;

public class PostingServiceStub implements PostingService{
	//
	private SocketDispatcher dispatcher;
	private String serviceName;
	
	public PostingServiceStub() {
		//
		serviceName = this.getClass().getInterfaces()[0].getSimpleName();
	}
	@Override
	public String register(String boardId, PostingDto posting) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("register", boardId, (new Gson()).toJson(posting), "String", "PostingDto");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		
		return response.getValue();
	}

	@Override
	public PostingDto find(String postingTitle) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("find", postingTitle, "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		
		return (new Gson()).fromJson(response.getValue(), PostingDto.class);
	}

	@Override
	public List<PostingDto> findByBoardId(String boardId) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("findByBoardId", boardId, "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		
		List<PostingDto> foundPostingDto = (new Gson()).fromJson(response.getValue(), new TypeToken<List<PostingDto>>() {}.getType());
		return (ArrayList<PostingDto>)foundPostingDto;
	}

	@Override
	public void modify(PostingDto newPosting) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("modify", (new Gson()).toJson(newPosting), "PostingDto");
		
		ResponseMessage response = null;
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
	}

	@Override
	public void remove(String postingId) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("remove", postingId, "String");
		
		try {
			dispatcher.dispatchVoid(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
	}

	@Override
	public void removeAllIn(String boardId) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("removeAllIn", boardId, "String");
		
		try {
			dispatcher.dispatchVoid(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
	}

	private RequestMessage createRequestMessage(String operation, String parameter, String remark) {
		//
		this.dispatcher = getDispatcher();
		RequestMessage requestMessage = new RequestMessage(operation, parameter);
		requestMessage.setServiceName(serviceName);
		requestMessage.setRemark(remark);
		return requestMessage;
	}

	private RequestMessage createRequestMessage(String operation, String parameter1, String parameter2,
			String remark1, String remark2) {
		//
		this.dispatcher = getDispatcher();
		RequestMessage requestMessage = new RequestMessage(operation, parameter1, parameter2);
		requestMessage.setServiceName(serviceName);
		requestMessage.setRemarks(remark1, remark2);
		return requestMessage;
	}

	private SocketDispatcher getDispatcher() {
		//
		return SocketDispatcher.getInstance("127.0.0.1", 9999);
	}
}
