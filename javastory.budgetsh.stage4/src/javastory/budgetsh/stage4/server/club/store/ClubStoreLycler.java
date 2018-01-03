package javastory.budgetsh.stage4.server.club.store;

public interface ClubStoreLycler {
	//
	public MemberStore requestMemberStore(); 
	public ClubStore requestClubStore(); 
	public BoardStore requestBoardStore(); 
	public PostingStore requestPostingStore(); 
}