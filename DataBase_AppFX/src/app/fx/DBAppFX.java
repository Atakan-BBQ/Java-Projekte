package app.fx;  // Das Package, in dem die Datei liegt. Packages helfen, Klassen zu organisieren.

import java.sql.*;  // Importiert SQL-Klassen, z.B. für Datenbankverbindungen. Im aktuellen Code wird es aber noch nicht verwendet.
import javafx.application.Application;  // Importiert die JavaFX-Basis-Klasse für eine GUI-Anwendung.
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;  // Importiert die Scene-Klasse, die den Inhalt des Fensters enthält.
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;  // Importiert die Klasse für Spalten in einer Tabelle.
import javafx.scene.control.TableView;  // Importiert die Klasse für Tabellen.
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;  // Importiert Hilfsklasse, um Objekte automatisch den Spalten zuzuordnen.
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;  // Importiert ein vertikales Layout.
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;  // Importiert das Hauptfenster der Anwendung.

public class DBAppFX extends Application {  // DBAppFX erbt von Application, also ist es eine JavaFX-App
	
	private Connection conn = null;

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
        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox(tableView);
        
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        
        borderPane.setCenter(vBox);
        borderPane.setTop(gridPane);
        borderPane.setAlignment(gridPane, Pos.CENTER);
        
        Font customFont = Font.font("Arial", FontWeight.BOLD, 15);
        Label guestIdLabel = new Label("Guest ID");
        TextField guestIdTF = new TextField();
        guestIdLabel.setFont(customFont);
        Label nameLabel = new Label("Name");
        TextField nameTF = new TextField();
        nameLabel.setFont(customFont);
        Label phoneLabel = new Label("Phone");
        TextField phoneTF = new TextField();
        phoneLabel.setFont(customFont);
        Label emailLabel = new Label("Email");
        TextField emailTF = new TextField();
        emailLabel.setFont(customFont);
        
        Button addBtn = new Button("Add");
        addBtn.setFont(customFont);
        
        gridPane.add(guestIdLabel, 1, 0);
        gridPane.add(guestIdTF, 1, 1);
        gridPane.add(nameLabel, 2, 0);
        gridPane.add(nameTF, 2, 1);
        gridPane.add(phoneLabel, 3, 0);
        gridPane.add(phoneTF, 3, 1);
        gridPane.add(emailLabel, 4, 0);
        gridPane.add(emailTF, 4, 1);
        
        HBox hBox = new HBox();
        hBox.getChildren().add(addBtn);
        // gridPane.setGridLinesVisible(true);
        hBox.setPadding(new Insets(0, 0, 15, 10));
        hBox.setAlignment(Pos.CENTER_RIGHT);
        gridPane.add(hBox, 0, 2 ,5,1);
        
        

        // 3. Spalten erstellen (guest_id, name, phone, email)
        // Jede Spalte ist vom Typ TableColumn<ObjektTyp, SpaltenTyp>
        /*	Was passiert hier?
			Wir erstellen vier Spalten für die Tabelle.
			TableColumn<Guest, String>
			<Guest> → Typ des Objekts, das in der Tabelle angezeigt wird (also unsere Klasse Guest).
			<String> → Datentyp, der in dieser Spalte angezeigt wird.
			new TableColumn<>("Name") → "Name" ist der Text, der oben in der Spaltenüberschrift angezeigt wird.
         */
        TableColumn<Guest, Integer> guest_id = new TableColumn<>("Guest_ID");
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
        guest_id.setMinWidth(160);
        name.setMinWidth(160);
        phone.setMinWidth(160);
        email.setMinWidth(160);
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

        
        
        // ------------------------------------------------------------
        // Datenbanken
        
 		String dbUrl = "jdbc:sqlite:src/hotel.db";
 		
 		// 2. Connection conn = DriverManager.getConnection(dbUrl);
 		try {
 			
 			conn = DriverManager.getConnection(dbUrl);
 			
 					if(conn != null) {
 				System.out.println("Connected to the database");
 				System.out.println(conn);
 			}
 			
 			
 			// SQL Befehl was genommen werden soll
 			String sql = "SELECT guest_id, name, phone, email FROM Guests";
 			//
 			// ACHTUNG: EIN! Statement DARF NUR EIN ResultSet GEBEN !!
 			// Wenn man 2 oder mehr gleichzeitig benötigt dann benötigen diese ein eigenes Statement!
 			Statement stmt = conn.createStatement();
 			ResultSet rs = stmt.executeQuery(sql);
 			
 			//
 			while(rs.next()) {
 				int sqlGuestId = rs.getInt("guest_id");
 				String sqlName = rs.getString("name");
 				String sqlPhone = rs.getString("phone");
 				String sqlEmail = rs.getString("email");
 				
 				tableView.getItems().add(new Guest(sqlGuestId, sqlName, sqlPhone, sqlEmail));
 			}
 			
 		} catch (SQLException e) {
 			System.out.println("Database connection failed.");
 			e.printStackTrace();
 		}
 		
 		// ------------------------------------------------------------
 		
	 	// Handler
        addBtn.setOnAction(event -> {
        		// SQL Schreib/Write Befehl zum einfügen
        		String sqlInsertString = "INSERT INTO Guests(guest_id, name, phone, email) VALUES(?,?,?,?)";
        		
        		// Prüfen das keine Felder leer sind!
        		// ACHTUNG! EXCEPTION trotzdem möglich wenn guestIdTF keine Zahl ist ODER in Datenbank die Zahl bereits existiert
        		if (!guestIdTF.getText().isEmpty()
        		        && !nameTF.getText().isEmpty()
        		        && !phoneTF.getText().isEmpty()
        		        && !emailTF.getText().isEmpty()) {
        			
        		// Daten zuordnen und im pstmt Puffer speichern
        		try {
				PreparedStatement pstmt = conn.prepareStatement(sqlInsertString);
				pstmt.setInt(1, (Integer.parseInt(guestIdTF.getText())));
				pstmt.setString(2, (nameTF.getText()));
				pstmt.setString(3, (phoneTF.getText()));
				pstmt.setString(4, (emailTF.getText()));
				// Inhalt vom pstmt Inhalt nun der Datenbank einfügen/schreiben
				pstmt.executeUpdate();
        		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        		
        		// TableView die Textfelder einfügen
        		tableView.getItems().add(new Guest(
        				Integer.parseInt(guestIdTF.getText()), 
        				nameTF.getText(), 
        				phoneTF.getText(), 
        				emailTF.getText()
        		));
        		// Textfelder leeren
        		guestIdTF.setText("");
        		nameTF.setText("");
        		phoneTF.setText("");
        		emailTF.setText("");
        		} else {
        			Alert alert = new Alert(Alert.AlertType.WARNING);
        	        alert.setTitle("Warnung");
        	        alert.setHeaderText(null); // kein Header
        	        alert.setContentText("Die Eingabefelder dürfen nicht leer sein!");
        	        alert.showAndWait();
        		}
        });
        
        
        // 7. Szene erstellen und der Stage (dem Fenster) zuweisen
        Scene scene = new Scene(borderPane, 800, 600);

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
