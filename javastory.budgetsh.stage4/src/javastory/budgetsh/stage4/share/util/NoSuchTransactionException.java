package javastory.budgetsh.stage4.share.util;

public class NoSuchTransactionException extends RuntimeException {
	//
	private static final long serialVersionUID = -1928254655434575578L;
	
	public NoSuchTransactionException(String message) {
		super(message);
	}
}
