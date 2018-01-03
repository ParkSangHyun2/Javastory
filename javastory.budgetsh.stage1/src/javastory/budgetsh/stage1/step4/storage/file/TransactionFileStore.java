package javastory.budgetsh.stage1.step4.storage.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import javastory.budgetsh.stage1.step1.entity.budget.Transaction;
import javastory.budgetsh.stage1.step1.entity.budget.tran.Expense;
import javastory.budgetsh.stage1.step1.entity.budget.tran.Forward;
import javastory.budgetsh.stage1.step1.entity.budget.tran.Revenue;
import javastory.budgetsh.stage1.step1.share.IdName;
import javastory.budgetsh.stage1.step4.storage.store.TransactionStore;
import javastory.budgetsh.stage1.util.ResourceUtil;

public class TransactionFileStore implements TransactionStore {
	//
	private String folderName;
	private String fileName;
	private String delimiter;

	public TransactionFileStore() {
		//
		this.folderName = "Budget";
		this.fileName = "Transaction.db";
		this.delimiter = "/";
	}

	private String convertToStr(Transaction transaction) {
		StringBuilder builder = new StringBuilder();

		builder.append(transaction.getId()).append(delimiter);
		builder.append(transaction.getDate()).append(delimiter);
		builder.append(transaction.getTitle()).append(delimiter);
		builder.append(transaction.getAccount().getId()).append(delimiter);
		builder.append(transaction.getAccount().getName()).append(delimiter);
		builder.append(transaction.getItem().getClass().getSimpleName()).append(delimiter);
		builder.append(transaction.getItem().getAmount()).append(delimiter);
		builder.append(transaction.getItem().getVat()).append(delimiter);
		builder.append(transaction.getMemo()).append(delimiter);
		builder.append(transaction.getTime()).append(delimiter);
		builder.append(transaction.getCashBookId());

		System.out.println(builder.toString());
		return builder.toString();
	}

	private Transaction convertToObject(String line) {
		//
		Transaction transaction = null;

		StringTokenizer tokenizer = new StringTokenizer(line, delimiter);

		String id = tokenizer.nextToken();
		String date = tokenizer.nextToken();
		String title = tokenizer.nextToken();
		String accountId = tokenizer.nextToken();
		String accountName = tokenizer.nextToken();
		String itemName = tokenizer.nextToken();
		double itemAmount = Double.parseDouble(tokenizer.nextToken());
		double itemVat = Double.parseDouble(tokenizer.nextToken());
		String memo = tokenizer.nextToken();
		long time = Long.parseLong(tokenizer.nextToken());
		String cashbookId = tokenizer.nextToken();

		if (itemName.contains("Expense")) {
			transaction = new Transaction(cashbookId, title, new IdName(accountId, accountName),
					new Expense(itemAmount, itemVat), id);
		} else if (itemName.contains("Forward")) {
			transaction = new Transaction(cashbookId, title, new IdName(accountId, accountName),
					new Forward(itemAmount, itemVat), id);
		} else if (itemName.contains("Revenue")) {
			transaction = new Transaction(cashbookId, title, new IdName(accountId, accountName),
					new Revenue(itemAmount, itemVat), id);
		}
		transaction.setDate(date);
		transaction.setMemo(memo);
		transaction.setTime(time);

		return transaction;
	}

	public boolean register(Transaction transaction) {
		//
		String id = transaction.getId();

		RandomAccessFile writer;

		int endPoint = 0;

		if (exist(id)) {
			return false;
		}

		try {
			writer = requestWriter();

			endPoint = (int) writer.length();
			writer.seek(endPoint);

			writer.writeBytes(convertToStr(transaction));
			writer.writeBytes("\r\n");
			writer.close();
			return true;
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		return false;
	}

	public boolean exist(String id) {
		//
		BufferedReader reader;

		String line;
		try {
			reader = requestReader();

			while (true) {
				//
				line = reader.readLine();
				if (line == null)
					break;

				if (line.contains(id)) {
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public Transaction retrieve(String id, String cashbookId) {
		//
		BufferedReader reader;
		String line;

		Transaction foundTransaction = null;

		try {
			reader = requestReader();

			while (true) {
				line = reader.readLine();
				if (line == null || (line.length()<10)) {
					break;
				}
				if (line.contains(id)) {
					foundTransaction = convertToObject(line);
					break;
				}
			}
			if (foundTransaction.getCashBookId().equals(cashbookId)) {
				return foundTransaction;
			}
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		return null;
	}

	public Collection<Transaction> retrieveAll(String cashbookId) {
		//
		List<Transaction> transactionList = new ArrayList<>();
		Transaction foundTransaction;

		BufferedReader reader;
		String line;

		try {
			reader = requestReader();
			while (true) {
				line = reader.readLine();
				System.out.println(line.length());
				if (line == null || (line.length()<10)) {
					break;
				}
				foundTransaction = convertToObject(line);
				if (foundTransaction.getCashBookId().equals(cashbookId)) {
					transactionList.add(convertToObject(line));
				}
			}

		} catch (IOException e) {
			//
			e.printStackTrace();
			return null;
		}

		return transactionList;
	}

	public void update(Transaction transaction) {
		//
		remove(transaction.getId());

		RandomAccessFile writer;

		int startPoint = 0;

		try {
			writer = requestWriter();
			startPoint = (int) writer.length();

			writer.seek(startPoint);
			writer.writeBytes(convertToStr(transaction));
			writer.writeBytes("\r\n");
			writer.close();
		} catch (IOException e) {
			//
			e.printStackTrace();
			return;
		}

	}

	public void remove(String id) {
		//
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
