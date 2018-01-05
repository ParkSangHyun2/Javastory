/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 24.
 */
package javastory.budgetsh.stage4.share.service.club;

import java.util.List;

import javastory.budgetsh.stage4.share.domain.club.dto.MemberDto;
import javastory.budgetsh.stage4.share.util.InvalidEmailException;

public interface MemberService {
	//
	public boolean register(MemberDto member) throws InvalidEmailException;

	public MemberDto find(String memberId);

	public List<MemberDto> findByName(String memberName);

	public void modify(MemberDto member) throws InvalidEmailException;

	public void remove(String memberId);
}