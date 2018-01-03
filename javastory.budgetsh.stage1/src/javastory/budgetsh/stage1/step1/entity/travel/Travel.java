package javastory.budgetsh.stage1.step1.entity.travel;

import javastory.budgetsh.stage1.step1.share.DatePair;
import javastory.budgetsh.stage1.step1.share.Entity;
import javastory.budgetsh.stage1.step1.share.IdName;
import javastory.budgetsh.stage1.step1.share.Socialian;

public class Travel extends Entity{
	//
	private IdName club;
	private String title;
	private Socialian leader;
	private DatePair term;
	private int participantsCount;
	private String budgetGoal;
	private String memo;
	private long time;
	
	public Travel() {
		//
	}
	
	public Travel(String title) {
		//
		this.title = title;
	}
	
	public Travel(IdName club, String title, Socialian leader, DatePair term) {
		//
		this.club = club;
		this.title = title;
		this.leader = leader;
		this.term = term;
	}

	@Override
	public String toString() {
		//
		StringBuilder builder = new StringBuilder();
		
		builder.append("[ Id/ Name : ").append(club.getId()).append("/").append(club.getName());
		builder.append(", title : ").append(title);
		builder.append(", leader : ").append(leader);
		builder.append(", term : ").append(term);
		builder.append(", PartcipantsCount : ").append(participantsCount);
		builder.append(", BudgetGoal : ").append(budgetGoal);
		builder.append(", Memo : ").append(memo);
		builder.append(", Time :").append(time);
		
		return builder.toString();
	}
	
	public static Travel getSample() {
		//
		IdName idName = new IdName("i003","Java");
		DatePair datePair = new DatePair("2017-10-10","2017-12-31");
		Socialian leader = new Socialian();
		
		return new Travel(idName, "Go JavaIsland", leader, datePair);
	}
	
	public IdName getIdName() {
		//
		return club;
	}
	
	public IdName getClub() {
		//
		return club;
	}
	
	public void setClub(IdName idName) {
		//
		this.club = idName;
	}
	
	public String getTitle() {
		//
		return title;
	}
	
	public void setTitle(String title) {
		//
		this.title = title;
	}
	
	public Socialian getLeader() {
		//
		return leader;
	}
	
	public void setLeader(Socialian leader) {
		//
		this.leader = leader;
	}
	
	public DatePair getTerm() {
		//
		return term;
	}
	
	public void setTerm(DatePair term) {
		//
		this.term = term;
	}
	
	public int getParticapantsCount() {
		//
		return participantsCount;
	}
	
	public void setParticipantsCount(int participantsCount) {
		//
		this.participantsCount = participantsCount;
	}
	
	public String getBudgetGoal() {
		//
		return budgetGoal;
	}
	
	public void setBudgetGoal(String budgetGoal) {
		//
		this.budgetGoal = budgetGoal;
	}
	
	public String getMemo() {
		//
		return memo;
	}
	
	public void setMemo(String memo) {
		//
		this.memo = memo;
	}
	
	public long getTime() {
		//
		return time;
	}
	
	public void setTime(long time) {
		//
		this.time = time;
	}
	
	public static void main(String[] args) {
		//
		System.out.println(getSample());
	}
}