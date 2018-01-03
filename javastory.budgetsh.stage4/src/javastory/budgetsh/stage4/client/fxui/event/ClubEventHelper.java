package javastory.budgetsh.stage4.client.fxui.event;

import java.util.ArrayList;
import java.util.List;

import javastory.budgetsh.stage4.client.dto.club.TravelClubDto;
import javastory.budgetsh.stage4.client.service.club.ClubService;
import javastory.budgetsh.stage4.client.service.club.MemberService;
import javastory.budgetsh.stage4.client.transfer.stub.club.ClubServiceStub;
import javastory.budgetsh.stage4.client.transfer.stub.club.ServiceLogicLycler;



public class ClubEventHelper {
	//
	private ClubService clubService;
	
	public boolean createTravelClub(String clubName, String clubIntroduce) {
		//
		clubService = new ClubServiceStub();
		TravelClubDto registClub = new TravelClubDto(clubName, clubIntroduce);
		if(clubService.registerClub(registClub)) {
			return true;
		}
		return false;
	}
	
	public ArrayList<TravelClubDto> find(String clubName) {
		//
		clubService = new ClubServiceStub();
		TravelClubDto foundClub = clubService.findClubByName(clubName);
		ArrayList<TravelClubDto> clubList = new ArrayList<TravelClubDto>();
		clubList.add(foundClub);
		return clubList;
	}

	public ArrayList<TravelClubDto> findAll() {
		//
		clubService = new ClubServiceStub();
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
