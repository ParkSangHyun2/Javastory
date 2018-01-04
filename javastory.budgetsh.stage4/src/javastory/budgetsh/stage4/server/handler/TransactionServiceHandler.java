package javastory.budgetsh.stage4.server.handler;

import java.util.List;

import com.google.gson.Gson;

import javastory.budgetsh.stage4.message.RequestMessage;
import javastory.budgetsh.stage4.message.ResponseMessage;
import javastory.budgetsh.stage4.server.budget.entity.budget.Transaction;
import javastory.budgetsh.stage4.server.budget.logic.BudgetServiceLogicLycler;
import javastory.budgetsh.stage4.server.budget.service.TransactionService;

public class TransactionServiceHandler implements ServiceHandler {
	//
	private TransactionService transactionService;
	
	public TransactionServiceHandler() {
		// 
		transactionService = BudgetServiceLogicLycler.shareInstance().createTransactionService();
	}
	/*
	 * 	void remove(String id);

	void update(Transaction transaction);

	boolean exist(String id);

	Transaction retrieve(String id, String cashbookId);

	boolean register(Transaction transaction);

	Collection<Transaction> retrieveAll(String id);

	(non-Javadoc)
	 * @see javastory.budgetsh.stage4.server.handler.ServiceHandler#handle(javastory.budgetsh.stage4.message.RequestMessage)
	 */
	@Override
	public ResponseMessage handle(RequestMessage request) {
		//
		String serviceName = request.getServiceName();
		String responseValue = null;
		String id, cashbookId;
		Transaction transaction;
		boolean isSuccess = true;
		
		switch(serviceName) {
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
		
		ResponseMessage responseMessage = new ResponseMessage(serviceName, responseValue);
		responseMessage.setSuccess(isSuccess);
		return responseMessage;
	}

}
