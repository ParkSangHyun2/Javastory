package javastory.budgetsh.stage4.server.budget.da.file.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javastory.budgetsh.stage4.server.budget.entity.budget.Transaction;
import javastory.budgetsh.stage4.server.club.da.file.io.FileConfig;
import javastory.budgetsh.stage4.server.club.util.FileDbWrapper;

public class TransactionFile {
	//
	private static String defaultDelimiter = "/";
	private FileDbWrapper transactionFile;
	private FileDbWrapper transactionTempFile;
	
	private static HashMap<String, Integer> keyIndexMap;
	
	static {
		keyIndexMap = new LinkedHashMap<String, Integer>();
		keyIndexMap.put("id", 0);
		keyIndexMap.put("cashBookId", 1);
	}
	
	public TransactionFile(){
		//
		transactionFile = new FileDbWrapper(
				"Budget",
				FileConfig.getFileName("Transaction"),
				FileConfig.getDelimiter()
				);
		
		transactionTempFile = new FileDbWrapper(
				"Budget",
				FileConfig.getFileName("TransactionTemp"),
				FileConfig.getDelimiter()
				);
		
		transactionFile.setKeyIndexMap(keyIndexMap);
		transactionTempFile.setKeyIndexMap(keyIndexMap);
	}

	public Transaction read(String id, String cashBookId) {
		//
		Transaction foundTransaction = null;
		BufferedReader reader;
		
		String line;
		try {
			reader = transactionFile.requestReader();
			while(!((line = reader.readLine()) == null)) {
				if(transactionFile.hasValueOf("id", id, line) && transactionFile.hasValueOf("cashBookId", cashBookId, line)) {
					foundTransaction = this.convertToTransaction(line);
				}
			}
			reader.close();
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		return foundTransaction;
	}


	public void write(Transaction transaction) {
		//
		PrintWriter writer;
		
		try {
			writer = transactionFile.requestPrintWriter();
			writer.write(transactionFile.convertFrom(transaction));
			writer.write("\r\n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean exist(String id) {
		//
		boolean found = false;
		System.out.println("Fild " +id);
		BufferedReader reader;
		String line;
		try {
			reader = transactionFile.requestReader();
			while(!((line = reader.readLine()) == null)) {
				System.out.println("Line : "+line);
				System.out.println("Id : " + id);
				if(transactionFile.hasValueOf("id", id, line)) {
					found = true;
					break;
				}
			}
			reader.close();
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		return found;
	}

	public void remove(String id) {
		//
		BufferedReader reader;
		PrintWriter writer;
		
		String line;
		
		try {
			reader = transactionFile.requestReader();
			writer = transactionTempFile.requestPrintWriter();
			
			while(!((line= reader.readLine()) == null)) {
				if(transactionFile.hasValueOf("id", id, line)) {
					continue;
				}
				writer.write(line);
				writer.write("\r\n");
			}
			writer.close();
			reader.close();
			
			if(!transactionFile.delete()) {
				System.out.println("failure to delete");
			}
			
			if(!transactionTempFile.renameTo(transactionFile)) {
				System.out.println("Failure to Rename");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void update(Transaction transaction) {
		// 
		String transactionId = transaction.getId();
		BufferedReader reader;
		PrintWriter writer;
		
		String line;
		
		try {
			reader = transactionFile.requestReader();
			writer = transactionTempFile.requestPrintWriter();
			
			while(!((line= reader.readLine()) == null)) {
				if(transactionFile.hasValueOf("id", transactionId, line)) {
					writer.write(transactionFile.convertFrom(transaction));
					writer.write("\r\n");
				}else {
					writer.write(line);
					writer.write("\r\n");
				}

			}
			writer.close();
			reader.close();
			
			if(!transactionFile.delete()) {
				System.out.println("failure to delete");
			}
			
			if(!transactionTempFile.renameTo(transactionFile)) {
				System.out.println("Failure to Rename");
			}
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}
	
	private Transaction convertToTransaction(String line) {
		return (Transaction)transactionFile.convertTo(line,Transaction.class);
	}

	public Collection<Transaction> retrieveAll(String cashBookId) {
		//
		List<Transaction> allTransaction = new ArrayList<>();
		BufferedReader reader;
		String line;
		try {
			reader = transactionFile.requestReader();
			while(!((line = reader.readLine()) == null)) {
				if(transactionFile.hasValueOf("cashBookId", cashBookId, line)) {
					allTransaction.add(this.convertToTransaction(line));
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		return allTransaction;
	}
}
