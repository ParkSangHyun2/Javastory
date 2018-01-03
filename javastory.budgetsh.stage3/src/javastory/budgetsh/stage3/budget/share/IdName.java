package javastory.budgetsh.stage3.budget.share;

public class IdName {
	//
	private String id;
	private String name;
	
	public IdName() {
		//
	}
	
	public IdName(String id, String name) {
		//
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		// 
		StringBuilder builder = new StringBuilder();
		
		builder.append(name);
		
		return builder.toString();
	}
	
	public static IdName getSample() {
		//
		String id = Entity.getSampleId();
		String name = "sanghyun";
		
		return new IdName(id,name);
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public static void main(String[] args) {
		System.out.println(getSample());
	}
	
}
