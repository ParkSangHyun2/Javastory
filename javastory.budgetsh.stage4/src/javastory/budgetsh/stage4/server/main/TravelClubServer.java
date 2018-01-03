package javastory.budgetsh.stage4.server.main;

import javastory.budgetsh.stage4.server.react.TravelClubServerReactor;

public class TravelClubServer {
	//
	public static void main(String[] args) {
		//
		startServer();
	}
	
	private static void startServer() {
		//
		TravelClubServerReactor reactor = new TravelClubServerReactor();
		reactor.start();
		System.out.println("Server is started..");
	}
}
