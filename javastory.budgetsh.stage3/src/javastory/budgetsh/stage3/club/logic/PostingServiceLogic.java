/*
 * COPYRIGHT (c) NEXTREE Consulting 2016
 * This software is the proprietary of NEXTREE Consulting CO.  
 * 
 * @author <a href="mailto:eykim@nextree.co.kr">Kim, Eunyoung</a>
 * @since 2016. 7. 12.
 */
package javastory.budgetsh.stage3.club.logic;

import java.util.ArrayList;
import java.util.List;

import javastory.budgetsh.stage3.club.da.file.ClubStoreFileLycler;
import javastory.budgetsh.stage3.club.entity.board.Posting;
import javastory.budgetsh.stage3.club.entity.board.SocialBoard;
import javastory.budgetsh.stage3.club.service.PostingService;
import javastory.budgetsh.stage3.club.service.dto.PostingDto;
import javastory.budgetsh.stage3.club.store.BoardStore;
import javastory.budgetsh.stage3.club.store.PostingStore;
import javastory.budgetsh.stage3.club.util.NoSuchBoardException;
import javastory.budgetsh.stage3.club.util.NoSuchPostingException;

public class PostingServiceLogic implements PostingService {
	//
	private BoardStore boardStore;
	private PostingStore postingStore;

	public PostingServiceLogic() {
		//
		this.boardStore = ClubStoreFileLycler.shareInstance().requestBoardStore();
		this.postingStore = ClubStoreFileLycler.shareInstance().requestPostingStore();
	}

	@Override
	public String register(String boardId, PostingDto postingDto) {
		//
		SocialBoard board = boardStore.retrieve(boardId);
		if (board == null) {
			return null;
			//throw new NoSuchBoardException("No such board with id --> " + boardId);
		}
		Posting posting = postingDto.toPostingIn(board);

		return postingStore.create(posting);
	}

	@Override
	public PostingDto find(String postingTitle) {
		//
		Posting posting = postingStore.retrieve(postingTitle);
		if (posting == null) {
			throw new NoSuchPostingException("No such a posting with Title : " + postingTitle);
		}
		return new PostingDto(posting);
	}

	@Override
	public List<PostingDto> findByBoardId(String boardId) {
		//
		SocialBoard board = boardStore.retrieve(boardId);
		if (board == null) {
			return null;
			//throw new NoSuchBoardException("No such board with id --> " + boardId);
		}

		List<Posting> postings = postingStore.retrieveByBoardId(boardId);

		List<PostingDto> postingDtos = new ArrayList<>();
		for (Posting posting : postings) {
			postingDtos.add(new PostingDto(posting));
		}
		return postingDtos;
	}

	@Override
	public void modify(PostingDto newPosting) {
		//
		String postingId = newPosting.getUsid();
		Posting targetPosting = postingStore.retrieve(postingId);
		if (targetPosting == null) {
			throw new NoSuchPostingException("No such a posting with title : " + postingId);
		}
		
		// modify if only user inputs some value.
		if (newPosting.getTitle() != null && !newPosting.getTitle().isEmpty()) {
			targetPosting.setTitle(newPosting.getTitle());
		}
		if (newPosting.getContents() != null && !newPosting.getContents().isEmpty()) {
			targetPosting.setContents(newPosting.getContents());
		}
		targetPosting.setReadCount(newPosting.getReadCount());
		
		postingStore.update(targetPosting);
	}

	@Override
	public void remove(String postingId) {
		//
		if (!postingStore.exists(postingId)) {
			throw new NoSuchPostingException("No such a posting with id : " + postingId);
		}
		postingStore.delete(postingId);
	}

	@Override
	public void removeAllIn(String boardId) {
		//
		List<Posting> postings = postingStore.retrieveByBoardId(boardId);
		for (Posting posting : postings) {
			postingStore.delete(posting.getId());
		}
	}
}
