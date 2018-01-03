package javastory.budgetsh.stage4.client.transfer.stub.club;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javastory.budgetsh.stage4.client.dto.club.MemberDto;
import javastory.budgetsh.stage4.client.service.club.MemberService;
import javastory.budgetsh.stage4.client.transfer.SocketDispatcher;
import javastory.budgetsh.stage4.message.RequestMessage;
import javastory.budgetsh.stage4.message.ResponseMessage;
import javastory.budgetsh.stage4.server.club.util.InvalidEmailException;

public class MemberServiceStub implements MemberService{
	//
	private SocketDispatcher dispatcher;
	private String serviceType;
	
	
	public MemberServiceStub() {
		this.serviceType = (this.getClass().getInterfaces())[0].getSimpleName();
	}

	@Override
	public boolean register(MemberDto member) throws InvalidEmailException {
		//
		RequestMessage requestMessage =
				createRequestMessage("register",(new Gson()).toJson(member),"MemberDto");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dispatcher.close();
		
		return response.isSuccess();
	}

	@Override
	public MemberDto find(String memberId) {
		//
		RequestMessage requestMessage =
				createRequestMessage("find",memberId, "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dispatcher.close();
		
		return (new Gson()).fromJson(response.getValue(), MemberDto.class);
	}

	@Override
	public List<MemberDto> findByName(String memberName) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("findByName", memberName, "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		List<MemberDto> foundMemberDtos = (new Gson()).fromJson(response.getValue(), new TypeToken<List<MemberDto>>() {}.getType());
		return (ArrayList<MemberDto>)foundMemberDtos;
	}

	@Override
	public void modify(MemberDto member) throws InvalidEmailException {
		//
		RequestMessage requestMessage = 
				createRequestMessage("modify", (new Gson()).toJson(member),"MemberDto");
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
	public void remove(String memberId) {
		// 		
		RequestMessage requestMessage = 
		createRequestMessage("remove", memberId,"MemberDto");
		ResponseMessage response = null;

		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
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
