package javastory.budgetsh.stage3.club.ui.fxui.pane;

import java.util.ArrayList;

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
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javastory.budgetsh.stage3.club.entity.club.RoleInClub;
import javastory.budgetsh.stage3.club.service.dto.ClubMembershipDto;
import javastory.budgetsh.stage3.club.service.dto.MemberDto;
import javastory.budgetsh.stage3.club.service.dto.TravelClubDto;
import javastory.budgetsh.stage3.club.ui.fxui.event.ClubEventHelper;
import javastory.budgetsh.stage3.club.ui.fxui.event.MemberEventHelper;
import javastory.budgetsh.stage3.club.ui.fxui.util.AlertBox;

public class MemberPaneContainer {
	
	private Label title, name, email, phone, nickname, birthday, clubName, role;
	private TextField nameField, emailField, phoneField, nicknameField, birthdayField;
	private TableView<MemberDto> memberTableView;
	private TableView<ClubMembershipDto> membershipTableView;
	private Button newMemberBtn, addMembershipBtn, removeBtn, modifyBtn, modifyConfirmBtn;
	
	private MemberEventHelper memberEvent;
	private ClubEventHelper clubEvent;
	
	public MemberPaneContainer() {
		memberEvent = new MemberEventHelper();
		clubEvent = new ClubEventHelper();
	}
	
	public Pane showAllMembers() {
		//
		memberTableView = new TableView<MemberDto>();
		
		TableColumn<MemberDto, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setMinWidth(100);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<MemberDto, String> emailColumn = new TableColumn<>("E-mail");
		emailColumn.setMinWidth(145);
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		
//		TableColumn<MemberDto, String> phoneColumn = new TableColumn<>("PhoneNumber");
//		phoneColumn.setMinWidth(100);
//		phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		
		memberTableView.getColumns().addAll(nameColumn, emailColumn);
		memberTableView.setItems(setMemberTableView(memberEvent.findAllMembers()));
		
		// Title
		title = new Label("Member List");
		title.setFont(Font.font(25));

		newMemberBtn = new Button("New Member");
		Button confirmBtn = new Button("Create");
		confirmBtn.setVisible(false);
		
		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(newMemberBtn,confirmBtn);

		HBox titleBox = new HBox(45);
		titleBox.getChildren().addAll(title, stackPane);
		titleBox.setAlignment(Pos.BOTTOM_CENTER);

		// Field
		name = new Label("Name");
		email = new Label("E-mail");
		nickname = new Label("NickName");
		phone = new Label("Phone Number");
		birthday = new Label("BirthDay");

		nameField = new TextField();
		emailField = new TextField();
		nicknameField = new TextField();
		phoneField = new TextField();
		birthdayField = new TextField();

		GridPane fieldPane = new GridPane();
		fieldPane.add(name, 0, 0);
		fieldPane.add(email, 0, 1);
		fieldPane.add(nickname, 0, 2);
		fieldPane.add(phone, 0, 3);
		fieldPane.add(birthday, 0, 4);

		fieldPane.add(nameField, 1, 0);
		fieldPane.add(emailField, 1, 1);
		fieldPane.add(nicknameField, 1, 2);
		fieldPane.add(phoneField, 1, 3);
		fieldPane.add(birthdayField, 1, 4);

		fieldPane.setHgap(20);
		fieldPane.setVgap(20);
		
		fieldPane.setDisable(true);

		// Btn
		HBox btnBox = new HBox(15);

		modifyBtn = new Button("Modify");
		modifyConfirmBtn = new Button("Confirm");
		modifyConfirmBtn.setVisible(false);
		StackPane modifyBtns = new StackPane();
		modifyBtns.getChildren().addAll(modifyBtn, modifyConfirmBtn);
		modifyBtn.setDisable(true);
		removeBtn = new Button("Remove");
		btnBox.getChildren().addAll(modifyBtns, removeBtn);
		btnBox.setAlignment(Pos.BASELINE_CENTER);

		VBox insertBox = new VBox(65);
		insertBox.getChildren().addAll(titleBox, fieldPane, btnBox);
		insertBox.setAlignment(Pos.CENTER);
		insertBox.setPadding(new Insets(15));

		HBox mainBox = new HBox(15);
		mainBox.setPadding(new Insets(5));

		mainBox.getChildren().addAll(memberTableView, insertBox);
		HBox.setHgrow(insertBox, Priority.ALWAYS);
		
		memberTableView.setOnMouseClicked(e ->{
			ObservableList<MemberDto> selectedItem, allItem;
			allItem = memberTableView.getItems();
			selectedItem = memberTableView.getSelectionModel().getSelectedItems();
			
			MemberDto clickedMember = selectedItem.iterator().next();
			
			emailField.setText(clickedMember.getEmail());
			nameField.setText(clickedMember.getName());
			nicknameField.setText(clickedMember.getNickName());
			phoneField.setText(clickedMember.getPhoneNumber());
			birthdayField.setText(clickedMember.getBirthDay());
			modifyBtn.setDisable(false);
		});
		
		newMemberBtn.setOnAction(e ->{
			fieldPane.setDisable(false);
			modifyBtn.setDisable(true);
			removeBtn.setDisable(true);
			memberTableView.setDisable(true);
			
			emailField.clear();
			nameField.clear();
			nicknameField.clear();
			phoneField.clear();
			birthdayField.clear();
			
			newMemberBtn.setVisible(false);
			confirmBtn.setVisible(true);
		});
		
		confirmBtn.setOnAction(e ->{
			MemberDto member = new MemberDto(emailField.getText(),
					nameField.getText(),phoneField.getText());
			member.setNickName(nicknameField.getText());
			member.setBirthDay(birthdayField.getText());
			if(memberEvent.registMember(member)) {
				AlertBox.alert("", "Registed.");
				memberTableView.setItems(setMemberTableView(memberEvent.findAllMembers()));
				
				fieldPane.setDisable(true);
				modifyBtn.setDisable(false);
				removeBtn.setDisable(false);
				memberTableView.setDisable(false);
				
				newMemberBtn.setVisible(true);
				confirmBtn.setVisible(false);
			}
		});
		
		removeBtn.setOnAction(e ->{
			ObservableList<MemberDto> selectedItem, allItem;
			allItem = memberTableView.getItems();
			selectedItem = memberTableView.getSelectionModel().getSelectedItems();
			
			MemberDto removeMember = selectedItem.iterator().next();
			memberEvent.removeMember(removeMember);
			selectedItem.forEach(allItem::remove);
		});
		
		modifyBtn.setOnAction(e ->{
			fieldPane.setDisable(false);
			modifyBtn.setVisible(false);
			removeBtn.setDisable(true);
			memberTableView.setDisable(true);
			
			newMemberBtn.setDisable(true);
			modifyConfirmBtn.setVisible(true);
		});
		
		modifyConfirmBtn.setOnAction(e ->{
			ObservableList<MemberDto> selectedItem;
			selectedItem = memberTableView.getSelectionModel().getSelectedItems();
			MemberDto modifiedMember = new MemberDto(
					selectedItem.iterator().next().getEmail(),
					nameField.getText(),
					phoneField.getText());
			modifiedMember.setNickName(nicknameField.getText());
			modifiedMember.setBirthDay(birthday.getText());
			memberEvent.update(modifiedMember);
			
			fieldPane.setDisable(true);
			modifyBtn.setVisible(true);
			modifyConfirmBtn.setVisible(false);
			removeBtn.setDisable(false);
			memberTableView.setDisable(false);
			
			newMemberBtn.setDisable(false);
			
			AlertBox.alert("", "Updated.");
			memberTableView.setItems(setMemberTableView(memberEvent.findAllMembers()));
		});
		
		return mainBox;
	}

	public Pane showMembership() {
		//
		membershipTableView = new TableView<ClubMembershipDto>();
		membershipTableView.setMaxSize(600, 300);
		
		TableColumn<ClubMembershipDto, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setMinWidth(200);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("memberName"));
		TableColumn<ClubMembershipDto, String> emailColumn = new TableColumn<>("Email");
		emailColumn.setMinWidth(200);
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("memberEmail"));
		TableColumn<ClubMembershipDto, String> joinColumn = new TableColumn<>("JoinDate");
		joinColumn.setMinWidth(100);
		joinColumn.setCellValueFactory(new PropertyValueFactory<>("joinDate"));
		TableColumn<ClubMembershipDto, RoleInClub> roleColumn = new TableColumn<>("Role");
		roleColumn.setMinWidth(90);
		roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
		
		membershipTableView.getColumns().addAll(nameColumn, emailColumn, joinColumn,roleColumn);

		title = new Label("Membership List");
		title.setFont(Font.font(30));
		
		ArrayList<TravelClubDto> clubDtoList = clubEvent.findAll();

		ObservableList<String> clubList =
				FXCollections.observableArrayList();
		
		for(TravelClubDto club : clubDtoList) {
			clubList.add(club.getName());
		}
		clubName = new Label("Club Name");
		ComboBox<String> clubNameComboBox = new ComboBox<String>(clubList);
		clubNameComboBox.setValue("--Select Club--");

		name = new Label("Name");
		
		ArrayList<MemberDto> memberDtoList = memberEvent.findAllMembers();
		ObservableList<String> memberList =
				FXCollections.observableArrayList();
		for(MemberDto member : memberDtoList) {
			memberList.add(member.getName());
		}
		ComboBox<String> memberNameComboBox = new ComboBox<String>(memberList);
		memberNameComboBox.setValue("--Select Member--");
		
		role = new Label("Role");
		ChoiceBox<String> roleChoiceBox = new ChoiceBox<String>();
		roleChoiceBox.getItems().addAll("President","Member");
		roleChoiceBox.setValue("Member");
		
		addMembershipBtn = new Button("Add Membership");

		GridPane fieldPane = new GridPane();
		fieldPane.add(clubName, 0, 0);
		fieldPane.add(clubNameComboBox, 1, 0);
		fieldPane.add(name, 0, 1);
		fieldPane.add(memberNameComboBox, 1, 1);
		fieldPane.add(role, 0, 2);
		fieldPane.add(roleChoiceBox, 1, 2);
		fieldPane.add(addMembershipBtn, 2, 2);
		fieldPane.setHgap(15);
		fieldPane.setVgap(10);
		fieldPane.setAlignment(Pos.CENTER);

		removeBtn = new Button("Remove");
		

		VBox insertBox = new VBox(35);
		insertBox.getChildren().addAll(title,fieldPane);
		insertBox.setAlignment(Pos.CENTER);
		insertBox.setPadding(new Insets(20));

		VBox mainBox = new VBox(10);
		mainBox.getChildren().addAll(insertBox, membershipTableView, removeBtn);
		mainBox.setPadding(new Insets(10));
		
		clubNameComboBox.setOnAction(e ->{
			String clubName = clubNameComboBox.getValue();
			

			membershipTableView.setItems(setMembershipTableView(clubName));
		});
		
		addMembershipBtn.setOnAction(e ->{
			String memberName = memberNameComboBox.getValue();
			String clubName = clubNameComboBox.getValue();
			String role = roleChoiceBox.getValue();
			
			if(memberName.equals("--Select Member--")) {
				AlertBox.alert("Select", "select Member");
				return;
			}
			if(clubName.equals("--Select Club--")){
				AlertBox.alert("Select", "select Club");
				return;
			}
			
			if(memberEvent.addMembership(memberName, clubName,role)) {
				AlertBox.alert("", "Added");
				membershipTableView.setItems(setMembershipTableView(clubName));
			}else {
				AlertBox.alert("", "Already in club");
			}
		});
		
		removeBtn.setOnAction(e ->{
			ObservableList<ClubMembershipDto> selectedItem, allItem;
			allItem = membershipTableView.getItems();
			selectedItem = membershipTableView.getSelectionModel().getSelectedItems();
			
			ClubMembershipDto removeMember = selectedItem.iterator().next();
			memberEvent.removeMembership(removeMember);
			selectedItem.forEach(allItem::remove);
		});

		return mainBox;
	}
	
	private ObservableList<MemberDto> setMemberTableView(ArrayList<MemberDto> foundMember){
		ObservableList<MemberDto> foundMemberList = FXCollections.observableArrayList();
		foundMemberList.addAll(foundMember);

		return foundMemberList;
	}
	
	private ObservableList<ClubMembershipDto> setMembershipTableView(String clubName){
		ArrayList<ClubMembershipDto> membershipList = memberEvent.findAllMembership(clubName);
		
		ObservableList<ClubMembershipDto> membershipDtoList =
				FXCollections.observableArrayList();

		membershipDtoList.addAll(membershipList);
		return membershipDtoList;
	}
}
