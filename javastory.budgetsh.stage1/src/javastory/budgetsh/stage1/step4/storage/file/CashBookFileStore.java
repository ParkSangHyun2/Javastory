package javastory.budgetsh.stage1.step4.storage.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javastory.budgetsh.stage1.step1.entity.budget.CashBook;
import javastory.budgetsh.stage1.step1.entity.travel.Travel;
import javastory.budgetsh.stage1.step1.share.DatePair;
import javastory.budgetsh.stage1.step1.share.IdName;
import javastory.budgetsh.stage1.step1.share.Socialian;
import javastory.budgetsh.stage1.step4.storage.store.CashBookStore;
import javastory.budgetsh.stage1.util.ResourceUtil;

public class CashBookFileStore implements CashBookStore {
	//
	private String folderName;
	private String fileName;
	private String delimiter;

	public CashBookFileStore() {
		//
		this.folderName = "Budget";
		this.fileName = "CashBook.db";
		this.delimiter = "/";
	}
	
	private String convertToStr(CashBook cashbook) {
		//
		StringBuilder builder = new StringBuilder();

		builder.append(cashbook.getId()).append(delimiter);
		builder.append(cashbook.getBankAccount()).append(delimiter);
		builder.append(cashbook.getCurrencyCode()).append(delimiter);
		builder.append(cashbook.getMonthlyDue()).append(delimiter);
		builder.append(cashbook.getBudgetGoal()).append(delimiter);
		builder.append(cashbook.getTerm().getStartDate()).append(delimiter);
		builder.append(cashbook.getTerm().getEndDate()).append(delimiter);
		builder.append(cashbook.getMemo()).append(delimiter);
		builder.append(cashbook.getTime()).append(delimiter);
		builder.append(cashbook.getTravel().getId()).append(delimiter);
		builder.append(cashbook.getTravel().getName()).append(delimiter);
		builder.append(cashbook.getClub().getId()).append(delimiter);
		builder.append(cashbook.getClub().getName()).append(delimiter);
		builder.append(cashbook.getLeader().getSocialId()).append(delimiter);
		builder.append(cashbook.getLeader().getFirstName()).append(delimiter);
		builder.append(cashbook.getLeader().getFamilyName()).append(delimiter);
		builder.append(cashbook.getLeader().getEmail()).append(delimiter);
		builder.append(cashbook.getLeader().getPhone()).append(delimiter);

		return builder.toString();
	}

	private CashBook convertToObject(String line) throws NoSuchElementException {
		//
		StringTokenizer tokenizer = new StringTokenizer(line, delimiter);
		CashBook cashbook;
		try {
			String id = tokenizer.nextToken();
			String bankAccount = tokenizer.nextToken();
			String currencyCode = tokenizer.nextToken();
			double monthlyDue = Double.parseDouble(tokenizer.nextToken());
			double budgetGoal = Double.parseDouble(tokenizer.nextToken());
			String startDate = tokenizer.nextToken();
			String endDate = tokenizer.nextToken();
			String memo = tokenizer.nextToken();
			long time = Long.parseLong(tokenizer.nextToken());
			String travelId = tokenizer.nextToken();
			String travelName = tokenizer.nextToken();
			String clubId = tokenizer.nextToken();
			String clubName = tokenizer.nextToken();
			String socialId = tokenizer.nextToken();
			String firstName = tokenizer.nextToken();
			String familyName = tokenizer.nextToken();
			String email = tokenizer.nextToken();
			String phone = tokenizer.nextToken();
			
			Socialian socialian = new Socialian(socialId, firstName, familyName, email);
			socialian.setEmail(email);
			socialian.setPhone(phone);

			Travel travel = new Travel(new IdName(travelId, travelName), travelName, socialian,
					new DatePair(startDate, endDate));

			cashbook = new CashBook(travel, monthlyDue, budgetGoal, new DatePair(startDate, endDate),id);
			cashbook.setMemo(memo);
			cashbook.setTime(time);
			cashbook.setCurrencyCode(currencyCode);
			cashbook.setBankAccount(bankAccount);
			cashbook.setClub(new IdName(clubId, clubName));
			
			return cashbook;
		} catch (NoSuchElementException e) {
			
		}
		return null;
	}

	public boolean exist(String bankAccount) {
		//
		BufferedReader reader;
		boolean isExist = false;

		try {
			reader = requestReader();

			String line = null;
			while (true) {
				line = reader.readLine();
				if (line == null) {
					break;
				}
				if (line.contains(bankAccount)) {
					isExist = true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isExist;
	}

	public CashBook retrieve(String bankAccount) {
		//
		if (!exist(bankAccount)) {
			return null;
		}
		String line;

		try {
			BufferedReader reader = requestReader();

			while (true) {
				line = reader.readLine();
				if (line == null) {
					break;
				}
				if (line.contains(bankAccount)) {
					return convertToObject(line);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void remove(CashBook foundCashbook) {
		//
		if (!exist(foundCashbook.getBankAccount())) {
			return;
		}
		String id = foundCashbook.getBankAccount();
		RandomAccessFile writer = null;

		String line = null;
		int startPoint = 0;
		int endPoint = 0;

		try {
			writer = requestWriter();

			while (true) {
				writer.seek(startPoint);
				line = writer.readLine();
				if (line == null) {
					break;
				}

				endPoint = (int) writer.getFilePointer();
				if (line.contains(id)) {
					for (int i = endPoint; i > startPoint; i--) {
						writer.seek(i);
						writer.writeByte('\b');
					}
				}
				startPoint = endPoint;
			}
			writer.close();
		} catch (IOException e) {
			//
			e.printStackTrace();
			try {
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	public void update(CashBook foundCashbook) {
		//
		RandomAccessFile writer;

		int endPoint = 0;
		try {
			remove(foundCashbook);

			writer = requestWriter();
			endPoint = (int) writer.length();
			writer.seek(endPoint);
			writer.writeBytes(convertToStr(foundCashbook));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public CashBook regist(CashBook cashbook) {
		//
		if (exist(cashbook.getBankAccount())) {
			return null;
		}

		try {
			RandomAccessFile writer = requestWriter();
			int endPoint = (int) writer.length();

			writer.seek(endPoint);

			writer.writeBytes(convertToStr(cashbook));
			writer.writeBytes("\r\n");
			writer.close();
		} catch (IOException e) {
			//
			e.printStackTrace();
			return null;
		}
		return cashbook;

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
		return ResourceUtil.getFile("fileStorage", folderName, fileName);
	}
}
