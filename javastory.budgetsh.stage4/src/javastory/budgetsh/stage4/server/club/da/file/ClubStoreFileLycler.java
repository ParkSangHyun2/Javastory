package javastory.budgetsh.stage4.server.club.da.file;

import javastory.budgetsh.stage4.server.club.store.BoardStore;
import javastory.budgetsh.stage4.server.club.store.ClubStore;
import javastory.budgetsh.stage4.server.club.store.ClubStoreLycler;
import javastory.budgetsh.stage4.server.club.store.MemberStore;
import javastory.budgetsh.stage4.server.club.store.PostingStore;

public class ClubStoreFileLycler implements ClubStoreLycler {
	//
	private static ClubStoreLycler instance;
	
	private ClubStoreFileLycler() {
		//
	}
	
	public synchronized static ClubStoreLycler shareInstance() {
		//
		if (instance == null) {
			instance = new ClubStoreFileLycler();
		}
		return instance;
	} 
	
	@Override
	public MemberStore requestMemberStore() {
		//
		return new MemberFileStore();
	}

	@Override
	public ClubStore requestClubStore() {
		//
		return new ClubFileStore();
	}

	@Override
	public BoardStore requestBoardStore() {
		//
		return new BoardFileStore();
	}

	@Override
	public PostingStore requestPostingStore() {
		//
		return new PostingFileStore();
	}
}
