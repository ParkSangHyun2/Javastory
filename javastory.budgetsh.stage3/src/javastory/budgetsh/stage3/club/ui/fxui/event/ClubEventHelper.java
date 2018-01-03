package javastory.budgetsh.stage3.club.ui.fxui.event;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.SingleSelectionModel;
import javastory.budgetsh.stage3.club.entity.club.RoleInClub;
import javastory.budgetsh.stage3.club.logic.ServiceLogicLycler;
import javastory.budgetsh.stage3.club.service.BoardService;
import javastory.budgetsh.stage3.club.service.ClubService;
import javastory.budgetsh.stage3.club.service.MemberService;
import javastory.budgetsh.stage3.club.service.PostingService;
import javastory.budgetsh.stage3.club.service.dto.ClubMembershipDto;
import javastory.budgetsh.stage3.club.service.dto.MemberDto;
import javastory.budgetsh.stage3.club.service.dto.TravelClubDto;

public class ClubEventHelper {
	//
	private ClubService clubService = ServiceLogicLycler.shareInstance().createClubService();
	private MemberService memberService = ServiceLogicLycler.shareInstance().createMemberService();
	
	public boolean createTravelClub(String clubName, String clubIntroduce) {
		//
		TravelClubDto registClub = new TravelClubDto(clubName, clubIntroduce);
		if(clubService.registerClub(registClub)) {
			return true;
		}
		return false;
	}
	
	public ArrayList<TravelClubDto> find(String clubName) {
		//
		TravelClubDto foundClub = clubService.findClubByName(clubName);
		ArrayList<TravelClubDto> clubList = new ArrayList<TravelClubDto>();
		clubList.add(foundClub);
		return clubList;
	}

	public ArrayList<TravelClubDto> findAll() {
		//
		List<TravelClubDto> foundClubs = clubService.findAll();
		ArrayList<TravelClubDto> clubList = new ArrayList<TravelClubDto>();
		clubList.addAll(foundClubs);
		return clubList;
	}

	public void remove(String usid) {
		//
		clubService.remove(usid);
	}


	
}
