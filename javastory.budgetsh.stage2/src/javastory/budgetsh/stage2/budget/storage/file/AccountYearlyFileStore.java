package javastory.budgetsh.stage2.budget.storage.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;

import javastory.budgetsh.stage2.budget.entity.account.AccountYearlyDue;
import javastory.budgetsh.stage2.budget.share.IdName;
import javastory.budgetsh.stage2.budget.storage.store.AccountYearlyStore;
import javastory.budgetsh.stage2.budget.util.ResourceUtil;

public class AccountYearlyFileStore implements AccountYearlyStore {
	//
	private String folderName;
	private String fileName;
	private String delimiter;

	public AccountYearlyFileStore() {
		//
		this.folderName = "Budget";
		this.fileName = "Account.db";
		this.delimiter = "/";
	}

	private String convertToStr(AccountYearlyDue yearlyDue) {
		//
		StringBuilder builder = new StringBuilder();

		builder.append(yearlyDue.getMember().getId()).append(delimiter);
		builder.append(yearlyDue.getMember().getName()).append(delimiter);
		builder.append(yearlyDue.getYear());

		return builder.toString();
	}

	private AccountYearlyDue convertToObject(String line) {
		//
		AccountYearlyDue accountYearlyDue;

		StringTokenizer tokenizer = new StringTokenizer(line, delimiter);

		String memberAccount = tokenizer.nextToken();
		String memberName = tokenizer.nextToken();
		int year = Integer.parseInt(tokenizer.nextToken());

		accountYearlyDue = new AccountYearlyDue(new IdName(memberAccount, memberName), year);

		return accountYearlyDue;

	}

	public boolean exist(String account) {
		//
		BufferedReader reader;

		if (account == null) {
			return false;
		}

		try {
			String line;
			reader = requestReader();
			while (true) {
				line = reader.readLine();
				if (line == null) {
					break;
				}
				if (line.contains(account)) {
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void remove(String account) {
		//
		if (!this.exist(account)) {
			return;
		}
		RandomAccessFile writer;
		String line;

		int startPoint = 0;
		int endPoint;
		try {
			writer = requestWriter();

			while (true) {
				writer.seek(startPoint);
				line = writer.readLine();
				endPoint = (int) writer.getFilePointer();
				if (line == null) {
					break;
				}
				if (line.contains(account)) {
					for (int i = endPoint; i > startPoint; i--) {
						writer.seek(i);
						writer.writeByte('\b');
					}
					return;
				}
				startPoint = endPoint;
			}
			writer.close();
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
	}

	public void update(AccountYearlyDue yearlyDue) {
		//
		if (!this.exist(yearlyDue.getMember().getId())) {
			return;
		}
		this.remove(yearlyDue.getMember().getId());

		String newLine = convertToStr(yearlyDue);

		RandomAccessFile writer;
		int endPoint = 0;
		try {
			writer = requestWriter();

			endPoint = (int) writer.length();
			writer.seek(endPoint);
			writer.writeBytes(newLine);

			writer.close();
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	public AccountYearlyDue retrieve(String account) {
		//
		if (!exist(account)) {
			return null;
		}
		AccountYearlyDue foundAccountDue;
		
		BufferedReader reader;
		String line;
		try {
			reader = requestReader();
			while (true) {
				line = reader.readLine();
				if(line == null) {
					break;
				}
				if(line.contains(account)) {
					foundAccountDue = convertToObject(line);
					return foundAccountDue;
				}
			}
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		return null;
	}

	public void regist(AccountYearlyDue yearlyDue) {
		//
		if(this.exist(yearlyDue.getMember().getId())) {
			return;
		}
		
		RandomAccessFile writer;
		String newLine = convertToStr(yearlyDue);
		int endPoint;
		
		try {
			writer = requestWriter();
			
			endPoint = (int) writer.length();
			writer.seek(endPoint);
			writer.writeBytes(newLine);
			writer.writeBytes("\r\n");
			
			writer.close();
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

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
