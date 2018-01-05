package javastory.budgetsh.stage4.server.handler;

import java.util.List;

import com.google.gson.Gson;

import javastory.budgetsh.stage4.message.RequestMessage;
import javastory.budgetsh.stage4.message.ResponseMessage;
import javastory.budgetsh.stage4.server.club.logic.ClubServiceLogicLycler;
import javastory.budgetsh.stage4.server.club.service.MemberService;
import javastory.budgetsh.stage4.server.club.service.dto.MemberDto;

public class MemberServiceHandler implements ServiceHandler {
	//
	private MemberService memberService;
	
	public MemberServiceHandler() {
		//
		memberService = ClubServiceLogicLycler.shareInstance().createMemberService();
	}
	
	/*
	 * 	public boolean register(MemberDto member) throws InvalidEmailException; 
	public MemberDto find(String memberId); 
	public List<MemberDto> findByName(String memberName); 
	public void modify(MemberDto member) throws InvalidEmailException; 
	public void remove(String memberId); (non-Javadoc)
	 * @see javastory.budgetsh.stage4.server.handler.ServiceHandler#handle(javastory.budgetsh.stage4.message.RequestMessage)
	 */
	@Override
	public ResponseMessage handle(RequestMessage request) {
		//
		String serviceName = request.getServiceName();
		String memberId, memberName;
		MemberDto memberDto;
		String responseValue = null;
		boolean success = true;
		
		switch(serviceName) {
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
		ResponseMessage responseMessage = new ResponseMessage(serviceName, responseValue);
		responseMessage.setSuccess(success);
		return responseMessage;
	}

}
