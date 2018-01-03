package javastory.budgetsh.stage1.step4.util;

public class NoSuchMonthlyDueException extends RuntimeException {
	//
	private static final long serialVersionUID = -1928374655434526178L;
	
	public NoSuchMonthlyDueException(String message) {
		super(message);
	}
}
