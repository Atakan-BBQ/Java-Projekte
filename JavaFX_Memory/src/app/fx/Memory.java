package app.fx;

// Wenn einiges nicht klar ist einfach zum testen import ausklammern 
// dann sieht man zu was es gehört
import javafx.application.Application;	
import javafx.scene.Scene;				
import javafx.scene.control.Button;		
import javafx.scene.layout.GridPane;		
import javafx.scene.text.Font;			
import javafx.scene.text.FontWeight;		
import javafx.stage.Stage;				
import javafx.animation.PauseTransition;	// Timer der nach einer definierten Dauer einen Event-Handler ausführt
import javafx.util.Duration;				// Zeitangabe für PauseTransition (Wichtig: Javafx.util.Duration Version)
import java.util.ArrayList;				// Dynamisches Array zur Speicherung der Button-Werte
import java.util.Collections;			// Für Collections.shuffle()
import java.util.List;					// Interface für die Liste der Button-Werte


public class Memory extends Application {

	// Zwischenspeicher für den ersten angeklickten Button
	private static int tmpUserData = -1; // -1 reset bzw. ungewählter Zustand
	private static Button tmpButton;
	private static boolean busy = false; // Blockiert Klicks während Timer läuft

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		// Schriftart für die Buttons
		Font customFont = Font.font("Arial", FontWeight.BOLD, 20);

		// 3x4 = 12 Buttons
		Button[] buttons = new Button[12];

		// 6 Paare (0–5) zweimal jede Zahl
		List<Integer> valuesList = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			valuesList.add(i); // Paar Nr1
			valuesList.add(i); // paar Nr2
		} // for - Paare erstellen

		// Liste mischen damit Paare Nr1 und Nr2 nicht hintereinander sind
		Collections.shuffle(valuesList);

		// Layout Manager: 3 Spalten, 4 Reihen
		GridPane gridPane = new GridPane();
		gridPane.setHgap(15);
		gridPane.setVgap(15);

		// Buttons mit jeweilige Einstellung erstellen und dem Array zuweisen
		for (int i = 0; i < buttons.length; i++) {
			Button button = new Button("?");
			button.setPrefSize(100, 100);
			button.setFont(customFont);
			button.setUserData(valuesList.get(i)); // speichert den versteckten Wert im Button

			// Spalte % und Zeile / wegen int glatte Zahlen
			int col = i % 3;
			int row = i / 3;
			gridPane.add(button, col, row);

			button.setOnAction(event -> pressButtonLogic(button)); // Gesamte Logik SIEHE METHODE
			buttons[i] = button; // Button Objekt dem Zeiger im Array übergeben
		} // for - Buttons einstellen und zuweisen

		// Scene und Stage
		Scene scene = new Scene(gridPane, 330, 450);
		stage.setScene(scene);
		stage.setTitle("Memory");
		stage.show();
	} // start()

	private static void pressButtonLogic(Button p_button) {
		// Prüft ob Timer läuft oder der Button bereits aufgedeckt ist != "?"
		if (busy || !p_button.getText().equals("?")) {
			return;
		} // if - prüft ob aktiv ist - Wenn ja abbruch (NACHTRÄGLICH erweitert/gesichert mit setDisable(true))

		// Wert des Buttons auslesen
		int buttonUserData = (int) p_button.getUserData();
		p_button.setText(String.valueOf(buttonUserData)); // Textwert Spieler sichtbar machen
		p_button.setDisable(true); // sofort deaktivieren um Doppelclick zu verhindern

		// Wenn -1 nichts ausgewählt für ERSTEN Klick
		if (tmpUserData == -1) {
			tmpUserData = buttonUserData; // Button Wert merken
			tmpButton = p_button; // Button selbst merken und referenz zum Objekt herstellen
		} else {
			// Wenn nicht -1 dann ist es der ZWEITE Klick
			// Wenn RICHTIGES Paar dann bleiben beide Buttons deaktiviert
			if (tmpUserData == buttonUserData) {
				tmpButton.setDisable(true);
				tmpUserData = -1; // reset tmpWert
				tmpButton = null; // reset tmpButton
			} else {
				// Wenn FALSCHES Paar dann die Buttons kurz sichtbar lassen damit man diese noch
				// lesen kann
				busy = true; // Blockiert weitere Klicks während der Pause

				// Timer-Objekt erstellen welches auf 1 Sek. Timer eingestellt ist
				PauseTransition pause = new PauseTransition(Duration.seconds(1));

				// Dieser Codeblock wird ausgeführt sobald pause.play() ausgeführt wird
				// Logik wird aber NACHDEM Timer abgelaufen/beendet ist erst ausgeführt
				pause.setOnFinished(e -> {
					// Beide Buttons wieder verstecken für das Memory-Spiel
					p_button.setText("?");
					tmpButton.setText("?");
					// Buttons wieder aktivieren damit sie erneut geklickt werden können
					p_button.setDisable(false);
					tmpButton.setDisable(false);

					// Zwischenspeicher zurücksetzen damit nächstes Paar ausgewählt werden kann
					tmpUserData = -1;
					tmpButton = null;
					busy = false; // Timer vorbei - Klicks wieder erlaubt
				});

				// Startet 1 Sek. Timer anschließend führt er pause.SetOnFinished aus
				pause.play();
			} // if - RICHTIGE oder FALSCHE Paar erwischt
		} // if - 1. oder 2. Button Klick
	} // pressButtonLogic()
} // class Memory
