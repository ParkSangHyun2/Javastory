package javastory.budgetsh.stage4.client.transfer.stub.budget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javastory.budgetsh.stage4.client.dto.budget.Transaction;
import javastory.budgetsh.stage4.client.service.budget.TransactionService;
import javastory.budgetsh.stage4.client.transfer.SocketDispatcher;
import javastory.budgetsh.stage4.message.RequestMessage;
import javastory.budgetsh.stage4.message.ResponseMessage;

public class TransactionServiceStub implements TransactionService{
	//
	private SocketDispatcher dispatcher;
	private String serviceType;
	
	public TransactionServiceStub() {
		//
		serviceType = this.getClass().getInterfaces()[0].getSimpleName();
	}
	@Override
	public void remove(String id) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("remove", id, "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		System.out.println("Transaction remove " + response.getValue());
	}

	@Override
	public void update(Transaction transaction) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("update", (new Gson()).toJson(transaction), "Transaction");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		System.out.println("Transaction update " + response.getValue());
	}

	@Override
	public boolean exist(String id) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("exist", id, "String");
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
	public Transaction retrieve(String id, String cashbookId) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("retrieve", id, cashbookId, "String", "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		return (new Gson()).fromJson(response.getValue(), Transaction.class);
	}

	@Override
	public boolean register(Transaction transaction) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("register", (new Gson()).toJson(transaction), "Transaction");
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
	public Collection<Transaction> retrieveAll(String id) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("retrieveAll", id, "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		List<Transaction> foundTransactionList = (new Gson()).fromJson(response.getValue(), new TypeToken<List<Transaction>>() {}.getType());
		return (ArrayList<Transaction>)foundTransactionList;
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
