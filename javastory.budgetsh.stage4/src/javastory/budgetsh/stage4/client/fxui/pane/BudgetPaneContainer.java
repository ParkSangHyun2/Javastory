package javastory.budgetsh.stage4.client.fxui.pane;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javastory.budgetsh.stage4.client.fxui.event.BudgetEventHelper;
import javastory.budgetsh.stage4.client.fxui.event.ClubEventHelper;
import javastory.budgetsh.stage4.client.fxui.util.AlertBox;
import javastory.budgetsh.stage4.client.fxui.util.ConfirmBox;
import javastory.budgetsh.stage4.share.domain.budget.budget.CashBook;
import javastory.budgetsh.stage4.share.domain.club.dto.TravelClubDto;



public class BudgetPaneContainer {
	//
	private TableView<CashBook> cashbookTableView;
	private Button modifyBtn, deleteBtn, createBtn, newBtn, modifyConfirmBtn;
	private Label title, travelName, club, leader, account, currencyCode, monthlyDue, budgetGoal, startDate, endDate,
			memo;
	private TextField travelNameField, clubField, leaderField, accountField, currencyCodeField, monthlyDueField,
			budgetGoalField, startDateField, endDateField, memoField;

	private ComboBox<String> clubComboBox;

	private BudgetEventHelper budgetEvent;
	private ClubEventHelper clubEvent;

	public BudgetPaneContainer() {
		budgetEvent = new BudgetEventHelper();
		clubEvent = new ClubEventHelper();
	}

	public Pane showCashBook() {
		//
		cashbookTableView = new TableView<CashBook>();

		TableColumn<CashBook, String> nameColumn = new TableColumn<>("Club Name");
		nameColumn.setMinWidth(120);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("club"));
		nameColumn.setStyle("-fx-alignment: CENTER-RIGHT;");

		TableColumn<CashBook, String> accountColumn = new TableColumn<>("Bank account");
		accountColumn.setMinWidth(140);
		accountColumn.setCellValueFactory(new PropertyValueFactory<>("bankAccount"));
		accountColumn.setStyle("-fx-alignment: CENTER-RIGHT;");

		TableColumn<CashBook, String> budgetGoalColumn = new TableColumn<>("Budget Goal");
		budgetGoalColumn.setMinWidth(100);
		budgetGoalColumn.setCellValueFactory(new PropertyValueFactory<>("budgetGoal"));
		budgetGoalColumn.setStyle("-fx-alignment: CENTER-RIGHT;");

		cashbookTableView.getColumns().addAll(nameColumn, accountColumn, budgetGoalColumn);

		ArrayList<CashBook> cashbookList = budgetEvent.retrieveCashbook();

		cashbookTableView.setItems(setCashbookTableView(cashbookList));

		modifyBtn = new Button("Modify");
		modifyBtn.setDisable(true);
		modifyConfirmBtn = new Button("Confirm");
		modifyConfirmBtn.setVisible(false);
		StackPane modifyBtns = new StackPane();
		modifyBtns.getChildren().addAll(modifyBtn, modifyConfirmBtn);
		deleteBtn = new Button("Delete");

		HBox btnBox = new HBox(10);
		btnBox.getChildren().addAll(modifyBtns, deleteBtn);
		btnBox.setAlignment(Pos.BOTTOM_RIGHT);

		VBox listBox = new VBox(10);
		Label cashbookLabel = new Label("CashBook List");
		cashbookLabel.setFont(Font.font(20));
		listBox.getChildren().addAll(cashbookLabel, cashbookTableView, btnBox);

		title = new Label("CashBook Detail");
		title.setFont(Font.font(20));
		StackPane titleStack = new StackPane();
		titleStack.getChildren().add(title);

		travelName = new Label("TravelName");
		club = new Label("Club");
		leader = new Label("Leader");
		account = new Label("BankAccount");
		currencyCode = new Label("CurrencyCode");
		monthlyDue = new Label("MonthlyDue");
		budgetGoal = new Label("BudgetGoal");
		startDate = new Label("StartDate");
		endDate = new Label("EndDate");
		memo = new Label("Memo");

		travelNameField = new TextField();
		clubField = new TextField();
		clubComboBox = new ComboBox<String>();
		leaderField = new TextField();
		accountField = new TextField();
		currencyCodeField = new TextField();
		monthlyDueField = new TextField();
		budgetGoalField = new TextField();
		startDateField = new TextField();
		endDateField = new TextField();
		memoField = new TextField();

		ArrayList<TravelClubDto> clubList = clubEvent.findAll();
		ArrayList<String> clubNames = new ArrayList<>();
		for (TravelClubDto foundClub : clubList) {
			clubNames.add(foundClub.getName());
		}
		clubComboBox.getItems().addAll(clubNames);
		clubComboBox.setVisible(false);
		clubComboBox.setMinWidth(200);
		clubComboBox.setMaxWidth(200);

		travelNameField.setDisable(true);
		clubField.setDisable(true);
		leaderField.setDisable(true);
		accountField.setDisable(true);
		currencyCodeField.setDisable(true);
		monthlyDueField.setDisable(true);
		budgetGoalField.setDisable(true);
		startDateField.setDisable(true);
		endDateField.setDisable(true);
		memoField.setDisable(true);

		StackPane clubPane = new StackPane();
		clubPane.getChildren().addAll(clubField, clubComboBox);

		GridPane fieldPane = new GridPane();
		fieldPane.add(travelName, 0, 0);
		fieldPane.add(club, 0, 1);
		fieldPane.add(leader, 0, 2);
		fieldPane.add(account, 0, 3);
		fieldPane.add(currencyCode, 0, 4);
		fieldPane.add(monthlyDue, 0, 5);
		fieldPane.add(budgetGoal, 0, 6);
		fieldPane.add(startDate, 0, 7);
		fieldPane.add(endDate, 0, 8);
		fieldPane.add(memo, 0, 9);
		fieldPane.add(travelNameField, 1, 0);
		fieldPane.add(clubPane, 1, 1);
		fieldPane.add(leaderField, 1, 2);
		fieldPane.add(accountField, 1, 3);
		fieldPane.add(currencyCodeField, 1, 4);
		fieldPane.add(monthlyDueField, 1, 5);
		fieldPane.add(budgetGoalField, 1, 6);
		fieldPane.add(startDateField, 1, 7);
		fieldPane.add(endDateField, 1, 8);
		fieldPane.add(memoField, 1, 9);
		fieldPane.setHgap(5);
		fieldPane.setVgap(14.48);

		newBtn = new Button("new CashBook");
		createBtn = new Button("create");
		createBtn.setVisible(false);
		StackPane createStack = new StackPane();
		createStack.getChildren().addAll(newBtn, createBtn);
		createStack.setAlignment(Pos.BASELINE_RIGHT);
		createStack.setPadding(new Insets(4));

		VBox fieldBox = new VBox(10);
		fieldBox.getChildren().addAll(titleStack, fieldPane, createStack);

		HBox mainBox = new HBox(15);
		mainBox.getChildren().addAll(listBox, fieldBox);
		mainBox.setPadding(new Insets(10));

		cashbookTableView.setOnMouseClicked(e -> {
			modifyBtn.setDisable(false);
			ObservableList<CashBook> selectedItem;
			selectedItem = cashbookTableView.getSelectionModel().getSelectedItems();
			CashBook selectedCashbook = selectedItem.iterator().next();
			System.out.println(selectedCashbook);

			travelNameField.setText(selectedCashbook.getTravel().getName());
			clubField.setText(selectedCashbook.getClub().getName());
			leaderField.setText(selectedCashbook.getLeader().getName());
			accountField.setText(selectedCashbook.getBankAccount());
			currencyCodeField.setText(selectedCashbook.getCurrencyCode());
			monthlyDueField.setText(Double.toString(selectedCashbook.getMonthlyDue()));
			budgetGoalField.setText(Double.toString(selectedCashbook.getBudgetGoal()));
			startDateField.setText(selectedCashbook.getTerm().getStartDate());
			endDateField.setText(selectedCashbook.getTerm().getEndDate());
			memoField.setText(selectedCashbook.getMemo());
		});

		newBtn.setOnAction(e -> {
			clubField.setVisible(false);
			clubField.clear();
			clubComboBox.setVisible(true);
			newBtn.setVisible(false);
			createBtn.setVisible(true);
			travelNameField.setDisable(false);
			travelNameField.clear();
			clubField.setDisable(false);
			clubField.clear();
			currencyCodeField.setText("KRW");
			leaderField.setDisable(false);
			leaderField.clear();
			accountField.setDisable(false);
			accountField.clear();
			monthlyDueField.setDisable(false);
			monthlyDueField.clear();
			budgetGoalField.setDisable(false);
			budgetGoalField.clear();
			startDateField.setDisable(false);
			startDateField.clear();
			endDateField.setDisable(false);
			endDateField.clear();
			memoField.setDisable(false);
			memoField.clear();
			cashbookTableView.setDisable(true);
			modifyBtn.setDisable(true);
			deleteBtn.setDisable(true);
		});

		createBtn.setOnAction(e -> {
			String travelName = travelNameField.getText();
			String clubName = clubComboBox.getValue();
			String leaderName = leaderField.getText();
			String account = accountField.getText();
			String monthlyDue = monthlyDueField.getText();
			String budgetGoal = budgetGoalField.getText();
			String startDate = startDateField.getText();
			String endDate = endDateField.getText();
			String memo = memoField.getText();

			if (budgetEvent.registCashbook(travelName, clubName, leaderName, account, monthlyDue, budgetGoal, startDate,
					endDate, memo)) {
				AlertBox.alert("", "Added");
				clubField.setVisible(true);
				clubComboBox.setVisible(false);
				newBtn.setVisible(true);
				createBtn.setVisible(false);
				travelNameField.setDisable(true);
				clubField.setDisable(true);
				leaderField.setDisable(true);
				accountField.setDisable(true);
				monthlyDueField.setDisable(true);
				budgetGoalField.setDisable(true);
				startDateField.setDisable(true);
				endDateField.setDisable(true);
				memoField.setDisable(true);
				cashbookTableView.setDisable(false);
				modifyBtn.setDisable(false);
				deleteBtn.setDisable(false);
				cashbookTableView.setItems(setCashbookTableView(budgetEvent.retrieveCashbook()));
			} else {
				AlertBox.alert("", "invalid values");
			}
		});

		deleteBtn.setOnAction(e -> {
			ObservableList<CashBook> selectedItem, allItem;
			allItem = cashbookTableView.getItems();
			selectedItem = cashbookTableView.getSelectionModel().getSelectedItems();

			if (ConfirmBox.display("DELETE", "sure to delete?")) {
				System.out.println(selectedItem);
				budgetEvent.remove(selectedItem.iterator().next());
				selectedItem.forEach(allItem::remove);
			}
		});

		modifyBtn.setOnAction(e -> {
			//TODO
			cashbookTableView.setDisable(true);
			deleteBtn.setDisable(true);
			
			travelNameField.setDisable(false);
			leaderField.setDisable(false);
			monthlyDueField.setDisable(false);
			budgetGoalField.setDisable(false);
			startDateField.setDisable(false);
			endDateField.setDisable(false);
			memoField.setDisable(false);
			
			newBtn.setDisable(true);
			
			modifyConfirmBtn.setVisible(true);
			modifyBtn.setVisible(false);
		});
		
		modifyConfirmBtn.setOnAction(e -> {
			String travelName = travelNameField.getText();
			String clubName = clubComboBox.getValue();
			String leaderName = leaderField.getText();
			String monthlyDue = monthlyDueField.getText();
			String budgetGoal = budgetGoalField.getText();
			String startDate = startDateField.getText();
			String endDate = endDateField.getText();
			String memo = memoField.getText();
			
			travelNameField.setDisable(true);
			leaderField.setDisable(true);
			monthlyDueField.setDisable(true);
			budgetGoalField.setDisable(true);
			startDateField.setDisable(true);
			endDateField.setDisable(true);
			memoField.setDisable(true);
			
			ObservableList<CashBook> selectedItem;
			selectedItem = cashbookTableView.getSelectionModel().getSelectedItems();
			
			budgetEvent.update(
					travelName, 
					clubName, 
					leaderName, 
					selectedItem.iterator().next().getBankAccount(),
					monthlyDue, 
					budgetGoal, 
					startDate,
					endDate,
					memo);
			
			cashbookTableView.setDisable(false);
			deleteBtn.setDisable(false);
			newBtn.setDisable(false);
			
			modifyConfirmBtn.setVisible(false);
			modifyBtn.setVisible(true);
			cashbookTableView.setItems(setCashbookTableView(budgetEvent.retrieveCashbook()));
		});
		return mainBox;
	}

	private ObservableList<CashBook> setCashbookTableView(ArrayList<CashBook> foundcashbook) {
		ObservableList<CashBook> foundcashbookList = FXCollections.observableArrayList();
		foundcashbookList.addAll(foundcashbook);

		return foundcashbookList;
	}
}
