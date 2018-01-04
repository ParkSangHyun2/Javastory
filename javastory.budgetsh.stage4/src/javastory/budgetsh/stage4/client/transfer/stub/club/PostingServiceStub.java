package javastory.budgetsh.stage4.client.transfer.stub.club;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javastory.budgetsh.stage4.client.dto.club.PostingDto;
import javastory.budgetsh.stage4.client.service.club.PostingService;
import javastory.budgetsh.stage4.client.transfer.SocketDispatcher;
import javastory.budgetsh.stage4.message.RequestMessage;
import javastory.budgetsh.stage4.message.ResponseMessage;

public class PostingServiceStub implements PostingService{
	//
	private SocketDispatcher dispatcher;
	private String serviceType;
	
	public PostingServiceStub() {
		//
		serviceType = this.getClass().getInterfaces()[0].getSimpleName();
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
		
		System.out.println("Posting modify " + response.getValue());
	}

	@Override
	public void remove(String postingId) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("remove", postingId, "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		
		System.out.println("Posting remove " + response.getValue());
	}

	@Override
	public void removeAllIn(String boardId) {
		// TODO Auto-generated method stub
		
	}

	private RequestMessage createRequestMessage(String serviceName, String parameter, String remark) {
		//
		this.dispatcher = getDispatcher();
		RequestMessage requestMessage = new RequestMessage(serviceName, parameter);
		requestMessage.setType(serviceType);
		requestMessage.setRemark(remark);
		return requestMessage;
	}

	private RequestMessage createRequestMessage(String serviceName, String parameter1, String parameter2,
			String remark1, String remark2) {
		//
		this.dispatcher = getDispatcher();
		RequestMessage requestMessage = new RequestMessage(serviceName, parameter1, parameter2);
		requestMessage.setType(serviceType);
		requestMessage.setRemarks(remark1, remark2);
		return requestMessage;
	}

	private SocketDispatcher getDispatcher() {
		//
		return SocketDispatcher.getInstance("127.0.0.1", 9999);
	}
}
