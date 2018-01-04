package javastory.budgetsh.stage4.client.transfer.stub.club;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javastory.budgetsh.stage4.client.dto.club.BoardDto;
import javastory.budgetsh.stage4.client.service.club.BoardService;
import javastory.budgetsh.stage4.client.transfer.SocketDispatcher;
import javastory.budgetsh.stage4.message.RequestMessage;
import javastory.budgetsh.stage4.message.ResponseMessage;

public class BoardServiceStub implements BoardService{
	//
	private SocketDispatcher dispatcher;
	private String serviceType;
	
	public BoardServiceStub() {
		//
		serviceType = this.getClass().getInterfaces()[0].getSimpleName();
	}
	
	@Override
	public String register(BoardDto board) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("register", (new Gson()).toJson(board), "BoardDto");
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
	public BoardDto find(String boardId) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("find", boardId, "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		
		return (new Gson()).fromJson(response.getValue(), BoardDto.class);
	}

	@Override
	public List<BoardDto> findByName(String boardName) {
		//
		RequestMessage reqeustMessage = 
				createRequestMessage("findByName", boardName, "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(reqeustMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		
		List<BoardDto> foundBoardList = (new Gson()).fromJson(response.getValue(), new TypeToken<List<BoardDto>>() {}.getType());
		return (ArrayList<BoardDto>)foundBoardList;
	}

	@Override
	public BoardDto findByClubName(String clubName) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("findByClubName", clubName, "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		return (new Gson()).fromJson(response.getValue(), BoardDto.class);
	}

	@Override
	public void modify(BoardDto board) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("modify", (new Gson()).toJson(board), "BoardDto");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		System.out.println("Board modify " + response.getValue());
	}

	@Override
	public void remove(String boardId) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("remove", boardId, "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		System.out.println("Board remove " + response.getValue());
	}
	
	private RequestMessage createRequestMessage(String serviceName, String parameter, String remark) {
		//
		this.dispatcher = getDispatcher();
		RequestMessage requestMessage = new RequestMessage(serviceName, parameter);
		requestMessage.setType(serviceType);
		requestMessage.setRemark(remark);
		return requestMessage;
	}
	
	private SocketDispatcher getDispatcher() {
		//
		return SocketDispatcher.getInstance("127.0.0.1", 9999);
	}

}
