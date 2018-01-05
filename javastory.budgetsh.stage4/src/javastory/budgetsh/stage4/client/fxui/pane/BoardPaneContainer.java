package javastory.budgetsh.stage4.client.fxui.pane;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javastory.budgetsh.stage4.client.fxui.SceneContainer;
import javastory.budgetsh.stage4.client.fxui.event.BoardEventHelper;
import javastory.budgetsh.stage4.client.fxui.event.ClubEventHelper;
import javastory.budgetsh.stage4.client.fxui.util.AlertBox;
import javastory.budgetsh.stage4.share.domain.club.dto.PostingDto;
import javastory.budgetsh.stage4.share.domain.club.dto.TravelClubDto;


public class BoardPaneContainer {
	//
	private ClubEventHelper clubEvent;
	private BoardEventHelper boardEvent;
	private SceneContainer sceneContainer;
	private ArrayList<PostingDto> postingList;

	private TableView<PostingDto> postingTableView;
	private Button createBtn;

	public BoardPaneContainer() {
		clubEvent = new ClubEventHelper();
		boardEvent = new BoardEventHelper();
		sceneContainer = new SceneContainer();
	}

	public Pane showPostings() {
		//
		Label title = new Label("Board Postings");
		title.setFont(Font.font(30));
		
		Label detailTitle = new Label("--> Double-click a table to view detail");
		
		HBox titleBox = new HBox(10);
		titleBox.getChildren().addAll(title, detailTitle);
		titleBox.setAlignment(Pos.CENTER_LEFT);
		postingTableView = new TableView<PostingDto>();
		
		TableColumn<PostingDto, String> titleColumn = new TableColumn<>("Title");
		titleColumn.setMinWidth(140);
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		titleColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		
		TableColumn<PostingDto, String> writerEmailColumn = new TableColumn<>("WriterEmail");
		writerEmailColumn.setMinWidth(150);
		writerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("writerEmail"));
		writerEmailColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		
		TableColumn<PostingDto, String> dateColumn = new TableColumn<>("WrittenDate");
		dateColumn.setMinWidth(70);
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("writtenDate"));
		dateColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		
		TableColumn<PostingDto, Integer> countColumn = new TableColumn<>("Read Count");
		countColumn.setMinWidth(5);
		countColumn.setCellValueFactory(new PropertyValueFactory<>("readCount"));
		countColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
		
		postingTableView.getColumns().addAll(titleColumn, writerEmailColumn, dateColumn, countColumn);
		
		ArrayList<TravelClubDto> clubList = clubEvent.findAll();
		ArrayList<String> clubNameList = new ArrayList<>();
		for (TravelClubDto club : clubList) {
			clubNameList.add(club.getName());
		}
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll(clubNameList);
		comboBox.setValue(clubNameList.iterator().next());
		
		postingList = boardEvent.retrievePostings(comboBox.getValue());
		postingTableView.setItems(setPostingTableView(postingList));
		
		Label club = new Label("ClubName");
		createBtn = new Button("New Posting");
		HBox fieldBox = new HBox(10);
		fieldBox.getChildren().addAll(club, comboBox, createBtn);
		fieldBox.setAlignment(Pos.BASELINE_RIGHT);

		VBox mainBox = new VBox(10);
		mainBox.getChildren().addAll(titleBox, postingTableView, fieldBox);
		mainBox.setPadding(new Insets(10));
		
		comboBox.setOnAction(e ->{
			postingList = boardEvent.retrievePostings(comboBox.getValue());
			if(postingList == null) {
				AlertBox.alert("", "There is no Board");
				return;
			}
			postingTableView.setItems(setPostingTableView(postingList));
		});
		
		createBtn.setOnAction(e ->{
			Stage popupWindow = new Stage();
			popupWindow.setTitle("create New Posting");
			popupWindow.setScene(sceneContainer.createPostingScene(popupWindow,comboBox.getValue()));
			popupWindow.initModality(Modality.APPLICATION_MODAL);
			popupWindow.setMinHeight(350);
			popupWindow.setMinWidth(380);
			popupWindow.showAndWait();
			postingList = boardEvent.retrievePostings(comboBox.getValue());
			postingTableView.setItems(setPostingTableView(postingList));
		});
		
		postingTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//
				if(event.getClickCount()>1) {
					ObservableList<PostingDto> selectedItem;
					selectedItem = postingTableView.getSelectionModel().getSelectedItems();
					PostingDto clickedPostingDto = selectedItem.iterator().next();
					clickedPostingDto.increaseCount();
					Stage popupWindow = new Stage();
					popupWindow.setTitle("Posting");
					boardEvent.update(clickedPostingDto);
					
					postingList = boardEvent.retrievePostings(comboBox.getValue());
					postingTableView.setItems(setPostingTableView(postingList));
					
					System.out.println(clickedPostingDto.getContents());
					popupWindow.setScene(sceneContainer.showPostingScene(popupWindow, clickedPostingDto));
					popupWindow.setMinWidth(380);
					popupWindow.setMinHeight(350);
					popupWindow.showAndWait();
				}
			}
		});
		return mainBox;
	}

	private ObservableList<PostingDto> setPostingTableView(ArrayList<PostingDto> foundPosting) {
		ObservableList<PostingDto> foundPostingList = FXCollections.observableArrayList();
		if(foundPosting == null) {
			return null;
		}
		foundPostingList.addAll(foundPosting);

		return foundPostingList;
	}
}
