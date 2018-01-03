/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 24.
 */
package javastory.budgetsh.stage2.club.da.file;

import java.util.List;
import java.util.NoSuchElementException;

import javastory.budgetsh.stage2.club.da.file.io.MemberFile;
import javastory.budgetsh.stage2.club.entity.club.CommunityMember;
import javastory.budgetsh.stage2.club.store.MemberStore;
import javastory.budgetsh.stage2.club.util.MemberDuplicationException;

public class MemberFileStore implements MemberStore {
	//
	private MemberFile memberFileDb; 
	
	public MemberFileStore() {
		//  
		this.memberFileDb = new MemberFile(); 
	}
	
	@Override
	public String create(CommunityMember member) {
		// 
		if (memberFileDb.exists(member.getId())) {
			throw new MemberDuplicationException("Member already exists with email: " + member.getId()); 
		}
		
		memberFileDb.write(member); 
		return member.getId();
	}

	@Override
	public CommunityMember retrieve(String memberId) {
		// 
		return memberFileDb.read(memberId); 
	}
	
	@Override
	public List<CommunityMember> retrieveByName(String name) {
		//
		return memberFileDb.readByName(name); 
	}

	@Override
	public void update(CommunityMember member) {
		// 
		if (!memberFileDb.exists(member.getId())) {
			throw new NoSuchElementException("No such a member with email: " + member.getId()); 
		}
		
		memberFileDb.update(member); 
	}

	@Override
	public void delete(String memberId) {
		// 
		memberFileDb.delete(memberId);
	}

	@Override
	public boolean exists(String memberId) {
		//
		return memberFileDb.exists(memberId);
	}
}