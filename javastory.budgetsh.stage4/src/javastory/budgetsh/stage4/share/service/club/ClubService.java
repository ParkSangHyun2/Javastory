/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 24.
 */
package javastory.budgetsh.stage4.share.service.club;

import java.util.List;

import javastory.budgetsh.stage4.share.domain.club.dto.ClubMembershipDto;
import javastory.budgetsh.stage4.share.domain.club.dto.MemberDto;
import javastory.budgetsh.stage4.share.domain.club.dto.TravelClubDto;

public interface ClubService {
	//
	public boolean registerClub(TravelClubDto club);

	public TravelClubDto findClub(String clubId);

	public TravelClubDto findClubByName(String name);

	public void modify(TravelClubDto club);

	public void remove(String clubId);

	boolean addMembership(ClubMembershipDto membershipDto);

	public ClubMembershipDto findMembershipIn(String clubId, String memberId);

	public List<ClubMembershipDto> findAllMembershipsIn(String clubId);

	public void modifyMembership(String clubId, ClubMembershipDto member);

	public void removeMembership(String clubId, String memberId);

	public List<TravelClubDto> findAll();

	public List<MemberDto> findAllMembers();
}