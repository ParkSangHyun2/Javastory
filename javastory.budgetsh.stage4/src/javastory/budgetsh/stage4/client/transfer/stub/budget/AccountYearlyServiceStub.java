package javastory.budgetsh.stage4.client.transfer.stub.budget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javastory.budgetsh.stage4.client.dto.budget.AccountYearlyDue;
import javastory.budgetsh.stage4.client.dto.budget.MonthlyDue;
import javastory.budgetsh.stage4.client.service.budget.AccountYearlyService;
import javastory.budgetsh.stage4.client.transfer.SocketDispatcher;
import javastory.budgetsh.stage4.message.RequestMessage;
import javastory.budgetsh.stage4.message.ResponseMessage;

public class AccountYearlyServiceStub implements AccountYearlyService{
	//
	private SocketDispatcher dispatcher;
	private String serviceType;
	
	public AccountYearlyServiceStub() {
		//
		serviceType = this.getClass().getInterfaces()[0].getSimpleName();
	}
	
	@Override
	public boolean exist(String account) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("exist", account, "String");
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
	public void remove(String account) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("remove", account, "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		System.out.println("YearlyDue " + response.getValue());
	}

	@Override
	public void update(AccountYearlyDue yearlyDue) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("update", (new Gson()).toJson(yearlyDue), "AccountYearlyDue");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		System.out.println("YearlyDue " + response.getValue());
		
	}

	@Override
	public AccountYearlyDue retrieve(String account) {
		//
		RequestMessage requestMessage =
				createRequestMessage("retrieve", account, "String");
		ResponseMessage response = null;
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		return (new Gson()).fromJson(response.getValue(), AccountYearlyDue.class);
	}

	@Override
	public void regist(AccountYearlyDue yearlyDue) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("regist", (new Gson()).toJson(yearlyDue), "AccountYearlyDue");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		System.out.println("YearlyDue regist " + response.getValue());
	}

	@Override
	public Collection<MonthlyDue> retrieveMonthlyDue(String account) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("retrieveMonthlyDue", account, "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		List<MonthlyDue> foundMonthlyDueList = (new Gson()).fromJson(response.getValue(), new TypeToken<List<MonthlyDue>>() {}.getType());
		return (ArrayList<MonthlyDue>)foundMonthlyDueList;
	}

	@Override
	public Collection<AccountYearlyDue> retrieveAll(String account) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("retrieveAll" , account, "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		List<AccountYearlyDue> foundYearlyDueList = (new Gson()).fromJson(response.getValue(), new TypeToken<List<AccountYearlyDue>>() {}.getType());
		return (ArrayList<AccountYearlyDue>) foundYearlyDueList;
	}

	@Override
	public void addMonthlyDue(String year, String account, int month, String amount, String id, String name,
			String type) {
		//should be modify from logic
		RequestMessage requestMessage = 
				createRequestMessage("addMonthlyDue", year, account, month, amount, id, name, type);
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		System.out.println(response.getValue());
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
			int parameter3, String parameter4, String parameter5, String parameter6, String parameter7) {
		//
		this.dispatcher = getDispatcher();
		RequestMessage requestMessage = new RequestMessage(serviceName, parameter1, parameter2, parameter3,
			 parameter4, parameter5, parameter6, parameter7);
		requestMessage.setType(serviceType);
		return requestMessage;
	}

	private SocketDispatcher getDispatcher() {
		//
		return SocketDispatcher.getInstance("127.0.0.1", 9999);
	}

}
