package javastory.budgetsh.stage4.client.fxui.event;

import java.util.ArrayList;
import java.util.List;

import javastory.budgetsh.stage4.client.dto.club.ClubMembershipDto;
import javastory.budgetsh.stage4.client.dto.club.MemberDto;
import javastory.budgetsh.stage4.client.dto.club.TravelClubDto;
import javastory.budgetsh.stage4.client.dto.club.share.RoleInClub;
import javastory.budgetsh.stage4.client.service.club.ClubService;
import javastory.budgetsh.stage4.client.service.club.MemberService;
import javastory.budgetsh.stage4.client.transfer.stub.club.ServiceLogicLycler;



public class MemberEventHelper {
	//
	private ClubService clubService = ServiceLogicLycler.shareInstance().createClubService();
	private MemberService memberService = ServiceLogicLycler.shareInstance().createMemberService(); 
	public ArrayList<MemberDto> findAllMembers() {
		//
		ArrayList<MemberDto> foundMembers = (ArrayList<MemberDto>) clubService.findAllMembers();
		return foundMembers;
	}

	public boolean registMember(MemberDto member) {
		//
		if(memberService.register(member)) {
			return true;
		}
		return false;
	}

	public void removeMember(MemberDto removeMember) {
		//
		memberService.remove(removeMember.getEmail());
	}

	public ArrayList<ClubMembershipDto> findAllMembership(String clubName) {
		//
		TravelClubDto foundClub = clubService.findClubByName(clubName.toString());
		return (ArrayList<ClubMembershipDto>) clubService.findAllMembershipsIn(foundClub.getUsid());
	}

	public boolean addMembership(String memberName, String clubName, String role) {
		//
		List<MemberDto> foundMember = memberService.findByName(memberName);
		MemberDto member = foundMember.iterator().next();
		
		TravelClubDto foundClub = clubService.findClubByName(clubName); 
		
		if(member == null || foundClub == null) {
			return false;
		}
		ClubMembershipDto membershipDto = new ClubMembershipDto(foundClub.getUsid(),member.getEmail(),member.getName());
		switch(role) {
		case "President":
			membershipDto.setRole(RoleInClub.President);
			break;
		case "Member":
			membershipDto.setRole(RoleInClub.Member);
			break;
		}
		
		if(clubService.addMembership(membershipDto)) {
			return true;
		}
		return false;
	}

	public void removeMembership(ClubMembershipDto removeMember) {
		//
		clubService.removeMembership(removeMember.getClubId(), removeMember.getMemberEmail());
	}

	public void update(MemberDto modifiedMember) {
		//
		memberService.modify(modifiedMember);
	}
}
