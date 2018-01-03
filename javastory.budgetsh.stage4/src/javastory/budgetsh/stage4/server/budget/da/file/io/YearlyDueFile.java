package javastory.budgetsh.stage4.server.budget.da.file.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javastory.budgetsh.stage4.server.budget.entity.account.AccountYearlyDue;
import javastory.budgetsh.stage4.server.club.da.file.io.FileConfig;
import javastory.budgetsh.stage4.server.club.util.FileDbWrapper;

public class YearlyDueFile {
	//
	private FileDbWrapper yearlyDueFile;
	private FileDbWrapper yearlyDueTempFile;
	
	private static String defaultDelimeter = "/";
	
	private static Map<String, Integer> keyIndexMap; 
	
	static {
		keyIndexMap = new LinkedHashMap<String, Integer>();
		keyIndexMap.put("bankAccount", 0);
		keyIndexMap.put("year", 1);
	}
	
	public YearlyDueFile() {
		//
		yearlyDueFile = new FileDbWrapper(
				"Budget",
				FileConfig.getFileName("YearlyDue"),
				FileConfig.getDelimiter());
		yearlyDueTempFile = new FileDbWrapper(
				"Budget",
				FileConfig.getFileName("YearlyDueTemp"),
				FileConfig.getDelimiter());
		
		yearlyDueFile.setKeyIndexMap(keyIndexMap);
		yearlyDueTempFile.setKeyIndexMap(keyIndexMap);
	}

	public boolean exist(String account) {
		boolean found = false;
		BufferedReader reader;

		String line;
		try {
			reader = yearlyDueFile.requestReader();
			while(!((line = reader.readLine()) == null)) {
				System.out.println(line +" : " + account);
				if(yearlyDueFile.hasValueOf("bankAccount",account,line)) {
					found =true;
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return found;
	}

	public void remove(String account) {
		//
		BufferedReader reader;
		PrintWriter writer;
		String line;
		
		try {
			reader = yearlyDueFile.requestReader();
			writer = yearlyDueTempFile.requestPrintWriter();
			while(!((line = reader.readLine()) == null)) {
				if(yearlyDueFile.hasValueOf("bankAccount",account,line)) {
					continue;
				}
				writer.write(line);
				writer.write("\r\n");
				
				writer.close();
				reader.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!yearlyDueFile.delete()) {
			System.out.println("failure to delete");
		}
		
		if(!yearlyDueTempFile.renameTo(yearlyDueFile)) {
			System.out.println("Failure to rename");
		}
		
	}

	public void update(AccountYearlyDue yearlyDue) {
		//
		String bankAccount;//
		BufferedReader reader;
		PrintWriter writer;
		
		try {
			reader = yearlyDueFile.requestReader();
			writer = yearlyDueTempFile.requestPrintWriter();
			
			String line;
			
			StringTokenizer tokenizer;
			
			while(!((line = reader.readLine()) == null)) {
				tokenizer = new StringTokenizer(line, "||");
				bankAccount = tokenizer.nextToken().toString();
				if((yearlyDueFile.hasValueOf("bankAccount",bankAccount,line))) {
					writer.write(yearlyDueTempFile.convertFrom(yearlyDue));
					writer.write("\r\n");
				}else {
					writer.write(line);
					writer.write("\r\n");
				}
			}
			writer.close();
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!yearlyDueFile.delete()) {
			System.out.println("failure to delete");
			return;
		}
		
		if(!yearlyDueTempFile.renameTo(yearlyDueFile)) {
			System.out.println("Failure to rename");
		}
	}

	public AccountYearlyDue retrieve(String account) {
		// 
		AccountYearlyDue yearlyDue = null;
		
		BufferedReader reader;
		String line;
		try {
			reader = yearlyDueFile.requestReader();
			while(!((line = reader.readLine()) == null)) {
				if(yearlyDueFile.hasValueOf("bankAccount",account, line)) {
					yearlyDue = this.convertToYearlyDue(line);
					break;
				}
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return yearlyDue;
	}

	public void regist(AccountYearlyDue yearlyDue) {
		//
		Writer writer;
		try {
			writer = yearlyDueFile.requestPrintWriter();
			writer.write(yearlyDueFile.convertFrom(yearlyDue));
			writer.write("\r\n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Collection<AccountYearlyDue> retrieveAll(String account) {
		//
		System.out.println("In retrieveAll");
		AccountYearlyDue yearlyDue = null;
		
		List<AccountYearlyDue> list = new ArrayList<AccountYearlyDue>();
		BufferedReader reader;
		String line;
		try {
			reader = yearlyDueFile.requestReader();
			while(!((line = reader.readLine()) == null)) {
				if(yearlyDueFile.hasValueOf("bankAccount",account, line)) {
					System.out.println(line +" : " + account);
					list.add(this.convertToYearlyDue(line));
					break;
				}
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	private AccountYearlyDue convertToYearlyDue(String line) {
		//
		return (AccountYearlyDue) yearlyDueFile.convertTo(line, AccountYearlyDue.class);
	}
	
	private String convertToLine(AccountYearlyDue accountYearlyDue) {
		//
		return yearlyDueFile.convertFrom(accountYearlyDue);
	}
}
