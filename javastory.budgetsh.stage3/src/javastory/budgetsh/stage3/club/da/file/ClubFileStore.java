/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 24.
 */
package javastory.budgetsh.stage3.club.da.file;

import java.util.List;
import java.util.NoSuchElementException;

import javastory.budgetsh.stage3.club.da.file.io.AutoIdFile;
import javastory.budgetsh.stage3.club.da.file.io.AutoIdSequence;
import javastory.budgetsh.stage3.club.da.file.io.ClubFile;
import javastory.budgetsh.stage3.club.entity.AutoIdEntity;
import javastory.budgetsh.stage3.club.entity.club.TravelClub;
import javastory.budgetsh.stage3.club.service.dto.TravelClubDto;
import javastory.budgetsh.stage3.club.store.ClubStore;
import javastory.budgetsh.stage3.club.util.MemberDuplicationException;

public class ClubFileStore implements ClubStore {
	//
	private ClubFile clubFile; 
	private AutoIdFile autoIdFile; 
	
	public ClubFileStore() {
		//  
		this.clubFile = new ClubFile();
		this.autoIdFile = new AutoIdFile(); 
	}
	
	@Override
	public String create(TravelClub club) {
		// 
		if (clubFile.exists(club.getId())) {
			throw new MemberDuplicationException("Member already exists with email: " + club.getId()); 
		}
		
		if (club instanceof AutoIdEntity) {
			String className = TravelClub.class.getSimpleName(); 
			
			if(autoIdFile.read(className) == null) {
				autoIdFile.write(new AutoIdSequence(className));  
			}
			AutoIdSequence autoIdSequence = autoIdFile.read(className); 
			club.setAutoId(String.format("%05d", autoIdSequence.nextSequence()));  
			
			autoIdFile.update(autoIdSequence);
		}

		clubFile.write(club); 
		return club.getId();
	}

	@Override
	public TravelClub retrieve(String clubId) {
		// 
		return clubFile.read(clubId); 
	}
	
	@Override
	public TravelClub retrieveByName(String name) {
		//
		return clubFile.readByName(name); 
	}

	@Override
	public void update(TravelClub club) {
		// 
		if (!clubFile.exists(club.getId())) {
			throw new NoSuchElementException("No such a element: " + club.getId()); 
		}
		
		clubFile.update(club); 
	}

	@Override
	public void delete(String clubId) {
		// 
		clubFile.delete(clubId);
	}

	@Override
	public boolean exists(String clubId) {
		//
		return clubFile.exists(clubId);
	}
	
	public static void main(String[] args) {
		// 
		ClubFileStore store = new ClubFileStore(); 
		TravelClub club = TravelClub.getSample(false); 
		String clubId = store.create(club); 
		
		TravelClub readClub = store.retrieve(clubId);
		System.out.println("read club: " + readClub.toString()); 
	}

	@Override
	public List<TravelClub> retrieveAll() {
		//
		return clubFile.readAll();
	}
}