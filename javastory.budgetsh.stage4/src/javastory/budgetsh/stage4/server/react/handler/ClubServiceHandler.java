package javastory.budgetsh.stage4.server.react.handler;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import javastory.budgetsh.stage4.server.logic.club.ClubServiceLogicLycler;
import javastory.budgetsh.stage4.share.domain.club.dto.ClubMembershipDto;
import javastory.budgetsh.stage4.share.domain.club.dto.MemberDto;
import javastory.budgetsh.stage4.share.domain.club.dto.TravelClubDto;
import javastory.budgetsh.stage4.share.service.club.ClubService;
import javastory.budgetsh.stage4.share.util.RequestMessage;
import javastory.budgetsh.stage4.share.util.ResponseMessage;

public class ClubServiceHandler implements ServiceHandler{
	//
	private ClubService clubService;
	
	public ClubServiceHandler() {
		//
		clubService = ClubServiceLogicLycler.shareInstance().createClubService();
	}
	
	@Override
	public ResponseMessage handle(RequestMessage request) {
		//
		String operation = request.getOperation();
		String responseValue = null;
		String clubId, memberId, clubName;
		TravelClubDto club;
		boolean success = true;
		
		switch(operation) {
		case "registerClub":
			String json = request.getValue();
			club = (new Gson()).fromJson(json, TravelClubDto.class);
			if(!clubService.registerClub(club)) {
				success = false;
			}
			break;
			
		case "findAll":
			ArrayList<TravelClubDto> foundClubList = (ArrayList<TravelClubDto>) clubService.findAll();
			responseValue = (new Gson()).toJson(foundClubList);
			break;
			
		case "findClubByName":
			clubName = request.getValue();
			TravelClubDto foundClub = clubService.findClubByName(clubName);
			responseValue = (new Gson()).toJson(foundClub);
			break;
			
		case "modify":
			//
			club = (new Gson()).fromJson(request.getValue(), TravelClubDto.class);
			clubService.modify(club);
			responseValue = "success";
			break;
			
		case "remove":
			clubId = request.getValue();
			clubService.remove(clubId);
			responseValue = "success";
			break;
			
		case "addMembership":
			ClubMembershipDto membershipDto = (new Gson()).fromJson(request.getValue(), ClubMembershipDto.class);
			if(!clubService.addMembership(membershipDto)) {
				success = false;
			}
			break;
			
		case "findMembershipIn":
			String[] values = request.getValues();
			clubId = values[0];
			memberId = values[1];
			System.out.println(clubId +" , " +memberId);
			ClubMembershipDto foundMemberDto = clubService.findMembershipIn(clubId, memberId);
			responseValue = (new Gson()).toJson(foundMemberDto);
			break;
			
		case "findAllMembershipsIn":
			clubId = request.getValue();
			List<ClubMembershipDto> foundMembershipList = clubService.findAllMembershipsIn(clubId);
			responseValue = (new Gson()).toJson(foundMembershipList);
			break;
			
		case "modifyMembership":
			clubId = request.getValues()[0];
			ClubMembershipDto modifiedMembership = 
					(new Gson()).fromJson(request.getValues()[1], ClubMembershipDto.class);
			clubService.modifyMembership(clubId, modifiedMembership);
			responseValue = "success";
			break;
			
		case "removeMembership":
			clubId = request.getValues()[0];
			memberId = request.getValues()[1];
			clubService.removeMembership(clubId, memberId);

			responseValue = "success";
			break;
			
		case "findAllMembers":
			List<MemberDto> allMembers = clubService.findAllMembers();
			responseValue = (new Gson()).toJson(allMembers);
			break;
		}
		ResponseMessage responseMessage = new ResponseMessage(operation, responseValue);
		responseMessage.setSuccess(success);
		return responseMessage;
	}

}			
