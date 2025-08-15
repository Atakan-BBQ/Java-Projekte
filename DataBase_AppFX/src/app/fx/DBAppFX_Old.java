package app.fx;  // Das Package, in dem die Datei liegt. Packages helfen, Klassen zu organisieren.

import java.sql.*;  // Importiert SQL-Klassen, z.B. für Datenbankverbindungen. Im aktuellen Code wird es aber noch nicht verwendet.
import javafx.application.Application;  // Importiert die JavaFX-Basis-Klasse für eine GUI-Anwendung.
import javafx.scene.Scene;  // Importiert die Scene-Klasse, die den Inhalt des Fensters enthält.
import javafx.scene.control.TableColumn;  // Importiert die Klasse für Spalten in einer Tabelle.
import javafx.scene.control.TableView;  // Importiert die Klasse für Tabellen.
import javafx.scene.control.cell.PropertyValueFactory;  // Importiert Hilfsklasse, um Objekte automatisch den Spalten zuzuordnen.
import javafx.scene.layout.VBox;  // Importiert ein vertikales Layout.
import javafx.stage.Stage;  // Importiert das Hauptfenster der Anwendung.

public class DBAppFX_Old extends Application {  // DBAppFX erbt von Application, also ist es eine JavaFX-App

    // Main-Methode – Startpunkt der Java-Anwendung
    public static void main(String[] args) {
        launch(args);  // startet die JavaFX-Anwendung und ruft die start()-Methode auf
    }

    // Die start()-Methode wird automatisch aufgerufen, wenn die Anwendung startet
    @Override
    public void start(Stage stage) throws Exception {

        // 1. Erstelle eine Tabelle (TableView)
    		// Hinweis: Generischer Typ fehlt hier (TableView<Guest> wäre sauberer).
    		//TableView tableView = new TableView();
        TableView<Guest> tableView = new TableView<>(); // TableView zeigt eine Liste von Objekten an

        // 2. Layout: VBox stapelt die Elemente vertikal
        VBox vBox = new VBox(tableView);

        // 3. Spalten erstellen (guest_id, name, phone, email)
        // Jede Spalte ist vom Typ TableColumn<ObjektTyp, SpaltenTyp>
        /*	Was passiert hier?
			Wir erstellen vier Spalten für die Tabelle.
			TableColumn<Guest, String>
			<Guest> → Typ des Objekts, das in der Tabelle angezeigt wird (also unsere Klasse Guest).
			<String> → Datentyp, der in dieser Spalte angezeigt wird.
			new TableColumn<>("Name") → "Name" ist der Text, der oben in der Spaltenüberschrift angezeigt wird.
         */
        TableColumn<Guest, String> guest_id = new TableColumn<>("Guest_ID");
        TableColumn<Guest, String> name = new TableColumn<>("Name");
        TableColumn<Guest, String> phone = new TableColumn<>("Phone");
        TableColumn<Guest, String> email = new TableColumn<>("Email");

        // 4. Verknüpfe die Spalten mit den Eigenschaften der Guest-Klasse
        // PropertyValueFactory sucht automatisch nach "getGuest_id()", "getName()", ...
        /* 	Was passiert hier?
			Wir sagen jeder Spalte, welches Feld des Guest-Objekts angezeigt werden soll.
			PropertyValueFactory<>("guest_id")
			Sucht automatisch die Methode getGuest_id() in der Klasse Guest.
			Ruft diesen Wert für jede Zeile ab und zeigt ihn in der entsprechenden Spalte an.
			Wichtig: Die Strings müssen genau mit den Getter-Methoden übereinstimmen.
         */
        guest_id.setCellValueFactory(new PropertyValueFactory<>("guest_id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        // WICHTIG: PropertyValueFactory funktioniert nur, 
        // wenn die Getter-Methoden existieren und genau so heißen wie die Strings, die du übergib

        // 5. Füge die Spalten zur Tabelle hinzu (Ohne diese Zeile hätte die Tabelle keine Spalten.)
        tableView.getColumns().addAll(guest_id, name, phone, email);

        // 6. Füge ein Beispielobjekt (Gast) zur Tabelle hinzu
        tableView.getItems().add(new Guest(12, "Salim", "12345678", "email@example.com"));
        // ACHTUNG LIES UNTEN ZUM VERGLEICH! Wir speichern Objekte! keine einzelnen Werte
        tableView.getItems().add(new Guest(13, "Atakan", "12345678", "Atakan@example.com"));
        
        // HINWEIS: Die folgende Zeile ist falsch, daher auskommentiert:
        // HIER DER VERGLEICH:
        // TableView speichert Objekte, keine Einzelwerte. Darum funktioniert addAll(12, "Atakan", ...) nicht.
        // tableView.getItems().addAll(12, "Atakan", "12345678", "Atakan@example.com");
        // Fehler: addAll() erwartet eine Liste von Objekten vom Typ Guest, nicht einzelne Werte

        // 7. Szene erstellen und der Stage (dem Fenster) zuweisen
        Scene scene = new Scene(vBox, 500, 400);

        stage.setScene(scene);  // Szene setzen
        stage.setTitle("DataBase FX!");  // Fenster-Titel setzen
        stage.show();  // Fenster anzeigen
        
        /*	Zusammenfassung:
			Tabelle erstellen → TableView.
			Layout erstellen → VBox.
			Spalten erstellen → TableColumn<Guest, String>.
			Spalten mit Daten aus der Guest-Klasse verbinden → PropertyValueFactory.
			Spalten zur Tabelle hinzufügen → getColumns().addAll(...).
			Zeilen mit Objekten füllen → getItems().add(...).
         */
    }
}
