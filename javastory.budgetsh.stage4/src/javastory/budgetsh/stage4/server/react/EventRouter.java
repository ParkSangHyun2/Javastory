package javastory.budgetsh.stage4.server.react;

import java.io.IOException;
import java.net.Socket;

import javastory.budgetsh.stage4.client.util.SocketWorker;
import javastory.budgetsh.stage4.message.RequestMessage;
import javastory.budgetsh.stage4.message.ResponseMessage;
import javastory.budgetsh.stage4.server.handler.AccountMonthlyServiceHandler;
import javastory.budgetsh.stage4.server.handler.AccountYearlyServiceHandler;
import javastory.budgetsh.stage4.server.handler.BoardServiceHandler;
import javastory.budgetsh.stage4.server.handler.CashBookServiceHandler;
import javastory.budgetsh.stage4.server.handler.ClubServiceHandler;
import javastory.budgetsh.stage4.server.handler.MemberServiceHandler;
import javastory.budgetsh.stage4.server.handler.PostingServiceHandler;
import javastory.budgetsh.stage4.server.handler.ServiceHandler;
import javastory.budgetsh.stage4.server.handler.TransactionServiceHandler;

public class EventRouter implements Runnable{
	//
	private SocketWorker socketWorker;
	
	public EventRouter(Socket socket) {
		//
		this.socketWorker = new SocketWorker(socket);
	}
	
	public void route() {
		//
		String json = socketWorker.readMessage();
		System.out.println("json : " + json);
		RequestMessage request = RequestMessage.fromJson(json);
		
		String serviceType = request.getType();
		
		ServiceHandler serviceHandler = null;
		System.out.println("ServiceType : " + serviceType);
		
		switch(serviceType) {
		case "BoardService":
			serviceHandler = new BoardServiceHandler();
			break;
			
		case "ClubService":
			serviceHandler = new ClubServiceHandler();
			break;
			
		case "MemberService":
			serviceHandler = new MemberServiceHandler();
			break;
			
		case "PostingService":
			serviceHandler = new PostingServiceHandler();
			break;
			
		case "AccountMonthlyService":
			serviceHandler = new AccountMonthlyServiceHandler();
			break;
			
		case "AccountYearlyService":
			serviceHandler = new AccountYearlyServiceHandler();
			break;
			
		case "CashBookService":
			serviceHandler = new CashBookServiceHandler();
			break;
			
		case "TransactionService":
			serviceHandler = new TransactionServiceHandler();
			break;
		}
		
		ResponseMessage response = serviceHandler.handle(request);
		try {
			socketWorker.writeMessage(response.toJson());
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		socketWorker.closeSocket();
	}

	@Override
	public void run() {
		//
		route();
	}
}
