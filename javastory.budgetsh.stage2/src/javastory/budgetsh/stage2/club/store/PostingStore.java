/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 24.
 */
package javastory.budgetsh.stage2.club.store;

import java.util.List;

import javastory.budgetsh.stage2.club.entity.board.Posting;

public interface PostingStore {
	//
	public String create(Posting posting); 
	public Posting retrieve(String postingId);
	public List<Posting> retrieveByBoardId(String boardId);
	public List<Posting> retrieveByTitle(String title);
	public void update(Posting posting); 
	public void delete(String posingId); 
	
	public boolean exists(String postingId); 
}