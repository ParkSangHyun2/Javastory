package javastory.budgetsh.stage4.server.react.handler;


import java.util.List;

import com.google.gson.Gson;

import javastory.budgetsh.stage4.server.logic.budget.BudgetServiceLogicLycler;
import javastory.budgetsh.stage4.share.domain.budget.budget.CashBook;
import javastory.budgetsh.stage4.share.service.budget.CashBookService;
import javastory.budgetsh.stage4.share.util.RequestMessage;
import javastory.budgetsh.stage4.share.util.ResponseMessage;

public class CashBookServiceHandler implements ServiceHandler {
	//
	private CashBookService cashbookService;
	
	public CashBookServiceHandler() {
		//
		cashbookService = BudgetServiceLogicLycler.shareInstance().createCashBookService();
	}

	@Override
	public ResponseMessage handle(RequestMessage request) {
		//
		String operation = request.getOperation();
		String bankAccount;
		CashBook cashbook;
		String responseValue = null;
		boolean success = true;
		
		switch(operation) {
		case "exist":
			bankAccount = request.getValue();
			if(!cashbookService.exist(bankAccount)) {
				success = false;
			}
			break;
			
		case "regist":
			cashbook = (new Gson()).fromJson(request.getValue(), CashBook.class);
			if(!cashbookService.regist(cashbook)) {
				success = false;
			}
			break;
			
		case "retrieve":
			bankAccount = request.getValue();
			cashbook = cashbookService.retrieve(bankAccount);
			responseValue = (new Gson()).toJson(cashbook);
			break;
			
		case "update":
			cashbook = (new Gson()).fromJson(request.getValue(), CashBook.class);
			cashbookService.update(cashbook);
			responseValue = "success";
			break;
			
		case "remove":
			cashbook  = (new Gson()).fromJson(request.getValue(), CashBook.class);
			cashbookService.remove(cashbook);
			responseValue = "success";
			break;
			
		case "retrieveAll":
			List<CashBook> foundCashbookList = cashbookService.retrieveAll();
			responseValue = (new Gson()).toJson(foundCashbookList);
			break;
		}
		ResponseMessage responseMessage = new ResponseMessage(operation, responseValue);
		responseMessage.setSuccess(success);
		return responseMessage;
	}

}
