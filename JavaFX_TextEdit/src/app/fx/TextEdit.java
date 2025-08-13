package app.fx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TextEdit extends Application {

	public static void main(String[] args) {

		launch(args);

	}

	@Override
	public void start(Stage arg0) throws Exception {
		
		// 1. Stage Hier oben damit es alle nutzen können
		Stage stage = new Stage();
		
		// Elements
//		Button btnOpen = new Button("Open File");
//		Button btnWrite = new Button("Write to File");
//		Button btnClose = new Button("Close");
		
		TextArea textArea = new TextArea();
		Label statusLabel = new Label("Status");
		
		// Menu Bar
		MenuBar menuBar = new MenuBar();
		
		Menu mFile = new Menu("File");
		Menu mAbout = new Menu("About");
		
		Menu mSubHelpMenu = new Menu("Help");
		
		MenuItem itemOpen = new MenuItem("Open File");
		MenuItem itemWrite = new MenuItem("Write to File");
		MenuItem itemClose = new MenuItem("Exit");
		
		MenuItem itemAbout = new MenuItem("About");
		MenuItem itemContactItem = new MenuItem("Contact");
		
		SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
		
		// File Menu
		mFile.getItems().add(itemOpen);
		mFile.getItems().add(itemWrite);
		mFile.getItems().add(separatorMenuItem);
		mFile.getItems().add(itemClose);
		
		// About Menu
		mAbout.getItems().add(itemAbout);
		mAbout.getItems().add(mSubHelpMenu);
		
		// About Menu -> SubHelp Menu
		mSubHelpMenu.getItems().add(itemContactItem);
		
		menuBar.getMenus().add(mFile);
		menuBar.getMenus().add(mAbout);
		
		// FileChooser (öffnet ein Fenster(Explorer) um nach Datei zu suchen)
		// Wir erstellen die regeln welche Art von Datei er suchen kann
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open JFX File");
		fileChooser.setInitialFileName("jfxText.txt");
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("All Files", "*.*"),		// zeigt alle Daten
				new FileChooser.ExtensionFilter("Text Files", "*.txt"),	// zeigt nur .txt Daten
				new FileChooser.ExtensionFilter("HTML Files", "*.html")	// zeigt nur .html Daten
				);
		
		// EventHandler
		mAbout.setOnAction(event -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("About JFX Editor");
			alert.setHeaderText(null);
			alert.setContentText("JFX Text Editor\nVersion: 0.0.1\nAuthor: Atakan");
			alert.showAndWait();
		});
		
		// Datei öffnen/lesen
		itemOpen.setOnAction(event -> {
			// ACHTUNG OPEN VERSION fileChooser.showOpenDialog(stage); gibt uns den Pfad für selectedFile
			File selectedFile = fileChooser.showOpenDialog(stage);
			// Kontrollieren ob Auswahl auch ein Inhalt/Pfad besitzt
			if(selectedFile != null) {
				// erstellt uns von der ausgewählten Datei den absoluten Pfad bis C:
				String filePath = selectedFile.getAbsolutePath();
				// Am Bottom unseres Fenster geben wir als Label unser absoluten Pfad wieder als Feedback
				statusLabel.setText("Status: OpenFile, Path " + filePath);
				
				// Pfad wird readFileData gegeben und 
				// Dateiinhalt wird gelesen und in die TextArea direkt gesetzt / per return von der Methode gegeben
		        textArea.setText(readFileData(selectedFile));
			}
		});
		
		// Datei speichern/schreiben
		itemWrite.setOnAction(event -> {
			// ACHTUNG SAVE VERSION fileChooser.showSaveDialog(stage); gibt uns den Pfad für selectedFile SPEICHERN/ERSTELLEN
		    File selectedFile = fileChooser.showSaveDialog(stage);
		    
		    if (selectedFile != null) {
		    		// erstellt uns von der ausgewählten Datei den absoluten Pfad bis C:
		        String filePath = selectedFile.getAbsolutePath();
		        // Am Bottom unseres Fenster geben wir als Label unser absoluten Pfad wieder als Feedback
		        statusLabel.setText("Status: SaveFile, Path " + filePath);

		        // Inhalt von TextArea holen per textArea.getText()
		        	// In Datei schreiben welche vorher ausgewählt wurde Pfad 
		        // bzw. hintugefügt absolute Pfad ist in selectedFile
		        writeDataToFile(selectedFile, textArea.getText());
		    }
		});
		
		itemClose.setOnAction(event -> {
			stage.close();
		});
		
		// 3. Layout
//		GridPane gridPane = new GridPane();
		BorderPane borderPane = new BorderPane();
		
		borderPane.setTop(menuBar);
		borderPane.setCenter(textArea);
		borderPane.setBottom(statusLabel);
		
//		gridPane.add(btnOpen, 0, 0);
//		gridPane.add(btnWrite, 1, 0);
//		gridPane.add(btnClose, 2, 0);
//		gridPane.setHgap(10);
//		gridPane.setVgap(5);
		
		// Start: x = 0, y = 1 span/Spanne bis 3, 1
//		gridPane.add(textArea, 0, 1, 3, 1);
//		gridPane.setGridLinesVisible(true);
		
		
		
		// 2. Scene
		Scene scene = new Scene(borderPane, 600, 500);
				
		
		// 1. Stage
//		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("JavaFX TicTacTo");
		stage.setResizable(false);
		stage.show();
		
	}
	
	// Eigene Methode um Datei zu öffnen/lesen
	private static String readFileData(File file) {
		// String Platzhalter für den Inhalt/Content den wir aus der Datei lesen
		String data = ""; // unsere Daten halt noch ist nix drin aber es steht bereit
		
		try {
			// Mit Scanner lesen wir diesmal nicht die Tastatur mit System.in sondern wir lesen den Pfad der in file hinterlegt ist
			// Der Pfad gibt uns nur das Ziel wovon wir lesen also wir lesen von der Datei die dort hinterlegt ist Pfad+Datei
			Scanner scanner = new Scanner(file);
			// while/solange noch was drinne ist LIES
			while(scanner.hasNextLine()) {
				// Liest Char für Char von der Datei damit bereits gelesene Werte nicht gelöscht werden
				// sagen wir nimm das was bereits existiert und füge den kommenden Char hinzu UND NICHT überschreibe ihn jedesmal SONDERN FÜGE HINZU
				// man könnte auch mit date += scanner.nextLine() + "\n"; abkürzen
				data = data + scanner.nextLine() + "\n";
				// System.out.println(data);
			}
			return data;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	// Eigene Methode um Datei zu schreiben benötigt file Pfad + String(Text Inhalt)
	private static void writeDataToFile(File file, String data) {
		try {
			// FileWriter bekommt durch File den Pfad + Datei wo er schreiben soll
			FileWriter fileWriter = new FileWriter(file);
			// schreibt den String Inhalt zur Datei dessen Pfad bereits bekannt ist
			fileWriter.write(data);
			// schließt den Stream
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
