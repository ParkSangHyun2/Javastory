/*
 * COPYRIGHT (c) NEXTREE Consulting 2016
 * This software is the proprietary of NEXTREE Consulting CO.  
 * 
 * @author <a href="mailto:eykim@nextree.co.kr">Kim, Eunyoung</a>
 * @since 2016. 7. 12.
 */
package javastory.budgetsh.stage4.server.logic.club;

import javastory.budgetsh.stage4.share.service.club.BoardService;
import javastory.budgetsh.stage4.share.service.club.ClubService;
import javastory.budgetsh.stage4.share.service.club.ClubServiceLycler;
import javastory.budgetsh.stage4.share.service.club.MemberService;
import javastory.budgetsh.stage4.share.service.club.PostingService;

public class ClubServiceLogicLycler implements ClubServiceLycler {
	//
	private static ClubServiceLycler lycler;
	
	private ClubService clubService;
	private MemberService memberService;
	private BoardService boardService;
	private PostingService postingService;
	
	private ClubServiceLogicLycler() {
		//
	}
	public synchronized static ClubServiceLycler shareInstance() {
		//
		if (lycler == null) {
			lycler = new ClubServiceLogicLycler();
		}
		
		return lycler;
	}

	@Override
	public ClubService createClubService() {
		//
		if (clubService == null) {
			clubService = new ClubServiceLogic();
		}
		return clubService;
	}

	@Override
	public MemberService createMemberService() {
		//
		if (memberService == null) {
			memberService = new MemberServiceLogic();
		}
		return memberService;
	}

	@Override
	public BoardService createBoardService() {
		//
		if (boardService == null) {
			boardService = new BoardServiceLogic();
		}
		return boardService;
	}

	@Override
	public PostingService createPostingService() {
		//
		if (postingService == null) {
			postingService = new PostingServiceLogic();
		}
		return postingService;
	}
}
