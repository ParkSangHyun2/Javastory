package javastory.budgetsh.stage4.client.transfer.stub.club;

import java.util.List;

import javastory.budgetsh.stage4.client.dto.club.BoardDto;
import javastory.budgetsh.stage4.client.service.club.BoardService;
import javastory.budgetsh.stage4.client.transfer.SocketDispatcher;

public class BoardServiceStub implements BoardService{
	//
	private SocketDispatcher dispatcher;
	
	public BoardServiceStub() {
		//
	}
	
	@Override
	public String register(BoardDto board) {
		//
		this.dispatcher = getDispatcher();
		return null;
	}

	@Override
	public BoardDto find(String boardId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BoardDto> findByName(String boardName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoardDto findByClubName(String clubName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modify(BoardDto board) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String boardId) {
		// TODO Auto-generated method stub
		
	}
	
	private SocketDispatcher getDispatcher() {
		//
		return SocketDispatcher.getInstance("127.0.0.1", 9999);
	}

}
