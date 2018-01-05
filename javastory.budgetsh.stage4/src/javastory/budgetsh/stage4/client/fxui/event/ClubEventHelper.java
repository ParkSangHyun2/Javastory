package javastory.budgetsh.stage4.client.fxui.event;

import java.util.ArrayList;
import java.util.List;

import javastory.budgetsh.stage4.client.transfer.stub.club.ServiceLogicLycler;
import javastory.budgetsh.stage4.share.domain.club.dto.TravelClubDto;
import javastory.budgetsh.stage4.share.service.club.ClubService;



public class ClubEventHelper {
	//
	private ClubService clubService = ServiceLogicLycler.shareInstance().createClubService();
	
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
