package javastory.budgetsh.stage3.budget.da.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import javastory.budgetsh.stage3.budget.entity.account.DuesType;
import javastory.budgetsh.stage3.budget.entity.account.MonthlyDue;
import javastory.budgetsh.stage3.budget.share.IdName;
import javastory.budgetsh.stage3.budget.store.AccountMonthlyStore;
import javastory.budgetsh.stage3.budget.util.ResourceUtil;

public class AccountMonthlyFileStore implements AccountMonthlyStore {
	//
	private String folderName;
	private String fileName;
	private String delimiter;

	public AccountMonthlyFileStore() {
		//
		this.folderName = "Budget";
		this.fileName = "MonthlyAccount.db";
		this.delimiter = "/";
	}

	private String convertToStr(MonthlyDue monthlyDue) {
		//
		StringBuilder builder = new StringBuilder();

		builder.append(monthlyDue.getCurrencyCode()).append(delimiter);
		builder.append(monthlyDue.getMonth()).append(delimiter);
		// if (monthlyDue.getType().getClass().getSimpleName().equals("Regular")) {
		builder.append(monthlyDue.getType().getClass().getSimpleName()).append(delimiter);
		builder.append(monthlyDue.getAmount()).append(delimiter);
		builder.append(monthlyDue.getTime()).append(delimiter);
		builder.append(monthlyDue.getTravel().getId()).append(delimiter);
		builder.append(monthlyDue.getTravel().getName());

		return builder.toString();
	}

	private MonthlyDue convertToObject(String line) {
		//
		StringTokenizer tokenizer = new StringTokenizer(line, delimiter);
		MonthlyDue monthlyDue;
		DuesType type = DuesType.Regular;

		String currencyCode = tokenizer.nextToken();
		int month = Integer.parseInt(tokenizer.nextToken());
		String typeName = tokenizer.nextToken();
		if (typeName.equals("Regular")) {
			type = DuesType.Regular;
		} else if (typeName.equals("Donation")) {
			type = DuesType.Donation;
		} else if (typeName.equals("Fine")) {
			type = DuesType.Fine;
		}
		Double amount = Double.parseDouble(tokenizer.nextToken());
		Long time = Long.parseLong(tokenizer.nextToken());
		String travelId = tokenizer.nextToken();
		String travelName = tokenizer.nextToken();

		monthlyDue = new MonthlyDue(month, amount, new IdName(travelId, travelName));
		monthlyDue.setAmount(amount);
		monthlyDue.setTime(time);
		monthlyDue.setType(type);
		monthlyDue.setCurrencyCode(currencyCode);

		return monthlyDue;
	}

	public Collection<MonthlyDue> findAll(String yearlyDueAccount) {
		//
		BufferedReader reader = null;

		try {
			reader = requestReader();
		} catch (IOException e1) {
			//
			e1.printStackTrace();
		}
		String line;
		List<MonthlyDue> foundList = new ArrayList<>();

		for (int i = 1; i < 13; i++) {
			if (this.exist(i, yearlyDueAccount)) {
				while (true) {
					try {
						line = reader.readLine();
						if (line == null)
							break;
						if (line.contains(yearlyDueAccount)) {
							foundList.add(convertToObject(line));
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if (foundList.isEmpty()) {
			return null;
		}
		return foundList;
	}

	public void update(String yearlyDueAccount, MonthlyDue monthlyDue) {
		//
		RandomAccessFile writer;
		String targetMonth = "/"+monthlyDue.getMonth()+"/";
		String line;
		int startPoint =0;
		int endPoint;
		
		if(!exist(monthlyDue.getMonth(),yearlyDueAccount)) {
			return;
		}
		
		try {
			writer = requestWriter();
			
			while(true) {
				writer.seek(startPoint);
				line = writer.readLine();
				endPoint = (int) writer.getFilePointer();
				if(line == null) {
					break;
				}
				if(line.contains(targetMonth) && line.contains(yearlyDueAccount)) {
					remove(yearlyDueAccount,monthlyDue.getMonth());
					writer.seek(writer.length());
					writer.writeBytes(convertToStr(monthlyDue));
					writer.writeBytes("\r\n");
					break;
				}
				startPoint = endPoint;
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void remove(String yearlyDueAccount, int month) {
		//
		RandomAccessFile writer;
		
		String targetMonth = "/"+month+"/";
		int startPoint =0;
		int endPoint;
		String line;
		
		try {
			writer = requestWriter();
			while(true) {
				writer.seek(startPoint);
				line = writer.readLine();
				endPoint = (int) writer.getFilePointer();
				if(line == null) {
					return;
				}
				if(line.contains(yearlyDueAccount) && line.contains(targetMonth)) {
					for(int i=endPoint; i>startPoint; i--) {
						writer.seek(i);
						writer.writeByte('\b');
					}
					break;
				}
				startPoint = endPoint;
			}
			writer.close();
		} catch (IOException e) {
			//
			e.printStackTrace();
			return;
		}
	}
	
	@Override
	public MonthlyDue retrieve(String yearlyDueAccount, int month) {
		//
		BufferedReader reader;
		
		MonthlyDue monthlyDue;
		String line;
		String matchMonth = "/" + month + "/";

		try {
			reader = requestReader();

			while (true) {
				line = reader.readLine();
				if (line == null) {
					break;
				}
				if (line.contains(matchMonth) && line.contains(yearlyDueAccount)) {
					monthlyDue = convertToObject(line);
					return monthlyDue;
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public void register(MonthlyDue targetMonthlyDue) {
		//
		int month = targetMonthlyDue.getMonth();
		String account = targetMonthlyDue.getTravel().getId();
		
		RandomAccessFile writer;
		
		if(exist(month,account)) {
			return;
		}
		
		try {
			writer = requestWriter();
			writer.seek(writer.length());
			writer.writeBytes(convertToStr(targetMonthlyDue));
			writer.writeBytes("\r\n");
			writer.close();
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
	}

	public boolean exist(int month, String account) {
		//
		BufferedReader reader;
		String line;
		String matchMoth = "/" + month + "/";

		try {
			reader = requestReader();

			while (true) {
				line = reader.readLine();
				if (line == null) {
					break;
				}
				if (line.contains(matchMoth) && line.contains(account)) {
					return true;
				}
			}
			reader.close();
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		return false;
	}

	private BufferedReader requestReader() throws IOException {
		//
		return new BufferedReader(new FileReader(create()));
	}

	private RandomAccessFile requestWriter() throws IOException {
		//
		return new RandomAccessFile(create(), "rw");
	}

	private File create() throws IOException {
		//
		return ResourceUtil.getFile("filedb", folderName, fileName);
	}



}
