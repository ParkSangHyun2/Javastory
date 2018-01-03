package javastory.budgetsh.stage1.step1.share;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatePair {
	//
	private String startDate;
	private String endDate;
	
	public DatePair(LocalDate startDate , LocalDate endDate) {
		//
		this.startDate = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
		this.endDate = endDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}
	
	public DatePair(String startDate, String endDate) {
		//
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public String toString() {
		//
		StringBuilder builder = new StringBuilder();
		builder.append("[StartDate : ").append(startDate);
		builder.append(", EndDate : ").append(endDate).append("]");
		
		return builder.toString();
	}
	
	public static DatePair getSample() {
		//
		return new DatePair(LocalDate.now(), LocalDate.now().plusDays(20)); 
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public static void main(String[] args) {
		// 
		System.out.println(getSample());
	}

}
