/*
 * COPYRIGHT (c) NEXTREE Consulting 2016
 * This software is the proprietary of NEXTREE Consulting CO.  
 * 
 * @author <a href="mailto:eykim@nextree.co.kr">Kim, Eunyoung</a>
 * @since 2016. 7. 12.
 */
package javastory.budgetsh.stage3.club.logic;

import java.util.ArrayList;
import java.util.List;

import javastory.budgetsh.stage3.club.da.file.ClubStoreFileLycler;
import javastory.budgetsh.stage3.club.entity.club.ClubMembership;
import javastory.budgetsh.stage3.club.entity.club.CommunityMember;
import javastory.budgetsh.stage3.club.entity.club.RoleInClub;
import javastory.budgetsh.stage3.club.entity.club.TravelClub;
import javastory.budgetsh.stage3.club.service.ClubService;
import javastory.budgetsh.stage3.club.service.dto.ClubMembershipDto;
import javastory.budgetsh.stage3.club.service.dto.MemberDto;
import javastory.budgetsh.stage3.club.service.dto.TravelClubDto;
import javastory.budgetsh.stage3.club.store.ClubStore;
import javastory.budgetsh.stage3.club.store.MemberStore;
import javastory.budgetsh.stage3.club.util.MemberDuplicationException;
import javastory.budgetsh.stage3.club.util.NoSuchClubException;
import javastory.budgetsh.stage3.club.util.NoSuchMemberException;

public class ClubServiceLogic implements ClubService {
	//
	private ClubStore clubStore;
	private MemberStore memberStore;
	
	public ClubServiceLogic() {
		//
		this.clubStore = ClubStoreFileLycler.shareInstance().requestClubStore();
		this.memberStore = ClubStoreFileLycler.shareInstance().requestMemberStore();
	}

	@Override
	public boolean registerClub(TravelClubDto clubDto) {
		//
		if (clubStore.retrieveByName(clubDto.getName()) != null) {
			return false;
			//throw new ClubDuplicationException("It is already exist the club name:" + clubDto.getName());	
		}
		TravelClub club = clubDto.toTravelClub();
		String clubId = clubStore.create(club);
		
		clubDto.setUsid(clubId);
		return true;
	}

	@Override
	public TravelClubDto findClub(String clubId) {
		//
		TravelClub club = clubStore.retrieve(clubId);
		
		if(club == null) {
			throw new NoSuchClubException("No such a club with id: " + clubId);
		}
		
		return new TravelClubDto(club);
	}

	@Override
	public TravelClubDto findClubByName(String name) {
		//
		TravelClub club = clubStore.retrieveByName(name);
		
		if (club == null) {
			return null;
			//throw new NoSuchClubException("No such a club with name: " + name);
		} 
		
		return new TravelClubDto(club);
	}

	@Override
	public void modify(TravelClubDto clubDto) {
		//
		if (!clubStore.exists(clubDto.getUsid())) {
			throw new NoSuchClubException("No such a club with id: " + clubDto.getUsid());
		}
		
		TravelClub club = clubDto.toTravelClub();
		clubStore.update(club);
	}

	@Override
	public void remove(String clubId) {
		//
		if (!clubStore.exists(clubId)) {
			throw new NoSuchClubException("No such a club with id: " + clubId);
		}
		
		clubStore.delete(clubId);
	}

	// Membership
	@Override
	public boolean addMembership(ClubMembershipDto membershipDto) {
		//
		// check existing member
		String memberId = membershipDto.getMemberEmail();
		CommunityMember member = memberStore.retrieve(memberId);
		if (member == null) {
			throw new NoSuchClubException("No such a member with email: " + memberId);
		}
		
		// check existing membership in the club
		TravelClub club = clubStore.retrieve(membershipDto.getClubId());
		for (ClubMembership membership : club.getMembershipList()) {
			if(memberId.equals(membership.getMemberEmail())) {
				return false;
				//throw new MemberDuplicationException("There is member in club already -->" + memberId);
			}
		}
		
		// add membership
		ClubMembership clubMembership = membershipDto.toMembership();
		club.getMembershipList().add(clubMembership);
		clubStore.update(club);
		member.getMembershipList().add(clubMembership);
		memberStore.update(member);
		return true;
	}

	@Override
	public ClubMembershipDto findMembershipIn(String clubId, String memberId) {
		//
		TravelClub club = clubStore.retrieve(clubId);
		
		ClubMembership membership = getMembershipIn(club, memberId);
		
		return new ClubMembershipDto(membership);
	}
	
	@Override
	public List<ClubMembershipDto> findAllMembershipsIn(String clubId) {
		//
		List<ClubMembershipDto> clubMembershipDtos = new ArrayList<>();
		
		TravelClub club = clubStore.retrieve(clubId);
		
		for (ClubMembership membership : club.getMembershipList()) {
			clubMembershipDtos.add(new ClubMembershipDto(membership));
		}
		
		return clubMembershipDtos;
	}

	@Override
	public void modifyMembership(String clubId, ClubMembershipDto newMembership) {
		//
		String targetEmail = newMembership.getMemberEmail();
		RoleInClub newRole = newMembership.getRole();
		
		// modify membership of the club.
		TravelClub targetClub = clubStore.retrieve(clubId);
		ClubMembership membershipOfClub = getMembershipIn(targetClub, targetEmail);
		membershipOfClub.setRole(newRole);
		clubStore.update(targetClub);
		
		// modify membership of the member.
		CommunityMember targetMember = memberStore.retrieve(targetEmail);
		for (ClubMembership membershipOfMember : targetMember.getMembershipList()) {
			if (membershipOfMember.getClubId().equals(clubId)) {
				membershipOfMember.setRole(newRole);
			}
		}
		memberStore.update(targetMember);
		
	}

	@Override
	public void removeMembership(String clubId, String memberId) {
		//
		TravelClub foundClub = clubStore.retrieve(clubId);
		CommunityMember foundMember = memberStore.retrieve(memberId);
		ClubMembership clubMembership = getMembershipIn(foundClub, memberId);
		
		// remove membership
		foundClub.getMembershipList().remove(clubMembership);
		clubStore.update(foundClub);
		foundMember.getMembershipList().remove(clubMembership);
		memberStore.update(foundMember);
		
	}
	
	private ClubMembership getMembershipIn(TravelClub club, String memberEmail) {
		//
		for (ClubMembership membership : club.getMembershipList()) {
			if(memberEmail.equals(membership.getMemberEmail())) {
				return membership;
			}
		}
		
		String message = String.format("No such a member[email:%s] in club[name:%s]", memberEmail, club.getName());
		throw new NoSuchMemberException(message);
	}

	@Override
	public List<TravelClubDto> findAll() {
		// 
		List<TravelClub> foundList = clubStore.retrieveAll();
		List<TravelClubDto> foundDtoList = new ArrayList<TravelClubDto>();
		
		for(TravelClub club : foundList) {
			TravelClubDto dtoClub = new TravelClubDto(club);
			foundDtoList.add(dtoClub);
		}
		return foundDtoList;
	}

	@Override
	public List<MemberDto> findAllMembers() {
		//
		List<CommunityMember> foundList = memberStore.retrieveAll();
		List<MemberDto> foundDtoList = new ArrayList<MemberDto>();
		
		for(CommunityMember member: foundList) {
			MemberDto dtoMember = new MemberDto(member);
			foundDtoList.add(dtoMember);
		}
		return foundDtoList;
	}
}
