package javastory.budgetsh.stage4.share.domain.budget.budget;

public class DocumentoryEvidence {
	//
	private String type;
	private Transaction transaction;
	private String contents;
	
	public DocumentoryEvidence(Transaction transaction, String type, String contents) {
		//
		this.transaction = transaction;
		this.type = type;
		this.contents = contents;
	}

	@Override
	public String toString() {
		return "DocumentoryEvidence [type=" + type + ", transaction=" + transaction.getTitle() + ", contents=" + contents + "]";
	}

	public static DocumentoryEvidence getSample() {
		//
		DocumentoryEvidence evidence= new DocumentoryEvidence(Transaction.getSample(), "receipt", "dinner");
		
		return evidence;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public static void main(String[] args) {
		//
		System.out.println(getSample());
	}
	
}
