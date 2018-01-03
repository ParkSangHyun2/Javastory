package javastory.budgetsh.stage3.budget.da.file.io;

public class AutoIdSequence {
	//
	private int sequence;
	private String className;

	public AutoIdSequence(String className) {
		this.sequence = 1;
		this.className = className;
	}
	
	
	public String getClassName() {
		return className;
	}
	
	public int nextSequence() {
		return this.sequence++;
	}
}
