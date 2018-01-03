package javastory.budgetsh.stage3.club.ui.fxui.pane;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
import javastory.budgetsh.stage3.club.service.dto.ClubMembershipDto;
import javastory.budgetsh.stage3.club.service.dto.MemberDto;
import javastory.budgetsh.stage3.club.service.dto.PostingDto;
import javastory.budgetsh.stage3.club.service.dto.TravelClubDto;
import javastory.budgetsh.stage3.club.ui.fxui.event.ClubEventHelper;
import javastory.budgetsh.stage3.club.ui.fxui.util.AlertBox;
import javastory.budgetsh.stage3.club.ui.fxui.util.ConfirmBox;

public class ClubPaneContainer {
	//
	private Label name;

	private TextField nameField;
	private Button createBtn, searchBtn, modifyBtn, deleteBtn, allSearchBtn;
	private TableView<TravelClubDto> clubTableView;
	private TableView<PostingDto> postingTableView;

	private GridPane gridPane;
	
	private ClubEventHelper clubEvent;
	private ArrayList<TravelClubDto> clubList;

	public ClubPaneContainer() {
		//
		clubEvent = new ClubEventHelper();
		clubList = new ArrayList<TravelClubDto>();
	}

	public Pane loadClubPane() {
		//
		
		name = new Label("Club Name");

		nameField = new TextField();

		searchBtn = new Button("Search");
		modifyBtn = new Button("Modify");
		deleteBtn = new Button("Delete");
		allSearchBtn = new Button("All Search");

		modifyBtn.setAlignment(Pos.BOTTOM_RIGHT);
		deleteBtn.setAlignment(Pos.BOTTOM_RIGHT);

		clubTableView = new TableView<TravelClubDto>();
		clubTableView.setStyle(
			    "-fx-size : 40");
		
		TableColumn<TravelClubDto, String> nameColumn = new TableColumn<>("Club Name");
		nameColumn.setMinWidth(120);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		
		TableColumn<TravelClubDto, String> introduceColumn = new TableColumn<>("Club Introduce");
		introduceColumn.setMinWidth(340);
		introduceColumn.setCellValueFactory(new PropertyValueFactory<>("intro"));
		introduceColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		
		TableColumn<TravelClubDto, String> foundationColumn = new TableColumn<>("FoundationDay");
		foundationColumn.setMinWidth(120);
		foundationColumn.setCellValueFactory(new PropertyValueFactory<>("foundationDay"));
		foundationColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		
		clubTableView.getColumns().addAll(nameColumn, introduceColumn, foundationColumn);

		gridPane = new GridPane();
		gridPane.add(name, 0, 0);
		gridPane.add(nameField, 1, 0);
		gridPane.add(searchBtn, 2, 0);
		gridPane.add(allSearchBtn, 3, 0);
		gridPane.setHgap(5);
		gridPane.setVgap(5);

		HBox bottomBox = new HBox(5);
		bottomBox.getChildren().addAll(deleteBtn);
		bottomBox.setAlignment(Pos.BASELINE_RIGHT);

		VBox mainPane = new VBox(5);
		mainPane.getChildren().addAll(gridPane, clubTableView, bottomBox);
		mainPane.setPadding(new Insets(5));
		
		searchBtn.setOnAction(e ->{
			clubList = clubEvent.find(nameField.getText());
			if(clubList == null) {
				AlertBox.alert("", "No such clubName");
				clubTableView.setItems(null);
				return;
			}
			clubTableView.setItems(setClubTableView(clubList));
			
		});
		
		allSearchBtn.setOnAction(e ->{
			clubList = clubEvent.findAll();
			clubTableView.setItems(setClubTableView(clubList));
		});
		
		deleteBtn.setOnAction(e ->{
			ObservableList<TravelClubDto> selectedItem, allItem;
			allItem = clubTableView.getItems();
			selectedItem = clubTableView.getSelectionModel().getSelectedItems();

			if (ConfirmBox.display("DELETE", "sure to delete?")) {
				System.out.println(selectedItem);
				clubEvent.remove(selectedItem.iterator().next().getUsid());
				selectedItem.forEach(allItem::remove);
			}
		});
		
		modifyBtn.setOnAction(e ->{
			//TODO
		});

		return mainPane;
	}

	public Pane clearScreen() {
		//
		StackPane pane = new StackPane();

		return pane;
	}
	
	private ObservableList<TravelClubDto> setClubTableView(ArrayList<TravelClubDto> foundClub){
		ObservableList<TravelClubDto> foundClubList = FXCollections.observableArrayList();
		foundClubList.addAll(foundClub);

		return foundClubList;
	}
}
