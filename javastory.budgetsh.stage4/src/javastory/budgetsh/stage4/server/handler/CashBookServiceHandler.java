package javastory.budgetsh.stage4.server.handler;


import java.util.List;

import com.google.gson.Gson;

import javastory.budgetsh.stage4.message.RequestMessage;
import javastory.budgetsh.stage4.message.ResponseMessage;
import javastory.budgetsh.stage4.server.budget.entity.budget.CashBook;
import javastory.budgetsh.stage4.server.budget.logic.BudgetServiceLogicLycler;
import javastory.budgetsh.stage4.server.budget.service.CashBookService;

public class CashBookServiceHandler implements ServiceHandler {
	//
	private CashBookService cashbookService;
	
	public CashBookServiceHandler() {
		//
		cashbookService = BudgetServiceLogicLycler.shareInstance().createCashBookService();
	}
	/*
	 * 
	boolean exist(String bankAccount);

	boolean regist(CashBook cashbook);

	CashBook retrieve(String account);

	void update(CashBook foundCashbook);

	void remove(CashBook foundCashbook);

	ArrayList<CashBook> retrieveAll();
	(non-Javadoc)
	 * @see javastory.budgetsh.stage4.server.handler.ServiceHandler#handle(javastory.budgetsh.stage4.message.RequestMessage)
	 */
	@Override
	public ResponseMessage handle(RequestMessage request) {
		//
		String serviceName = request.getServiceName();
		String bankAccount;
		CashBook cashbook;
		String responseValue = null;
		boolean success = true;
		
		switch(serviceName) {
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
		ResponseMessage responseMessage = new ResponseMessage(serviceName, responseValue);
		responseMessage.setSuccess(success);
		return responseMessage;
	}

}
