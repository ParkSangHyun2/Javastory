package javastory.budgetsh.stage4.client.fxui;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javastory.budgetsh.stage4.client.fxui.event.BoardEventHelper;
import javastory.budgetsh.stage4.client.fxui.event.BudgetEventHelper;
import javastory.budgetsh.stage4.client.fxui.event.ClubEventHelper;
import javastory.budgetsh.stage4.client.fxui.event.YearlyDueEventHelper;
import javastory.budgetsh.stage4.client.fxui.util.AlertBox;
import javastory.budgetsh.stage4.share.domain.budget.budget.CashBook;
import javastory.budgetsh.stage4.share.domain.club.dto.PostingDto;
import javastory.budgetsh.stage4.share.domain.club.dto.TravelClubDto;

public class SceneContainer {
	//
	private Label name, clubName, introduce, title, content;
	private TextField nameField, introduceField, titleField;
	private Button createBtn, closeBtn, postingBtn;
	private TextArea contentField;

	private GridPane gridPane = new GridPane();
	private Label email;
	private TextField emailField;

	private ClubEventHelper clubEvent;
	private BoardEventHelper boardEvent;
	private BudgetEventHelper budgetEvent;
	private YearlyDueEventHelper yearlyDueEvent;

	public SceneContainer() {
		//
		clubEvent = new ClubEventHelper();
		boardEvent = new BoardEventHelper();
		budgetEvent = new BudgetEventHelper();
		yearlyDueEvent = new YearlyDueEventHelper();
	}

	public Scene createClubScene(Stage window) {
		//
		name = new Label("Name");
		introduce = new Label("Introduce");

		nameField = new TextField();
		introduceField = new TextField();

		gridPane.add(name, 2, 2);
		gridPane.add(introduce, 2, 3);
		gridPane.add(nameField, 3, 2);
		gridPane.add(introduceField, 3, 3);

		createBtn = new Button("create");

		gridPane.add(createBtn, 4, 5);
		gridPane.setPadding(new Insets(20));

		gridPane.setHgap(5);
		gridPane.setVgap(5);

		createBtn.setOnAction(e -> {

			if (clubEvent.createTravelClub(nameField.getText(), introduceField.getText())) {
				AlertBox.alert("", "Registed.");
				window.close();
			} else {
				AlertBox.alert("Failure", "Already exist Name");
			}
		});

		Scene scene = new Scene(gridPane, 350, 140);

		return scene;
	}

	public Scene createBoardScene(Stage window) {
		//
		window.setMinWidth(370);
		gridPane = new GridPane();

		clubName = new Label("Club Name");
		ArrayList<TravelClubDto> clubList = clubEvent.findAll();
		ArrayList<String> clubNameList = new ArrayList<>();
		for (TravelClubDto club : clubList) {
			clubNameList.add(club.getName());
		}
		ComboBox<String> comboBox = new ComboBox<String>();
		comboBox.getItems().addAll(clubNameList);
		comboBox.setValue(new String("--Select Club--"));

		name = new Label("BoardName");
		nameField = new TextField();
		email = new Label("AdminEmail");
		emailField = new TextField();

		createBtn = new Button("create");

		gridPane.add(clubName, 0, 0);
		gridPane.add(comboBox, 1, 0);
		gridPane.add(name, 0, 1);
		gridPane.add(nameField, 1, 1);
		gridPane.add(email, 0, 2);
		gridPane.add(emailField, 1, 2);
		gridPane.add(createBtn, 3, 2);
		gridPane.setPadding(new Insets(20));
		gridPane.setHgap(5);
		gridPane.setVgap(5);

		Scene scene = new Scene(gridPane, 350, 140);

		createBtn.setOnAction(e -> {
			String foundBoardName = boardEvent.createBoard(comboBox.getValue(), nameField.getText(),
					emailField.getText());

			if (foundBoardName.equals("already")) {
				AlertBox.alert("", "Board already in club");
			} else if (foundBoardName.equals("Invalid email")) {
				AlertBox.alert("", "No member in club");
			} else {
				AlertBox.alert("", "Added");
				window.close();
			}
		});
		

		return scene;
	}

	public Scene createPostingScene(Stage window, String clubName) {
		//
		gridPane = new GridPane();

		title = new Label("Title");
		titleField = new TextField();

		email = new Label("Email");
		emailField = new TextField();

		content = new Label("Content");
		contentField = new TextArea();
		contentField.setMinHeight(200);
		contentField.setMaxWidth(200);
		contentField.setWrapText(true);
		contentField.setPrefRowCount(2);

		postingBtn = new Button("Posting");

		gridPane.add(title, 0, 0);
		gridPane.add(titleField, 1, 0);
		gridPane.add(email, 0, 1);
		gridPane.add(emailField, 1, 1);
		gridPane.add(content, 0, 2);
		gridPane.add(contentField, 1, 2);
		gridPane.add(postingBtn, 3, 3);
		gridPane.setPadding(new Insets(20));
		gridPane.setHgap(5);
		gridPane.setVgap(5);

		Scene scene = new Scene(gridPane, 350, 140);
		
		postingBtn.setOnAction(e ->{
			boardEvent.registerPosting(titleField.getText(), emailField.getText(),contentField.getText(),clubName);
			AlertBox.alert("", "Added");
			window.close();
		});

		return scene;
	}

	public Scene showPostingScene(Stage window, PostingDto clickedPosting) {
		//
		gridPane = new GridPane();

		title = new Label("Title");
		titleField = new TextField();

		content = new Label("Content");
		contentField = new TextArea();
		contentField.setMinHeight(200);
		contentField.setMaxWidth(200);
		contentField.setWrapText(true);
		contentField.setPrefRowCount(2);
		
	//		titleField.setDisable(true);
		//contentField.setDisable(true);

		closeBtn = new Button("close");

		gridPane.add(title, 0, 0);
		gridPane.add(titleField, 1, 0);
		gridPane.add(content, 0, 1);
		gridPane.add(contentField, 1, 1);
		gridPane.add(closeBtn, 2, 2);
		gridPane.setPadding(new Insets(20));
		gridPane.setHgap(5);
		gridPane.setVgap(5);
		
		titleField.setText(clickedPosting.getTitle());
		contentField.setText(clickedPosting.getContents());
		//titleField.deselect();

		Scene scene = new Scene(gridPane, 350, 140);
		
		closeBtn.setOnAction(e ->{
			window.close();
		});

		return scene;
	}
	
	public void createMonthlyDue(String year, String account) {
		Stage window = new Stage();
		window.setMinWidth(330);
		window.setMinHeight(180);
		window.initModality(Modality.APPLICATION_MODAL);
		
		Label month = new Label("Month");
		ChoiceBox<Integer> monthChoice = new ChoiceBox<>();
		for(int i=1 ; i<=12; i++) {
			monthChoice.getItems().add(i);
			monthChoice.setValue(i);
		}
		
		Label type = new Label("Type");
		ChoiceBox<String> typeChoice = new ChoiceBox<>();
		typeChoice.getItems().addAll("Regular", "Donation", "Fine");
		typeChoice.setValue("Fine");
		
		Label amount = new Label("Amount");
		TextField amountField = new TextField();
		
		Label travel = new Label("Travel");
		
		ArrayList<CashBook> cashbooks = budgetEvent.retrieveCashbook();
		ComboBox<String> travelCombo = new ComboBox<>();
		for(CashBook cashbook : cashbooks) {
			travelCombo.getItems().add(cashbook.getTravel().getName());
		}
		Button addBtn = new Button("Add");
		
		GridPane gridPane = new GridPane();
		gridPane.add(month, 0, 0);
		gridPane.add(monthChoice, 1, 0);
		gridPane.add(type, 0, 1);
		gridPane.add(typeChoice, 1, 1);
		gridPane.add(amount, 0, 2);
		gridPane.add(amountField, 1, 2);
		gridPane.add(travel, 0, 3);
		gridPane.add(travelCombo, 1, 3);
		gridPane.add(addBtn, 3, 3);
		
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(10));
		
		VBox mainPane = new VBox(10);
		Label newMonthlyDue = new Label("New MonthlyDue");
		StackPane stackMonthlyDue = new StackPane();
		stackMonthlyDue.getChildren().add(newMonthlyDue);
		stackMonthlyDue.setAlignment(Pos.BASELINE_CENTER);
		
		mainPane.getChildren().addAll(stackMonthlyDue, gridPane);
		mainPane.setPadding(new Insets(10));
		
		addBtn.setOnAction(e ->{
			if((amountField.getText().equals("")) || (travelCombo.getValue() == null)) {
				AlertBox.alert("", "Invalid values");
				return;
			}
			yearlyDueEvent.regist(year,account, monthChoice.getValue(),typeChoice.getValue(),amountField.getText(), travelCombo.getValue());
			AlertBox.alert("", "Registed");
			window.close();
		});
		
		Scene scene = new Scene(mainPane,200,200);
		window.setScene(scene);
		window.show();
	}
}
