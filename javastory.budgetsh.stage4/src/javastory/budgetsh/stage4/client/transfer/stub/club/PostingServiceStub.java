package javastory.budgetsh.stage4.client.transfer.stub.club;

import java.util.List;

import javastory.budgetsh.stage4.client.dto.club.PostingDto;
import javastory.budgetsh.stage4.client.service.club.PostingService;

public class PostingServiceStub implements PostingService{

	@Override
	public String register(String boardId, PostingDto posting) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PostingDto find(String postingTitle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PostingDto> findByBoardId(String boardId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modify(PostingDto newPosting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String postingId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAllIn(String boardId) {
		// TODO Auto-generated method stub
		
	}

}
