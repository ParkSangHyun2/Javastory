package javastory.budgetsh.stage4.server.react.handler;

import java.util.List;

import com.google.gson.Gson;

import javastory.budgetsh.stage4.server.logic.club.ClubServiceLogicLycler;
import javastory.budgetsh.stage4.share.domain.club.dto.MemberDto;
import javastory.budgetsh.stage4.share.service.club.MemberService;
import javastory.budgetsh.stage4.share.util.RequestMessage;
import javastory.budgetsh.stage4.share.util.ResponseMessage;

public class MemberServiceHandler implements ServiceHandler {
	//
	private MemberService memberService;
	
	public MemberServiceHandler() {
		//
		memberService = ClubServiceLogicLycler.shareInstance().createMemberService();
	}
	
	@Override
	public ResponseMessage handle(RequestMessage request) {
		//
		String operation = request.getOperation();
		String memberId, memberName;
		MemberDto memberDto;
		String responseValue = null;
		boolean success = true;
		
		switch(operation) {
		case "register":
			memberDto = (new Gson()).fromJson(request.getValue(), MemberDto.class);
			if(!memberService.register(memberDto)) {
				success = false;
			}
			break;
			
		case "find":
			memberId = request.getValue();
			memberDto = memberService.find(memberId);
			responseValue = (new Gson()).toJson(memberDto);
			break;
			
		case "findByName":
			memberName = request.getValue();
			List<MemberDto> foundMemberList = memberService.findByName(memberName);
			responseValue = (new Gson()).toJson(foundMemberList);
			break;
			
		case "modify":
			memberDto = (new Gson()).fromJson(request.getValue(), MemberDto.class);
			memberService.modify(memberDto);
			break;
			
		case "remove":
			memberId = request.getValue();
			memberService.remove(memberId);
			break;
		}
		ResponseMessage responseMessage = new ResponseMessage(operation, responseValue);
		responseMessage.setSuccess(success);
		return responseMessage;
	}

}
