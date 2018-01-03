package javastory.budgetsh.stage2.budget.share;

import java.util.Objects;
import java.util.UUID;

public class Entity {
	//
	private final String id;
	
	protected Entity() {
		this.id = UUID.randomUUID().toString();
	}
	
	protected Entity(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		// 
		return "id : " + id;
	}
	
	public static String getSampleId() {
		//
		return UUID.randomUUID().toString();
		
	}
	
	public String getId() {
		return id;
	}
	
	public boolean equals(Object target) {
		//
		if (this == target) {
			return true;
		}
		
		if (target == null || getClass() != target.getClass()) {
			return false;
		}
		
		Entity entity = (Entity) target;
		
		return Objects.equals(id, entity.id);
	}

	@Override
	public int hashCode() {
		//
		return Objects.hash(id);
	}
	
	
	
	
}
