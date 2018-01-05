package javastory.budgetsh.stage4.client.fxui.pane;

import java.time.Year;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javastory.budgetsh.stage4.client.fxui.SceneContainer;
import javastory.budgetsh.stage4.client.fxui.event.BudgetEventHelper;
import javastory.budgetsh.stage4.client.fxui.event.YearlyDueEventHelper;
import javastory.budgetsh.stage4.client.fxui.util.AlertBox;
import javastory.budgetsh.stage4.client.fxui.util.ConfirmBox;
import javastory.budgetsh.stage4.share.domain.budget.account.DuesType;
import javastory.budgetsh.stage4.share.domain.budget.account.MonthlyDue;
import javastory.budgetsh.stage4.share.domain.budget.budget.CashBook;
import javastory.budgetsh.stage4.share.domain.share.IdName;


public class YearlyDuePaneContainer {
	//
	private Label year, cashbook;
	private ComboBox<String> yearCombo, cashbookCombo;
	private TableView<MonthlyDue> monthlyDueTable;

	private YearlyDueEventHelper yearlyEvent;
	private BudgetEventHelper budgetEvent;
	
	private SceneContainer sceneContainer;

	public YearlyDuePaneContainer() {
		yearlyEvent = new YearlyDueEventHelper();
		budgetEvent = new BudgetEventHelper();
		sceneContainer = new SceneContainer();
	}

	public Pane showYealyDue() {
		//
		year = new Label("Year");
		cashbook = new Label("CashBook Account");
		yearCombo = new ComboBox<String>();
		cashbookCombo = new ComboBox<String>();
		Button searchBtn = new Button("Search");
		ArrayList<CashBook> cashbookList = budgetEvent.retrieveCashbook();
		for (CashBook cashbook : cashbookList) {
			cashbookCombo.getItems().add(cashbook.getBankAccount());
		}
		cashbookCombo.setValue(cashbookList.iterator().next().getBankAccount());

//		ArrayList<AccountYearlyDue> yearlyDueList = new ArrayList<AccountYearlyDue>();
//		System.out.println("It's" + cashbookCombo.getValue());
//		yearlyDueList = yearlyEvent.retrieveYearlyAll(cashbookCombo.getValue());
//		if (yearlyDueList != null) {
//			for (AccountYearlyDue yearlyDue : yearlyDueList) {
//				yearCombo.getItems().add(yearlyDue.getYear());
//			}
//			yearCombo.setValue(yearlyDueList.iterator().next().getYear());
//		}else {
//			yearCombo.setValue("2017");
//		}
		for(int comboYear = 2016; comboYear <= Year.now().getValue(); comboYear++) {
			yearCombo.getItems().add(String.valueOf(comboYear));
		}
		yearCombo.setValue(String.valueOf(Year.now().getValue()));
		

		monthlyDueTable = new TableView<MonthlyDue>();

		TableColumn<MonthlyDue, Integer> monthColumn = new TableColumn<>("Month");
		monthColumn.setMinWidth(120);
		monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
		monthColumn.setStyle("-fx-alignment: CENTER;");

		TableColumn<MonthlyDue, DuesType> typeColumn = new TableColumn<>("Type");
		typeColumn.setMinWidth(120);
		typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
		typeColumn.setStyle("-fx-alignment: CENTER;");

		TableColumn<MonthlyDue, Double> amountColumn = new TableColumn<>("Amount");
		amountColumn.setMinWidth(120);
		amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
		amountColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		
		TableColumn<MonthlyDue, String> currencyCodeColumn = new TableColumn<>("CurrencyCode");
		currencyCodeColumn.setMinWidth(120);
		currencyCodeColumn.setCellValueFactory(new PropertyValueFactory<>("currencyCode"));
		currencyCodeColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		
		TableColumn<MonthlyDue, IdName> travelColumn = new TableColumn<>("Travel");
		travelColumn.setMinWidth(120);
		travelColumn.setCellValueFactory(new PropertyValueFactory<>("travel"));
		travelColumn.setStyle("-fx-alignment: CENTER;");

		monthlyDueTable.getColumns().addAll(monthColumn, typeColumn, amountColumn, currencyCodeColumn, travelColumn);

		HBox yearBox = new HBox(30);
		yearBox.getChildren().addAll(year, yearCombo, cashbook, cashbookCombo, searchBtn);
		yearBox.setAlignment(Pos.BASELINE_LEFT);
		
		Button deleteBtn = new Button("Delete");
		Button addBtn = new Button("Add");
		HBox buttonPane = new HBox(10);
		buttonPane.getChildren().addAll(addBtn, deleteBtn);
		buttonPane.setAlignment(Pos.BASELINE_RIGHT);

		VBox mainBox = new VBox(10);
		mainBox.getChildren().addAll(yearBox, monthlyDueTable, buttonPane);
		mainBox.setPadding(new Insets(10));
		
		searchBtn.setOnAction(e -> {
			ObservableList<MonthlyDue> monthlyDueList = FXCollections.observableArrayList();
			ArrayList<MonthlyDue> monthlyDues = yearlyEvent.retrieveMonthyAll(cashbookCombo.getValue(),yearCombo.getValue());
			
			if(monthlyDues == null) {
				AlertBox.alert("", "no monthlyDue in account");
				monthlyDueTable.setItems(null);
				return;
			}
			monthlyDueList.addAll(monthlyDues);
			monthlyDueTable.setItems(monthlyDueList);
		});
		
		deleteBtn.setOnAction(e ->{
			ObservableList<MonthlyDue> selectedItem, allItem;
			allItem = monthlyDueTable.getItems();
			selectedItem = monthlyDueTable.getSelectionModel().getSelectedItems();

			if (ConfirmBox.display("DELETE", "sure to delete?")) {
				System.out.println(selectedItem);
				yearlyEvent.remove(cashbookCombo.getValue(), selectedItem.iterator().next(),yearCombo.getValue());
				selectedItem.forEach(allItem::remove);
			}
		});
		
		addBtn.setOnAction(e -> {
			sceneContainer.createMonthlyDue(yearCombo.getValue(),cashbookCombo.getValue());
		});
		
		return mainBox;
	}

}
