package javastory.budgetsh.stage4.server.handler;

import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;

import javastory.budgetsh.stage4.message.RequestMessage;
import javastory.budgetsh.stage4.message.ResponseMessage;
import javastory.budgetsh.stage4.server.budget.entity.account.AccountYearlyDue;
import javastory.budgetsh.stage4.server.budget.entity.account.MonthlyDue;
import javastory.budgetsh.stage4.server.budget.logic.BudgetServiceLogicLycler;
import javastory.budgetsh.stage4.server.budget.service.AccountYearlyService;

public class AccountYearlyServiceHandler implements ServiceHandler {
	//
	private AccountYearlyService yearlyService;
	
	public AccountYearlyServiceHandler() {
		//
		yearlyService = BudgetServiceLogicLycler.shareInstance().createYearlyService();
	}
	/*
	 * 
	boolean exist(String account);

	void remove(String account);

	void update(AccountYearlyDue yearlyDue);

	AccountYearlyDue retrieve(String account);

	void regist(AccountYearlyDue yearlyDue);
	
	Collection<MonthlyDue> retrieveMonthlyDue(String account);
	
	public Collection<AccountYearlyDue> retrieveAll(String account);

	void addMonthlyDue(String year, String account, int month, String amount, String id, String name, String type);
	(non-Javadoc)
	 * @see javastory.budgetsh.stage4.server.handler.ServiceHandler#handle(javastory.budgetsh.stage4.message.RequestMessage)
	 */
	@Override
	public ResponseMessage handle(RequestMessage request) {
		//
		String serviceName = request.getServiceName();
		String account;
		AccountYearlyDue yearlyDue;
		boolean success = true;
		String responseValue = null;
		
		switch(serviceName) {
		case "exist":
			account = request.getValue();
			if(!yearlyService.exist(account)) {
				success = false;
			}
			break;
			
		case "remove":
			account = request.getValue();
			yearlyService.remove(account);
			responseValue = "success";
			break;
			
		case "update":
			yearlyDue = (new Gson()).fromJson(request.getValue(), AccountYearlyDue.class);
			yearlyService.update(yearlyDue);
			responseValue = "success";
			break;
			
		case "retrieve":
			account = request.getValue();
			yearlyDue = yearlyService.retrieve(account);
			responseValue = (new Gson()).toJson(yearlyDue);
			break;
			
		case "regist":
			yearlyDue = (new Gson()).fromJson(request.getValue(), AccountYearlyDue.class);
			yearlyService.regist(yearlyDue);
			responseValue = "success";
			break;
			
		case "retrieveMonthlyDue":
			account = request.getValue();
			List<MonthlyDue> monthlyDueList = (List<MonthlyDue>) yearlyService.retrieveMonthlyDue(account);
			responseValue = (new Gson()).toJson(monthlyDueList);
			
			break;
			
		case "retrieveAll":
			account = request.getValue();
			List<AccountYearlyDue> yearlyDueList = (List<AccountYearlyDue>) yearlyService.retrieveAll(account);
			responseValue = (new Gson()).toJson(yearlyDueList);
			break;
			
		case "addMonthlyDue":
			yearlyService.addMonthlyDue(request.getValues()[0], request.getValues()[1], Integer.parseInt(request.getValues()[2]),request.getValues()[3],
					request.getValues()[4], request.getValues()[5], request.getValues()[6]);
			break;
		}
		ResponseMessage responseMessage = new ResponseMessage(serviceName, responseValue);
		responseMessage.setSuccess(success);
		return responseMessage;
	}

}
