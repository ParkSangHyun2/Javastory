/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 24.
 */
package javastory.budgetsh.stage3.club.store;

import java.util.List;

import javastory.budgetsh.stage3.club.entity.club.TravelClub;
import javastory.budgetsh.stage3.club.service.dto.TravelClubDto;

public interface ClubStore {
	//
	public String create(TravelClub club); 
	public TravelClub retrieve(String clubId);
	public TravelClub retrieveByName(String name);
	public void update(TravelClub club); 
	public void delete(String clubId); 
	
	public boolean exists(String clubId);
	public List<TravelClub> retrieveAll(); 
}