package javastory.budgetsh.stage3.club.ui.fxui;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javastory.budgetsh.stage3.club.ui.fxui.pane.BoardPaneContainer;
import javastory.budgetsh.stage3.club.ui.fxui.pane.BudgetPaneContainer;
import javastory.budgetsh.stage3.club.ui.fxui.pane.ClubPaneContainer;
import javastory.budgetsh.stage3.club.ui.fxui.pane.MemberPaneContainer;
import javastory.budgetsh.stage3.club.ui.fxui.pane.TransactionPaneContainer;
import javastory.budgetsh.stage3.club.ui.fxui.pane.YearlyDuePaneContainer;

public class StageCreator {
	//
	private SceneContainer scenes;
	private ClubPaneContainer clubPanes;
	private MemberPaneContainer memberPanes;
	private BudgetPaneContainer budgetPanes;
	private BoardPaneContainer boardPanes;
	private TransactionPaneContainer transactionPanes;
	private YearlyDuePaneContainer yearlyDuePanes;
	private BorderPane layout;
	private Stage window;
	private Stage popupWindow;
	
	private Menu clubMenu, memberMenu, boardMenu, budgetMenu;

	
	private MenuItem newClub, loadClub, close, allMembers, membership,
			newBoard, loadPosting, cashbook, transaction, yearlyDue;
	
	public StageCreator(Stage window, BorderPane layout) {
		//
		this.window = window;
		clubPanes = new ClubPaneContainer();
		memberPanes = new MemberPaneContainer();
		budgetPanes = new BudgetPaneContainer();
		boardPanes = new BoardPaneContainer();
		transactionPanes = new TransactionPaneContainer();
		scenes = new SceneContainer();
		this.layout = layout;
	}
	
	public void createPopup(String title, Scene scene, Stage popupWindow) {
		//
		popupWindow.setTitle(title);
		popupWindow.setScene(scene);
		popupWindow.initModality(Modality.APPLICATION_MODAL);
		popupWindow.showAndWait();
		popupWindow.close();
	}
	
	public MenuBar createMenuBar() {
		//
		clubMenu = new Menu("Club");
		memberMenu = new Menu("Member");
		boardMenu = new Menu("Board");
		budgetMenu = new Menu("budget");
		
		clubMenu.getItems().addAll(
				newClub = new MenuItem("new club"),
				loadClub = new MenuItem("Load club"),
				close = new MenuItem("close travelClub"));
		
		memberMenu.getItems().addAll(
				allMembers = new MenuItem("All members"),
				membership = new MenuItem("Membership")
				);
		
		boardMenu.getItems().addAll(
				newBoard = new MenuItem("new board"),
				loadPosting = new MenuItem("Load posting")
				);
		
		budgetMenu.getItems().addAll(
				cashbook = new MenuItem("Cashbook"),
				transaction = new MenuItem("Transaction"),
				yearlyDue = new MenuItem("YearlyDue")
				);
		
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(clubMenu, memberMenu, boardMenu, budgetMenu);

		this.setEvents();
		
		return menuBar;
	}
	
	public void setEvents() {
		//
		newClub.setOnAction(e->{
			popupWindow = new Stage();
			layout.setMinSize(400, 500);
			scenes = new SceneContainer();
			createPopup("Create TravelClub", scenes.createClubScene(popupWindow),popupWindow);
		});
		loadClub.setOnAction(e->{
			createStage();
			window.setMinHeight(550);
			window.setMinWidth(600);
			layout.setMinSize(300, 400);
			clubPanes = new ClubPaneContainer();
			
			layout.setTop(createMenuBar());
			layout.setCenter(clubPanes.loadClubPane());
			Scene scene = new Scene(layout);
			window.setScene(scene);
			window.show();
		});
		close.setOnAction(e ->{
			createStage();
			
			layout.setMinSize(300, 400);
			clubPanes = new ClubPaneContainer();	
			layout.setCenter(clubPanes.clearScreen());
			layout.setTop(createMenuBar());
			Scene scene = new Scene(layout);
			window.setScene(scene);
			window.show();
		});
		allMembers.setOnAction(e ->{
			createStage();
			
			clubPanes = new ClubPaneContainer();
			window.setMinHeight(550);
			window.setMinWidth(600);
			layout.setMinSize(300, 400);
			layout.setCenter(memberPanes.showAllMembers());
			layout.setTop(createMenuBar());
			Scene scene = new Scene(layout);
			window.setScene(scene);
			window.show();
		});
		membership.setOnAction(e -> {
			createStage();
			
			window.setMaxHeight(550);
			window.setMaxWidth(600);
			layout.setMinSize(300, 400);
			clubPanes = new ClubPaneContainer();
			layout.setCenter(memberPanes.showMembership());
			layout.setTop(createMenuBar());
			Scene scene = new Scene(layout);
			window.setScene(scene);
			window.show();
		});
		newBoard.setOnAction(e ->{
			popupWindow = new Stage();
			scenes = new SceneContainer();
			createPopup("Create Board", scenes.createBoardScene(popupWindow), popupWindow);
		});
		loadPosting.setOnAction(e ->{
			createStage();
			
			window.setMaxHeight(550);
			window.setMaxWidth(600);
			layout.setMinSize(300, 400);
			boardPanes = new BoardPaneContainer();
			layout.setCenter(boardPanes.showPostings());
			layout.setTop(createMenuBar());
			Scene scene = new Scene(layout);
			window.setScene(scene);
			window.show();
		});
		cashbook.setOnAction(e ->{
			createStage();
			
			window.setMinHeight(550);
			window.setMinWidth(600);
			layout.setMinSize(300, 400);
			budgetPanes = new BudgetPaneContainer();
			layout.setCenter(budgetPanes.showCashBook());
			layout.setTop(createMenuBar());
			Scene scene = new Scene(layout);
			window.setScene(scene);
			window.show();
		});
		transaction.setOnAction(e ->{
			createStage();
			
			window.setMinHeight(550);
			window.setMinWidth(600);
			layout.setMinSize(300, 400);
			budgetPanes = new BudgetPaneContainer();
			layout.setCenter(transactionPanes.showTransaction());
			layout.setTop(createMenuBar());
			Scene scene = new Scene(layout);
			window.setScene(scene);
			window.show();
		});
		yearlyDue.setOnAction(e ->{
			//
			createStage();
			
			window.setMinHeight(550);
			window.setMinWidth(600);
			layout.setMinSize(300, 400);
			yearlyDuePanes = new YearlyDuePaneContainer();
			layout.setCenter(yearlyDuePanes.showYealyDue());
			layout.setTop(createMenuBar());
			Scene scene = new Scene(layout);
			window.setScene(scene);
			window.show();
		});
	}
	
	private void createStage() {
		//
		window.close();
		window = new Stage();
		layout = new BorderPane();
		window.setTitle("Travel Club");
		window.setMinHeight(400);
		window.setMinWidth(500);
	}
}
