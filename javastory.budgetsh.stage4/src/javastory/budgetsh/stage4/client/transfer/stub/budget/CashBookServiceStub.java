package javastory.budgetsh.stage4.client.transfer.stub.budget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javastory.budgetsh.stage4.client.dto.budget.CashBook;
import javastory.budgetsh.stage4.client.service.budget.CashBookService;
import javastory.budgetsh.stage4.client.transfer.SocketDispatcher;
import javastory.budgetsh.stage4.message.RequestMessage;
import javastory.budgetsh.stage4.message.ResponseMessage;

public class CashBookServiceStub implements CashBookService{
	//
	private SocketDispatcher dispatcher;
	private String serviceType;
	
	public CashBookServiceStub() {
		//
		serviceType = this.getClass().getInterfaces()[0].getSimpleName();
	}
	@Override
	public boolean exist(String bankAccount) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("exist", bankAccount, "String");
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
	public boolean regist(CashBook cashbook) {
		//
		RequestMessage requestMessage =
				createRequestMessage("regist", (new Gson()).toJson(cashbook),"CashBook");
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
	public CashBook retrieve(String account) {
		//
		RequestMessage reqeustMessage = 
				createRequestMessage("retrieve", account, "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(reqeustMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		return (new Gson()).fromJson(response.getValue(), CashBook.class);
	}

	@Override
	public void update(CashBook foundCashbook) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("update", (new Gson()).toJson(foundCashbook), "CashBook");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("CashBook update " + response.getValue());
	}

	@Override
	public void remove(CashBook foundCashbook) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("remove", (new Gson()).toJson(foundCashbook), "CashBook");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("CashBook remove " + response.getValue());
	}

	@Override
	public ArrayList<CashBook> retrieveAll() {
		//
		RequestMessage requestMessage = 
				createRequestMessage("retrieveAll", null, null);
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<CashBook> foundCashbookList = (new Gson()).fromJson(response.getValue(), new TypeToken<List<CashBook>>() {}.getType());
		return (ArrayList<CashBook>)foundCashbookList;
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
