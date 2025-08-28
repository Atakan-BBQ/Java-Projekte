package app.fx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainApp extends Application{
	
	private int counter = 0;

	public static void main(String[] args) {
		
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {

		Font customFont = Font.font("Arial", FontWeight.BOLD, 20);
		
		// 4. Nodes
		// Label
		Label label = new Label("Zähler: " + counter);
		label.setFont(customFont);
		
		// Button
		Button buttonIncrease = new Button("Erhöhen");
		Button buttonDecrease = new Button("Verringern");
		buttonIncrease.setFont(customFont);
		buttonDecrease.setFont(customFont);
		
		// 3. Layout Manager
		VBox vBox = new VBox(10);
		
		// 4.1 Nodes Layout einfügen
		vBox.setPadding(new Insets(10,10,10,10));
		vBox.getChildren().add(label);
		vBox.getChildren().add(buttonIncrease);
		vBox.getChildren().add(buttonDecrease);
		
		buttonIncrease.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                label.setText("Zähler: " + ++counter);
            }
        });
		
		buttonDecrease.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                label.setText("Zähler: " + --counter);
            }
        });
		
		// 2. Scene
		Scene scene = new Scene(vBox, 400, 400);
		
		// 1. Stage
		stage.setTitle("Zähler Aufgabe");
		stage.setScene(scene);
		stage.show();
		
		
	} // start()
} // Class MainApp
