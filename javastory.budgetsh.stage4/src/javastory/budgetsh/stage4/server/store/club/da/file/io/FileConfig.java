/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 24.
 */
package javastory.budgetsh.stage4.server.store.club.da.file.io;

public class FileConfig {
	//
	private static String DEFAULT_DELIMETER = "||"; 
	
	public static String getFileName(String target) {
		// 
		return target + "File.db"; 
	}
	
	public static String getTempFileName(String target) {
		// 
		return target + "TempFile.db"; 
	}
	
	public static String getDelimiter() {
		// 
		return DEFAULT_DELIMETER; 
	}
}