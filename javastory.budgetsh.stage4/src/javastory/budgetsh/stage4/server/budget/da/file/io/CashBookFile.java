package javastory.budgetsh.stage4.server.budget.da.file.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javastory.budgetsh.stage4.server.budget.entity.budget.CashBook;
import javastory.budgetsh.stage4.server.club.da.file.io.FileConfig;
import javastory.budgetsh.stage4.server.club.util.FileDbWrapper;

public class CashBookFile {
	//
	private FileDbWrapper cashbookFile;
	private FileDbWrapper cashbookTempFile;
	
	private static String defaultDelimeter = "/";
	
	private static Map<String, Integer> keyIndexMap; 
	
	static {
		keyIndexMap = new LinkedHashMap<String, Integer>();
		keyIndexMap.put("bankAccount", 0);
	}
	
	public CashBookFile() {
		//
		cashbookFile = new FileDbWrapper(
				"Budget",
				FileConfig.getFileName("CashBook"),
				FileConfig.getDelimiter());
		cashbookTempFile = new FileDbWrapper(
				"Budget",
				FileConfig.getFileName("CashBookTemp"),
				FileConfig.getDelimiter());
		
		cashbookFile.setKeyIndexMap(keyIndexMap);
		cashbookTempFile.setKeyIndexMap(keyIndexMap);
	}
	
	public boolean exist(String bankAccount) {
		// 
		boolean found = false;
		BufferedReader reader;

		String line;
		try {
			reader = cashbookFile.requestReader();
			while(!((line = reader.readLine()) == null)) {
				if(cashbookFile.hasValueOf("bankAccount",bankAccount,line)) {
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
	
	public CashBook retrieve(String bankAccount) {
		//
		CashBook foundCashBook = null;
		
		BufferedReader reader;
		String line;
		try {
			reader = cashbookFile.requestReader();
			while(!((line = reader.readLine()) == null)) {
				if(cashbookFile.hasValueOf("bankAccount",bankAccount, line)) {
					foundCashBook = this.convertToCashBook(line);
					break;
				}
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return foundCashBook;
	}

	public void remove(CashBook cashbook) {
		//
		String account = cashbook.getBankAccount();
		BufferedReader reader;
		PrintWriter writer;
		String line;
		
		try {
			reader = cashbookFile.requestReader();
			writer = cashbookTempFile.requestPrintWriter();
			while(!((line = reader.readLine()) == null)) {
				if(cashbookFile.hasValueOf("bankAccount",account,line)) {
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
		
		if(!cashbookFile.delete()) {
			System.out.println("failure to delete");
		}
		
		if(!cashbookTempFile.renameTo(cashbookFile)) {
			System.out.println("Failure to rename");
		}
	}

	public void update(CashBook cashbook) {
		//
		String bankAccount = cashbook.getBankAccount();
		BufferedReader reader;
		PrintWriter writer;
		
		try {
			reader = cashbookFile.requestReader();
			writer = cashbookTempFile.requestPrintWriter();
			
			String line;
			
			while(!((line = reader.readLine()) == null)) {
				if((cashbookFile.hasValueOf("bankAccount",bankAccount,line))) {
					writer.write(cashbookTempFile.convertFrom(cashbook));
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
		
		if(!cashbookFile.delete()) {
			System.out.println("failure to delete");
			return;
		}
		
		if(!cashbookTempFile.renameTo(cashbookFile)) {
			System.out.println("Failure to rename");
		}
	}

	public void regist(CashBook cashbook) {
		//
		Writer writer;
		try {
			writer = cashbookFile.requestPrintWriter();
			writer.write(cashbookFile.convertFrom(cashbook));
			writer.write("\r\n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private CashBook convertToCashBook(String line) {
		//
		return (CashBook) cashbookFile.convertTo(line, CashBook.class);
	}
	
	private String convertToLine(CashBook cashbook) {
		//
		return cashbookFile.convertFrom(cashbook);
	}

	public ArrayList<CashBook> retrieveAll() {
		//
		CashBook foundCashBook = null;
		ArrayList<CashBook> cashbookList = new ArrayList<CashBook>();
		
		BufferedReader reader;
		String line;
		try {
			reader = cashbookFile.requestReader();
			while(!((line = reader.readLine()) == null)) {
					foundCashBook = this.convertToCashBook(line);
					cashbookList.add(foundCashBook);
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cashbookList;
	}


}
