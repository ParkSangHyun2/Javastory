/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 24.
 */
package javastory.budgetsh.stage2.club.da.file;

import java.util.List;
import java.util.NoSuchElementException;

import javastory.budgetsh.stage2.club.da.file.io.BoardFile;
import javastory.budgetsh.stage2.club.entity.board.SocialBoard;
import javastory.budgetsh.stage2.club.store.BoardStore;
import javastory.budgetsh.stage2.club.util.MemberDuplicationException;

public class BoardFileStore implements BoardStore {
	//
	private BoardFile boardFile; 
	
	public BoardFileStore() {
		//  
		this.boardFile = new BoardFile(); 
	}
	
	@Override
	public String create(SocialBoard board) {
		// 
		if (boardFile.exists(board.getId())) {
			throw new MemberDuplicationException("Member already exists with email: " + board.getId()); 
		}
		
		boardFile.write(board); 
		return board.getId();
	}

	@Override
	public SocialBoard retrieve(String boardId) {
		// 
		return boardFile.read(boardId); 
	}
	
	@Override
	public List<SocialBoard> retrieveByName(String name) {
		//
		return boardFile.readByName(name); 
	}

	@Override
	public void update(SocialBoard board) {
		// 
		if (!boardFile.exists(board.getId())) {
			throw new NoSuchElementException("No such a element: " + board.getId()); 
		}
		
		boardFile.update(board); 
	}

	@Override
	public void delete(String boardId) {
		// 
		boardFile.delete(boardId);
	}

	@Override
	public boolean exists(String baordId) {
		//
		return boardFile.exists(baordId);
	}
}