package javastory.budgetsh.stage4.client.fxui.pane;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javastory.budgetsh.stage4.client.dto.budget.CashBook;
import javastory.budgetsh.stage4.client.dto.budget.Transaction;
import javastory.budgetsh.stage4.client.dto.share.IdName;
import javastory.budgetsh.stage4.client.dto.tran.Expense;
import javastory.budgetsh.stage4.client.dto.tran.Forward;
import javastory.budgetsh.stage4.client.dto.tran.Revenue;
import javastory.budgetsh.stage4.client.dto.tran.TranItem;
import javastory.budgetsh.stage4.client.dto.tran.TranType;
import javastory.budgetsh.stage4.client.fxui.event.BudgetEventHelper;
import javastory.budgetsh.stage4.client.fxui.util.ConfirmBox;


public class TransactionPaneContainer {
	//
	private BudgetEventHelper budgetEvent;
	private Label title,type,amount, vat, memo, cashbookName;
	private TextField titleField, amountField, vatField, memoField;
	private Button addBtn, deleteBtn;
	
	private TableView<Transaction> transactionTableView;
	private ComboBox<String> cashbookComboBox;
	private ChoiceBox<String> typeChoice;
	
	public TransactionPaneContainer() {
		budgetEvent = new BudgetEventHelper();
	}
	
	public Pane showTransaction() {
		//
		title = new Label("Title");
		type = new Label("Type");
		amount = new Label("Amount");
		vat = new Label("Vat");
		memo = new Label("Memo");
		cashbookName = new Label("Cashbook Name");
		deleteBtn = new Button("Delete");

		titleField = new TextField();
		typeChoice = new ChoiceBox<>();
		amountField = new TextField();
		vatField = new TextField();
		memoField = new TextField();
		
		typeChoice.getItems().addAll(
				"Revenue",
				"Expense", 
				"Expenditure",
				"Forward");
		typeChoice.setValue("Expense");
		
		addBtn = new Button("Add");
		
		GridPane insertPane = new GridPane();
		insertPane.add(title, 0, 0);
		insertPane.add(type, 2, 0);
		insertPane.add(amount, 0, 1);
		insertPane.add(vat, 2, 1);
		insertPane.add(memo, 4, 1);
		
		insertPane.add(titleField, 1, 0);
		insertPane.add(typeChoice, 3, 0);
		insertPane.add(amountField, 1, 1);
		insertPane.add(vatField, 3, 1);
		insertPane.add(memoField, 5, 1);
		insertPane.add(addBtn, 8, 1);
		
		insertPane.setHgap(10);
		insertPane.setVgap(10);
		insertPane.setAlignment(Pos.CENTER);
		
		transactionTableView = new TableView<Transaction>();
		cashbookComboBox = new ComboBox<>();
		ArrayList<CashBook> foundCashbooks = budgetEvent.retrieveCashbook();
		ArrayList<String> cashbookNames = new ArrayList<>();
		for(CashBook cashbook : foundCashbooks) {
			cashbookNames.add(cashbook.getClub().getName());
		}
		cashbookComboBox.getItems().addAll(cashbookNames);
		cashbookComboBox.setValue(cashbookNames.iterator().next());
		
		TableColumn<Transaction, String> titleColumn = new TableColumn<>("Title");
		titleColumn.setMinWidth(130);
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		
		TableColumn<Transaction, IdName> accountColumn = new TableColumn<>("Account");
		accountColumn.setMinWidth(140);
		accountColumn.setCellValueFactory(new PropertyValueFactory<>("account"));
		
		TableColumn<Transaction, TranType> typeColumn = new TableColumn<>("Type");
		typeColumn.setMinWidth(80);
		typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
		
		TableColumn<Transaction, Expense> expenseColumn = new TableColumn<>("Expense");
		expenseColumn.setMinWidth(250);
		expenseColumn.setCellValueFactory(new PropertyValueFactory<>("expense"));		
		
		TableColumn<Transaction, String> memoColumn = new TableColumn<>("Memo");
		memoColumn.setMinWidth(100);
		memoColumn.setCellValueFactory(new PropertyValueFactory<>("memo"));
		
		TableColumn<Transaction, String> dateColumn = new TableColumn<>("Date");
		dateColumn.setMinWidth(90);
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		
		transactionTableView.getColumns().addAll(titleColumn, accountColumn, typeColumn, expenseColumn, memoColumn, dateColumn);
		
		String selectedCashbookName = cashbookComboBox.getValue();
		CashBook selectedCashbook = budgetEvent.retrieveOneCashbook(selectedCashbookName);
		ArrayList<Transaction> foundTransactions = new ArrayList<>();
		foundTransactions = budgetEvent.retrieveAllTransaction(selectedCashbook.getBankAccount());
		transactionTableView.setItems(setTransactionTableView(foundTransactions));
		

		HBox choiceBox = new HBox(15);
		choiceBox.setAlignment(Pos.BASELINE_RIGHT);
		choiceBox.getChildren().addAll(cashbookName, cashbookComboBox, deleteBtn);
		
		VBox mainBox = new VBox(10);
		mainBox.getChildren().addAll(insertPane, transactionTableView, choiceBox);
		mainBox.setPadding(new Insets(10));
		
		addBtn.setOnAction(e -> {
			String title = titleField.getText();
			String type = typeChoice.getValue();
			Double amount = Double.parseDouble(amountField.getText());
			Double vat = Double.parseDouble(vatField.getText());
			String memo = memoField.getText();
			String cashbookName = cashbookComboBox.getValue();
			String foundCashbookId = null;
			TranItem tranItem;
			
			switch(type) {
			case "Revenue":
				tranItem = new Revenue(amount,vat);
				break;
			case "Expense":
				tranItem = new Expense(amount,vat);
				break;
			case "Forward":
				tranItem = new Forward(amount,vat);
				break;
				
				default:
					tranItem = new Expense();
			}
			System.out.println(tranItem);
			
			ArrayList<CashBook> cashbooks = budgetEvent.retrieveCashbook();
			for(CashBook cashbook : cashbooks) {
				if(cashbook.getClub().getName().equals(cashbookName)) {
					foundCashbookId = cashbook.getBankAccount();
				}
			}
			Transaction transaction = new Transaction(foundCashbookId, title, new IdName(foundCashbookId,foundCashbookId),tranItem,UUID.randomUUID().toString());
			transaction.setMemo(memo);
			budgetEvent.addTransaction(transaction);
			
			ArrayList<Transaction> foundTransaction = new ArrayList<>();
			foundTransaction = budgetEvent.retrieveAllTransaction(selectedCashbook.getBankAccount());
			transactionTableView.setItems(setTransactionTableView(foundTransaction));
		});
		
		cashbookComboBox.setOnAction(e ->{
			CashBook cashbook = budgetEvent.retrieveOneCashbook(cashbookComboBox.getValue());
			ArrayList<Transaction> transaction = budgetEvent.retrieveAllTransaction(cashbook.getBankAccount());
			transactionTableView.setItems(setTransactionTableView(transaction));
		});
		
		deleteBtn.setOnAction(e ->{
			ObservableList<Transaction> selectedItem, allItem;
			allItem = transactionTableView.getItems();
			selectedItem = transactionTableView.getSelectionModel().getSelectedItems();

			if (ConfirmBox.display("DELETE", "sure to delete?")) {
				System.out.println(selectedItem);
				budgetEvent.removeTransaction(selectedItem.iterator().next());
				selectedItem.forEach(allItem::remove);
			}
		});
		
		return mainBox;
	}

	private ObservableList<Transaction> setTransactionTableView(List<Transaction> foundTransactions) {
		//
		ObservableList<Transaction> transactionList = FXCollections.observableArrayList();
		transactionList.addAll(foundTransactions);

		return transactionList;
	}
}
