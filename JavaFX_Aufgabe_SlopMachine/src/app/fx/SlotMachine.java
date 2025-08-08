package app.fx;

import java.io.FileInputStream;
import java.util.Random;

import javafx.scene.media.AudioClip;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SlotMachine extends Application {
	
	int manipulation = 0;

	public static void main(String[] args) {
		
		launch(args);

	} // main()

	@Override
	public void start(Stage arg0) throws Exception {
		
		AudioClip insertCoin = new AudioClip("file:src/InsertCoin.wav");
		AudioClip IconMatch = new AudioClip("file:src/IconMatch.wav");
		
		// Image
		// Da wir die Bilder immer wieder ändern müssen wir die Images zwischenspeichern
		Image imageBanane = new Image(new FileInputStream("src/Banane.png"));
		Image imageKirsche = new Image(new FileInputStream("src/Kirsche.png"));
		Image imageGold = new Image(new FileInputStream("src/Gold.png"));
		
		
		// ImageView
		// Hier ein Beispiel wie man direkt alles in 1 Schritt zusammenfassen kann ABER wir benötigen
		// Das Image als zwischen Variable sodass wir diese immer wieder austauschen können 
		// ImageView viewSlot_1 = new ImageView(new Image(new FileInputStream("src/Banane.png")));
		ImageView viewSlot_1 = new ImageView(imageBanane);
		ImageView viewSlot_2 = new ImageView(imageKirsche);
		ImageView viewSlot_3 = new ImageView(imageGold);
		
		ImageView[] viewContainer = {
				viewSlot_1, viewSlot_2, viewSlot_3
		};
			
		for (ImageView view : viewContainer) {
			view.setFitHeight(150);
			view.setFitWidth(150);
		}
				
		
		// 4. Nodes
		
		// Label
		Label machineName = new Label("Slot Machine 2025");
		Label credits = new Label("Credits: ");
		Label creditPoints = new Label("500");
		
		machineName.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		credits.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		creditPoints.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		
		// Button
		Button pullButton = new Button("Pull");
		
		pullButton.setMinHeight(150);
		pullButton.setMinWidth(450);

		
		// 3. Layout Manager
		BorderPane borderPane = new BorderPane();
		VBox vBoxRight = new VBox();
		HBox hBoxTop = new HBox();
		HBox hBoxBottom = new HBox();
		HBox hBoxPictures = new HBox();
		
		borderPane.setTop(hBoxTop);
		borderPane.setBottom(hBoxBottom);
		borderPane.setCenter(hBoxPictures);
		borderPane.setRight(vBoxRight);
		
		// Top
		hBoxTop.getChildren().add(machineName);
		// Bottom
		hBoxBottom.getChildren().add(pullButton);
		// Center
		hBoxPictures.getChildren().addAll(viewContainer);
		// Right
		vBoxRight.getChildren().add(credits);
		vBoxRight.getChildren().add(creditPoints);
		
		
		pullButton.setOnAction(event -> {
			
			int currentPoints = Integer.parseInt(creditPoints.getText());
			currentPoints -= 10;
			
			
			if (currentPoints < 10) {
				pullButton.setDisable(true);
				
				Alert alertGameOver = new Alert(Alert.AlertType.WARNING);
				alertGameOver.setTitle("Game over");
				//alert.setHeaderText("Welcome to JavaFX!");
				alertGameOver.setHeaderText(null); // Notwendig ansonsten wird der Standard Konstrktor mit "Meldung!" generiert
				alertGameOver.setContentText("Haram, deine Zeit ist um.");
				alertGameOver.showAndWait();
			}
			
			// Slots neue Zufallswerte geben
			Random rnd = new Random();
			int slot_1 = rnd.nextInt(3);
			int slot_2 = rnd.nextInt(3);
			int slot_3 = rnd.nextInt(3);
			
			// insertCoin Sound abspielen
			insertCoin.play();
			
			// Bilder
			switch(slot_1) {
				case 0: viewSlot_1.setImage(imageBanane); break;
				case 1: viewSlot_1.setImage(imageKirsche); break;
				case 2: viewSlot_1.setImage(imageGold); break;
			} // switch - slot_1 Bild ändern
			
			switch(slot_2) {
			case 0: viewSlot_2.setImage(imageBanane); break;
			case 1: viewSlot_2.setImage(imageKirsche); break;
			case 2: viewSlot_2.setImage(imageGold); break;
			} // switch - slot_2 Bild ändern
			
			switch(slot_3) {
			case 0: viewSlot_3.setImage(imageBanane); break;
			case 1: viewSlot_3.setImage(imageKirsche); break;
			case 2: viewSlot_3.setImage(imageGold); break;
			} // switch - slot_3 Bild ändern
			
			// Übereinstimmung aller Slots prüfen
			if(slot_1 == slot_2 && slot_2 == slot_3) {
				// IconMatch Sound abspielen
				IconMatch.play();
				
				switch(slot_1) {
					case 0: currentPoints += 10; break;
					case 1: currentPoints += 25; break;
					case 2: currentPoints += 50; break;
				} // switch - je Übereinstimmung Punkte hinzufügen
				
				Alert alertMatch = new Alert(Alert.AlertType.WARNING);
				alertMatch.setTitle("DING DING DING");
				//alert.setHeaderText("Welcome to JavaFX!");
				alertMatch.setHeaderText(null); // Notwendig ansonsten wird der Standard Konstrktor mit "Meldung!" generiert
				alertMatch.setContentText("Du hast haram Geld gewonnen, schäm dich!");
				alertMatch.showAndWait();
			} // if - Übereinstimmung prüfen
			
			// Credits aktualisieren
			creditPoints.setText("" + currentPoints);
		});
		
		
		// 2. Scene
		Scene scene = new Scene(borderPane, 600, 300);
		
		// 1. Stage
		Stage stage = new Stage();
		
		stage.setScene(scene);
		stage.getIcons().add(new Image(new FileInputStream("src/Gold.png")));
		stage.setTitle("SlotMachine");
		stage.setResizable(false); // Fenster fixe größe
		stage.show();
		
	} // start()
	
} // class SlotMachine
