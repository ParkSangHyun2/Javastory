package javastory.budgetsh.stage1.step1.entity.budget;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import javastory.budgetsh.stage1.step1.entity.budget.tran.Revenue;
import javastory.budgetsh.stage1.step1.entity.budget.tran.TranItem;
import javastory.budgetsh.stage1.step1.share.Entity;
import javastory.budgetsh.stage1.step1.share.IdName;
import javastory.budgetsh.stage1.step1.share.Socialian;
import javastory.budgetsh.stage1.step4.storage.store.TransactionStore;

public class Transaction extends Entity {
	//Entity key
	private String date; 
	private String title;
	private IdName account; 
	private TranItem item; 
	private String memo; 
	private long time; 
	
	private String cashBookId; 
	 
	public Transaction() {
		// 
	}
	
	public Transaction(String id) {
		// 
		super(id); 
	}
	
	public Transaction(String cashBookId, String title, IdName account, TranItem item,String id) {
		// 
		super(id); 
		this.date = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
		//this.date = new DateUtil().today().toString();
		this.title = title; 
		this.account = account; 
		this.item = item;
		this.time = System.currentTimeMillis(); 
		this.cashBookId = cashBookId; 
	}

	public String getCashBookId() {
		return cashBookId;
	}

	public void setCashBookId(String cashBookId) {
		this.cashBookId = cashBookId;
	}

	@Override
	public String toString() {
		// 
		StringBuilder builder = new StringBuilder(); 
		
		builder.append("date:").append(date); 
		builder.append(", title:").append(title); 
		builder.append(", account:").append(account); 
		builder.append(", item:").append(item); 
		builder.append(", memo:").append(memo); 
		builder.append(", time:").append(time); 
		builder.append(", cashBookId:").append(cashBookId); 
		
		return builder.toString(); 
	}
	
	public static Transaction getSample() {
		// 
		String cashBookId = CashBook.getSample().getId(); 
		String title = "회비납부"; 
		IdName account = Socialian.getSample().getIdName();
		TranItem item = Revenue.getSample(); 
		String tranId = "001";
		
		Transaction sample = new Transaction(cashBookId, title, account, item,tranId); 
		String date = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
		sample.setDate(date);
		
		return sample; 
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public IdName getAccount() {
		return account;
	}

	public void setAccount(IdName account) {
		this.account = account;
	}

	public TranItem getItem() {
		return item;
	}

	public void setItem(TranItem item) {
		this.item = item;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	public static void main(String[] args) {
		// 
		System.out.println(getSample());
	}
}
