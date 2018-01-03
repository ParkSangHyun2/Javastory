package javastory.budgetsh.stage4.client.fxui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TravelClubFx extends Application{
	//
	private Stage window;
	private BorderPane layout;
	
	private StageCreator stageCreator;
	
	public TravelClubFx() {
		//
		window = new Stage();
		window.setTitle("Travel Club");
		window.setMinHeight(550);
		window.setMinWidth(600);
		layout = new BorderPane();
		stageCreator = new StageCreator(window, layout);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//
		Scene scene = new Scene(stageCreator.createMenuBar());
		window.setScene(scene);
		window.showAndWait();
		window.close();
	}
	
	public static void main(String args[]) {
		launch();
	}

}
