package javastory.budgetsh.stage4.server.handler;

import javastory.budgetsh.stage4.message.RequestMessage;
import javastory.budgetsh.stage4.message.ResponseMessage;

public interface ServiceHandler {
	//
	public ResponseMessage handle(RequestMessage request); 
}
