/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 24.
 */
package javastory.budgetsh.stage4.server.store.club;

import java.util.List;

import javastory.budgetsh.stage4.share.domain.club.club.CommunityMember;

public interface MemberStore {
	//
	public String create(CommunityMember member); 
	public CommunityMember retrieve(String email); 
	public List<CommunityMember> retrieveByName(String name); 
	public void update(CommunityMember member); 
	public void delete(String email); 
	
	public boolean exists(String email);
	public List<CommunityMember> retrieveAll(); 
}
