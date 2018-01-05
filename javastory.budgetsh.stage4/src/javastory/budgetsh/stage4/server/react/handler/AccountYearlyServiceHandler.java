package javastory.budgetsh.stage4.server.react.handler;

import java.util.List;

import com.google.gson.Gson;

import javastory.budgetsh.stage4.server.logic.budget.BudgetServiceLogicLycler;
import javastory.budgetsh.stage4.share.domain.budget.account.AccountYearlyDue;
import javastory.budgetsh.stage4.share.domain.budget.account.MonthlyDue;
import javastory.budgetsh.stage4.share.service.budget.AccountYearlyService;
import javastory.budgetsh.stage4.share.util.RequestMessage;
import javastory.budgetsh.stage4.share.util.ResponseMessage;

public class AccountYearlyServiceHandler implements ServiceHandler {
	//
	private AccountYearlyService yearlyService;
	
	public AccountYearlyServiceHandler() {
		//
		yearlyService = BudgetServiceLogicLycler.shareInstance().createYearlyService();
	}

	@Override
	public ResponseMessage handle(RequestMessage request) {
		//
		String operation = request.getOperation();
		String account;
		AccountYearlyDue yearlyDue;
		boolean success = true;
		String responseValue = null;
		
		switch(operation) {
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
		ResponseMessage responseMessage = new ResponseMessage(operation, responseValue);
		responseMessage.setSuccess(success);
		return responseMessage;
	}

}
