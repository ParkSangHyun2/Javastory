package javastory.budgetsh.stage4.client.util;

public class NoSuchMonthlyDueException extends RuntimeException {
	//
	private static final long serialVersionUID = -1928374655434526178L;
	
	public NoSuchMonthlyDueException(String message) {
		super(message);
	}
}
