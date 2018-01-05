package javastory.budgetsh.stage4.server.react.handler;

import java.util.List;

import com.google.gson.Gson;

import javastory.budgetsh.stage4.server.logic.budget.BudgetServiceLogicLycler;
import javastory.budgetsh.stage4.share.domain.budget.budget.Transaction;
import javastory.budgetsh.stage4.share.service.budget.TransactionService;
import javastory.budgetsh.stage4.share.util.RequestMessage;
import javastory.budgetsh.stage4.share.util.ResponseMessage;

public class TransactionServiceHandler implements ServiceHandler {
	//
	private TransactionService transactionService;
	
	public TransactionServiceHandler() {
		// 
		transactionService = BudgetServiceLogicLycler.shareInstance().createTransactionService();
	}

	@Override
	public ResponseMessage handle(RequestMessage request) {
		//
		String operation = request.getOperation();
		String responseValue = null;
		String id, cashbookId;
		Transaction transaction;
		boolean isSuccess = true;
		
		switch(operation) {
		case "remove":
			id = request.getValue();
			transactionService.remove(id);
			responseValue = "success";
			break;
			
		case "update":
			transaction = (new Gson()).fromJson(request.getValue(), Transaction.class);
			transactionService.update(transaction);
			responseValue = "success";
			break;
			
		case "exist":
			id = request.getValue();
			if(!transactionService.exist(id)) {
				isSuccess = false;
			}
			 
			break;
			
		case "retrieve":
			id = request.getValues()[0];
			cashbookId = request.getValues()[1];
			transaction = transactionService.retrieve(id, cashbookId);
			responseValue = (new Gson()).toJson(transaction);
			break;
			
		case "register":
			transaction = (new Gson()).fromJson(request.getValue(), Transaction.class);
			if(!transactionService.register(transaction)) {
				isSuccess = false;
			}
			break;
			
		case "retrieveAll":
			id = request.getValue();
			List<Transaction> foundTransactionList = (List<Transaction>) transactionService.retrieveAll(id);
			responseValue = (new Gson()).toJson(foundTransactionList);
			break;
		}
		
		ResponseMessage responseMessage = new ResponseMessage(operation, responseValue);
		responseMessage.setSuccess(isSuccess);
		return responseMessage;
	}

}
