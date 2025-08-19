package app.fx;

import javafx.application.Application;	
import javafx.scene.Scene;				
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;		
import javafx.scene.text.Font;			
import javafx.scene.text.FontWeight;		
import javafx.stage.Stage;				
import javafx.animation.PauseTransition;	
import javafx.util.Duration;				
import java.util.ArrayList;				
import java.util.Collections;			
import java.util.List;					

public class MemoryV2 extends Application {

	// Zwischenspeicher für den ersten angeklickten Button
	private static int tmpUserData = -1; 
	private static Button tmpButton;
	private static boolean busy = false; 

	// Bilder als Array (Index entspricht Zahl)
	private static Image[] images;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		// Schriftart für die Buttons
		Font customFont = Font.font("Arial", FontWeight.BOLD, 20);
		
		// Bilder laden (liegen im Klassenpfad /resources oder direkt in src)
		Image imgRed    = new Image(getClass().getResourceAsStream("/Rot.png"));
		Image imgBlue   = new Image(getClass().getResourceAsStream("/Blau.png"));
		Image imgYellow = new Image(getClass().getResourceAsStream("/Gelb.png"));
		Image imgGreen  = new Image(getClass().getResourceAsStream("/Grün.png"));
		Image imgBlack  = new Image(getClass().getResourceAsStream("/Schwarz.png"));
		Image imgPurple = new Image(getClass().getResourceAsStream("/Lila.png"));


		// Array für schnellen Zugriff nach Zahl
		images = new Image[] { imgRed, imgBlue, imgYellow, imgGreen, imgBlack, imgPurple };

		// 3x4 = 12 Buttons
		Button[] buttons = new Button[12];

		// 6 Paare (0–5) zweimal jede Zahl
		List<Integer> valuesList = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			valuesList.add(i); 
			valuesList.add(i); 
		}
		Collections.shuffle(valuesList);

		// Layout
		GridPane gridPane = new GridPane();
		gridPane.setHgap(15);
		gridPane.setVgap(15);

		// Buttons erstellen
		for (int i = 0; i < buttons.length; i++) {
			Button button = new Button("?");
			button.setPrefSize(100, 100);
			button.setFont(customFont);
			button.setUserData(valuesList.get(i)); 

			int col = i % 3;
			int row = i / 3;
			gridPane.add(button, col, row);

			button.setOnAction(event -> pressButtonLogic(button));
			buttons[i] = button;
		}

		// Scene und Stage
		Scene scene = new Scene(gridPane, 340, 480);
		stage.setScene(scene);
		stage.setTitle("MemoryV2");
		stage.show();
	} 

	private static void pressButtonLogic(Button p_button) {
		if (busy || !p_button.getText().equals("?")) {
			return;
		}

		int buttonUserData = (int) p_button.getUserData();

		// Bild anzeigen
		ImageView buttonImageView = new ImageView(images[buttonUserData]);
		buttonImageView.setFitWidth(50);
		buttonImageView.setFitHeight(50);
		p_button.setGraphic(buttonImageView);
		p_button.setText(""); 
		p_button.setDisable(true);

		if (tmpUserData == -1) {
			tmpUserData = buttonUserData; 
			tmpButton = p_button; 
		} else {
			if (tmpUserData == buttonUserData) {
				tmpButton.setDisable(true);
				tmpUserData = -1;
				tmpButton = null;
			} else {
				busy = true; 
				PauseTransition pause = new PauseTransition(Duration.seconds(1));

				pause.setOnFinished(e -> {
					// Bilder wieder verstecken
					p_button.setText("?");
					p_button.setGraphic(null);
					p_button.setDisable(false);

					tmpButton.setText("?");
					tmpButton.setGraphic(null);
					tmpButton.setDisable(false);

					tmpUserData = -1;
					tmpButton = null;
					busy = false;
				});

				pause.play();
			}
		}
	}
}
