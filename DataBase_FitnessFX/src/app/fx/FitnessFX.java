package app.fx;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement; 	// PreparedStatement:
									// Wird für parameterisierte SQL-Befehle verwendet!
									// Die SQL-Abfrage enthält Platzhalter (?) die man später füllen kann
									// sicherer gegen SQL-Injection

// import java.sql.Statement;		// Statement: 
// Für einfache statische SQL-Abfragen (fixer String) ohne Parameter kein (?)

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class FitnessFX extends Application {
	
	private String dbUrl = "jdbc:sqlite:src/fitness.db";
	private Connection connection = null; // LIES! Connection Dokumentation
	
	// fitness.db
    // id, vorname, nachname, email, mitgliedschafts_typ, registrierungsdatum
	private TextField iDTF = null;
	private TextField vornameTF = null;
	private TextField nachnameTF = null;
	private TextField emailTF = null;
	private TextField mitgliedschafts_typTF = null;
	private TextField registrierungsdatumTF = null;

	public static void main(String[] args) {
		
		launch(args);
		
	} // start()

	@Override
	public void start(Stage stage) throws Exception {
		
		// Verbindung zur SQLite-Datenbank aufbauen
		connectDatabase();

        // Erstelle eine Tabelle (TableView)
        TableView<DatabaseManager> tableView = new TableView<>(); // TableView zeigt eine Liste von Objekten an

        // Layout
        BorderPane borderPane = new BorderPane();
        VBox vBoxForTable = new VBox(tableView);
        GridPane gridPane = new GridPane();
        HBox hBoxForButtons = new HBox();
        VBox vBoxLeftButtons = new VBox();
        VBox vBoxRightButtons = new VBox();
        
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        borderPane.setCenter(vBoxForTable);
        borderPane.setTop(gridPane);
        borderPane.setAlignment(gridPane, Pos.CENTER);
        
        // Font
        Font customFont = Font.font("Arial", FontWeight.BOLD, 15);
        
        // fitness.db
        // id, vorname, nachname, email, mitgliedschafts_typ, registrierungsdatum
        
        // Label
        Label iDLabel = new Label("ID");
        Label vornameLabel = new Label("Vorname");
        Label nachnameLabel = new Label("Nachname");
        Label emailLabel = new Label("Email");
        Label mitgliedschafts_typLabel = new Label("Mitgliedschaftstyp");
        Label registrierungsdatumLabel = new Label("Registrierungsdatum");

        iDLabel.setFont(customFont);
        vornameLabel.setFont(customFont);
        nachnameLabel.setFont(customFont);
        emailLabel.setFont(customFont);
        mitgliedschafts_typLabel.setFont(customFont);
        registrierungsdatumLabel.setFont(customFont);

        // TextField
        TextField iDTF = new TextField();
        TextField vornameTF = new TextField();
        TextField nachnameTF = new TextField();
        TextField emailTF = new TextField();
        emailTF.setMinWidth(195);				// kleine visuelle ausnahme
        TextField mitgliedschafts_typTF = new TextField();
        TextField registrierungsdatumTF = new TextField();
        
        // Button
        Button btnAdd = new Button("Kunde hinzufügen");
        Button btnUpdate = new Button("Kunde aktualisieren");
        Button btnDelete = new Button("Kunde löschen");
        Button btnGetAll = new Button("Daten laden");

        btnAdd.setFont(customFont);
        btnUpdate.setFont(customFont);
        btnDelete.setFont(customFont);
        btnGetAll.setFont(customFont);
        
        btnAdd.setMinWidth(200);
        btnUpdate.setMinWidth(200);
        btnGetAll.setMinWidth(200);
        btnDelete.setMinWidth(200);
        
        // Nodes im gridPane anordnen
        gridPane.setPadding(new Insets(10, 0, 10, 0));
        gridPane.add(iDLabel, 1, 0);
        gridPane.add(iDTF, 1, 1);
        gridPane.add(vornameLabel, 2, 0);
        gridPane.add(vornameTF, 2, 1);
        gridPane.add(nachnameLabel, 3, 0);
        gridPane.add(nachnameTF, 3, 1);
        gridPane.add(emailLabel, 4, 0);
        gridPane.add(emailTF, 4, 1);
        gridPane.add(mitgliedschafts_typLabel, 5, 0);
        gridPane.add(mitgliedschafts_typTF, 5, 1);
        gridPane.add(registrierungsdatumLabel, 6, 0);
        gridPane.add(registrierungsdatumTF, 6, 1);
        
        // Spacer zwischen links und rechts
        // Erzeuge ein unsichtbares Platzhalter-Element
        Region spacer = new Region();
	    // Lässt den Spacer automatisch den horizontalen Raum ausfüllen
	    // Spacer sorgt dafür dass der Button rechts in der HBox bleibt
        HBox.setHgrow(spacer, Priority.ALWAYS); // ALWAYS maximalen nimmt Platz/Space
        hBoxForButtons.setSpacing(10);
        vBoxLeftButtons.setSpacing(10);
        vBoxRightButtons.setSpacing(10);

        // Einfügen
        hBoxForButtons.getChildren().addAll(vBoxLeftButtons, spacer, vBoxRightButtons);
        // gridPane.setGridLinesVisible(true);
        gridPane.add(hBoxForButtons, 0, 2 ,7,1);
        vBoxLeftButtons.getChildren().addAll(btnAdd, btnUpdate, btnGetAll);
        vBoxRightButtons.getChildren().add(btnDelete);
        // Anordnen
        hBoxForButtons.setAlignment(Pos.TOP_LEFT);
        vBoxRightButtons.setAlignment(Pos.CENTER_RIGHT);
        vBoxForTable.setAlignment(Pos.TOP_CENTER);
        
        // Spalten erstellen (guest_id, name, phone, email)
        // Jede Spalte ist vom Typ TableColumn<ObjektTyp, SpaltenTyp>
        TableColumn<DatabaseManager, Integer> id = new TableColumn<>("ID");
        TableColumn<DatabaseManager, String> vorname = new TableColumn<>("Vorname");
        TableColumn<DatabaseManager, String> nachname = new TableColumn<>("Nachname");
        TableColumn<DatabaseManager, String> email = new TableColumn<>("Email");
        TableColumn<DatabaseManager, String> mitgliedschafts_typ = new TableColumn<>("Mitgliedschaftstyp");
        TableColumn<DatabaseManager, String> registrierungsdatum = new TableColumn<>("Registrierungsdatum");

        // Breite festlegen
        id.setMinWidth(160);
        vorname.setMinWidth(160);
        nachname.setMinWidth(160);
        email.setMinWidth(200);
        mitgliedschafts_typ.setMinWidth(160);
        registrierungsdatum.setMinWidth(160);
        
        // Verknüpfe die Spalten mit den Eigenschaften der DatabaseManager-Klasse
        // PropertyValueFactory sucht automatisch nach "getId()", "getVorname()", ...
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        vorname.setCellValueFactory(new PropertyValueFactory<>("vorname"));
        nachname.setCellValueFactory(new PropertyValueFactory<>("nachname"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        mitgliedschafts_typ.setCellValueFactory(new PropertyValueFactory<>("mitgliedschafts_typ"));
        registrierungsdatum.setCellValueFactory(new PropertyValueFactory<>("registrierungsdatum"));
        // WICHTIG: PropertyValueFactory funktioniert nur
        // wenn die Getter-Methoden existieren und genau so heißen wie die Strings z.B. vorname => getVorname
        
        // Füge die Spalten zur Tabelle hinzu (Ohne diese Zeile hätte die Tabelle keine Spalten)
        tableView.getColumns().addAll(id, vorname, nachname, email, mitgliedschafts_typ, registrierungsdatum);

        // Test: Füge ein Beispielobjekt (DatabaseManager) zur Tabelle hinzu
        // tableView.getItems().add(new DatabaseManager(77, "Thomas", "Mueller", "TM@example.com", "Premium", "24.12.2023"));
        
 		// -----------------------------------------------------------------------------
        
        // Eventhandler
        
        // "Kunde hinzufügen" Button Logik
        btnAdd.setOnAction(event -> {
            // prüft ob alle Felder gefüllt sind
        		// Info: ID nicht nötig weil id int AUTO_INCREMENT
            if (!vornameTF.getText().isEmpty() &&
                !nachnameTF.getText().isEmpty() &&
                !emailTF.getText().isEmpty() &&
                !mitgliedschafts_typTF.getText().isEmpty() &&
                !registrierungsdatumTF.getText().isEmpty()) {

                try {
                    // neuen Kunden erstellen (ohne ID da DB Autoincrement übernimmt)
                    DatabaseManager kunde = new DatabaseManager(
                        0, // ID wird von der DB gesetzt
                        vornameTF.getText(),
                        nachnameTF.getText(),
                        emailTF.getText(),
                        mitgliedschafts_typTF.getText(),
                        registrierungsdatumTF.getText()
                    );

                    // in DB einfügen - weitere Logik siehe Methode
                    addKunde(kunde);

                    // Tabelle neu laden (aktuellen Stand holen)
                    tableView.getItems().clear();
                    tableView.getItems().addAll(getAllKunden());

                    // Textfelder leeren
                    vornameTF.clear();
                    nachnameTF.clear();
                    emailTF.clear();
                    mitgliedschafts_typTF.clear();
                    registrierungsdatumTF.clear();

                } catch (SQLException ex) {
                    showError("Fehler beim Hinzufügen: " + ex.getMessage());
                } // TryCatch - SQL read/write/connection Schutz
            } else {
                showError("Bitte alle Felder ausfüllen!");
            } // if - Überprüft ob alle Felder Inhalt haben um neune Kunden Datensatz zu erstellen
        });
        
        // -----------------------------------------------------------------------------
        
        // "Kunde aktualisieren" Button Logik
        btnUpdate.setOnAction(event -> {
        		// prüft ob alle Felder gefüllt sind
            if (!iDTF.getText().isEmpty() &&
                !vornameTF.getText().isEmpty() &&
                !nachnameTF.getText().isEmpty() &&
                !emailTF.getText().isEmpty() &&
                !mitgliedschafts_typTF.getText().isEmpty() &&
                !registrierungsdatumTF.getText().isEmpty()) {

                try {
                    int idCheck = Integer.parseInt(iDTF.getText()); // ID aus Textfeld
                    
                    // Neues DatabaseManager Objekt erstellen welches die ID selbe bekommt
                    DatabaseManager updatedKunde = new DatabaseManager(
                        idCheck,
                        vornameTF.getText(),
                        nachnameTF.getText(),
                        emailTF.getText(),
                        mitgliedschafts_typTF.getText(),
                        registrierungsdatumTF.getText()
                    );

                    // In DB updaten - weitere Logik siehe Methode
                    updateKunde(updatedKunde); // Mithilfe ID überprüfen und bei existenz überschreiben / neue Adresse zum Objekt geben

                    // Tabelle neu laden
                    tableView.getItems().clear();
                    tableView.getItems().addAll(getAllKunden());

                    // Textfelder leeren
                    iDTF.clear();
                    vornameTF.clear();
                    nachnameTF.clear();
                    emailTF.clear();
                    mitgliedschafts_typTF.clear();
                    registrierungsdatumTF.clear();

                } catch (NumberFormatException ex) {
                    showError("Die ID muss eine Zahl sein!");
                } catch (SQLException ex) {
                    showError("Fehler beim Aktualisieren: " + ex.getMessage());
                } // TryCatch - SQL read/write/connection Schutz + ob ID int Wert
            } else {
                showError("Bitte alle Felder ausfüllen!");
            } // Überprüft ob alle Felder Inhalt haben + ID eine Zahl ist womit gefiltert wird
        });
        
        // -----------------------------------------------------------------------------
        
        // "Kunde löschen" Button Logik
        btnDelete.setOnAction(event -> {
            DatabaseManager selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    deleteKunde(selected.getId());
                    tableView.getItems().remove(selected); // auch aus Tabelle löschen
                } catch (SQLException ex) {
                    showError("Fehler beim Löschen: " + ex.getMessage());
                } // TryCatch - SQL read/write/connection Schutz
            } else {
                showError("Bitte zuerst einen Kunden in der Tabelle auswählen!");
            } // if - Wenn Datensatz von tableView gewählt wurde lösche es
        });
        
        // -----------------------------------------------------------------------------
        
        // "Daten laden" Button Logik
        btnGetAll.setOnAction(event -> {
            try {
                // Hole alle Kunden aus der DB
                List<DatabaseManager> kunden = getAllKunden();

                // Tabelle leeren
                tableView.getItems().clear();

                // Neue Daten einfügen
                tableView.getItems().addAll(kunden);

            } catch (SQLException ex) {
                showError("Fehler beim Laden aller Kunden: " + ex.getMessage());
            } // TryCatch - SQL read/write/connection Schutz
        });
        
        // -----------------------------------------------------------------------------
        
        
        // 7. Szene erstellen und der Stage (dem Fenster) zuweisen
        Scene scene = new Scene(borderPane, 1020, 600);

        stage.setScene(scene);  // Szene setzen
        stage.setTitle("DataBase FX!");  // Fenster-Titel setzen
        stage.show();  // Fenster anzeigen
        
    } // start()
	
	// Wird ausgeführt wenn Programm geschlossen wird
	@Override
	public void stop() throws Exception {
		super.stop();
	    try {
	    		// Feedback ist in Methode 
	        disconnectDatabase();
	    } catch (SQLException e) {
	        // Über Error Konsole ausgeben
	    		System.err.println("Fehler beim Schließen der Datenbank: " + e.getMessage());
	    } // TryCatch - Beim beenden des Programms SQL Connection schließen oder Meldung geben
	} // stop()
	
	// -----------------------------------------------------------------------------
	
	// Kunde hinzufügen
	public void addKunde(DatabaseManager kunde) throws SQLException { // OPTION 1 throws SQLException {
	    // Formel: "INSERT INTO + Tabellenname + (Spaltennamen, ...) + VALUES + (?, ...)" Info: ? je platzhalter 
		String sqlInsert = "INSERT INTO Kunden (vorname, nachname, email, mitgliedschafts_typ, registrierungsdatum) VALUES (?, ?, ?, ?, ?)";
	    
	    try (PreparedStatement prepStmt = connection.prepareStatement(sqlInsert)) {
	        prepStmt.setString(1, kunde.getVorname());
	        prepStmt.setString(2, kunde.getNachname());
	        prepStmt.setString(3, kunde.getEmail());
	        prepStmt.setString(4, kunde.getMitgliedschafts_typ());
	        prepStmt.setString(5, kunde.getRegistrierungsdatum());
	        
	        prepStmt.executeUpdate();
	        
	    } catch (SQLException e) { // OPTION 2 Problem wird innen geregelt
	        // Lokales Logging
	        System.out.println("Fehler beim Hinzufügen des Kunden: " + e.getMessage());
	        // Ausnahme weitergeben damit der Aufrufer weiß dass etwas schiefgelaufen ist
	        throw e;	// OPTIONAL!! für OPTION 2 kann man trotzdem ein throw zur Außenwelt geben
	        			// So ist es als würden wir OPTION 1 zusätzlich auch nutzen
	    } // TryCatch - SQL INSERT
	} // addKunde()
	
	// -----------------------------------------------------------------------------

    // Kunde aktualisieren
    public void updateKunde(DatabaseManager kunde) throws SQLException {
    		// Formel: "UPDATE + Tabellenname + SET + Spaltenname=?, ... + WHERE + (FILTER: in unseren Beispiel id=?)"
    		// Erklärung jedes ? ist ein Platzhalter der zähler beginnt bei 1 wir können beliebig Spalten mit , hinzufügen
    		// falls wir mehrere Spalten Updaten wollen mit WHERE geben wir ein beliebigen FILTER ein hier fragen wir nach der id Nummer ab
        String sqlUpdate = "UPDATE Kunden SET vorname=?, nachname=?, email=?, mitgliedschafts_typ=?, registrierungsdatum=? WHERE id=?";
        
        try (PreparedStatement prepStmt = connection.prepareStatement(sqlUpdate)) {
            prepStmt.setString(1, kunde.getVorname());
            prepStmt.setString(2, kunde.getNachname());
            prepStmt.setString(3, kunde.getEmail());
            prepStmt.setString(4, kunde.getMitgliedschafts_typ());
            prepStmt.setString(5, kunde.getRegistrierungsdatum());
            prepStmt.setInt(6, kunde.getId());	// Unser Filter mit ID Nummer
            prepStmt.executeUpdate();			// Falls ID nicht existiert wird in nichts geschrieben (so die WHERE Bedingung)
        } // TryCatch - SQL UPDATE
    } // updateKunde()
    
    // -----------------------------------------------------------------------------

    // Kunde löschen
    public void deleteKunde(int id) throws SQLException {
    		// FORMEL: "DELETE FROM + Tabellenname + WHERE + (FILTER: in unseren Beispiel id=?)"
        String sqlDelete = "DELETE FROM Kunden WHERE id=?";
        
        try (PreparedStatement prepStmt = connection.prepareStatement(sqlDelete)) {
            prepStmt.setInt(1, id);
            prepStmt.executeUpdate();
        } // TryCatch - SQL DELETE
    } // deleteKunde()
    
    // -----------------------------------------------------------------------------
    
    // Alle Kunden auslesen
    public List<DatabaseManager> getAllKunden() throws SQLException {
    		// Liste für ALLE Datensätze erstellen
        List<DatabaseManager> kundenListe = new ArrayList<>();
        // Formel: "SELECT + Spaltennamen + FROM + Tabellenname" Info: * für ALLE
        String sqlSelectAll = "SELECT * FROM Kunden";

        try (PreparedStatement prepStmt = connection.prepareStatement(sqlSelectAll);
        		ResultSet rs = prepStmt.executeQuery()) {
            while (rs.next()) {
            	DatabaseManager kunde = new DatabaseManager(
                        rs.getInt("id"),
                        rs.getString("vorname"),
                        rs.getString("nachname"),
                        rs.getString("email"),
                        rs.getString("mitgliedschafts_typ"),
                        rs.getString("registrierungsdatum")
                );
                kundenListe.add(kunde);
            } // while - Solange next (Datensatz) existiert erstelle ein Kunden damit und füge diesen in die Liste hinzu
        } // TryCatch - SQL SELECT * FROM Kunden
        
        return kundenListe;
    } // getAllKunden()
    
    // -----------------------------------------------------------------------------
    
    // Fehler per Alert anzeigen - Kontext per Parameter
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText(null);
        alert.setContentText(message); // Kontext kommt per Parameter
        alert.showAndWait();
    } // showError()
    
    // Verbindung zur SQLite-Datenbank aufbauen
    public void connectDatabase() throws SQLException {
        if (connection == null || connection.isClosed()) {
        	
        	connection = DriverManager.getConnection(dbUrl);
        	
        		if(connection != null && connection.isValid(0)) {
        			System.out.println("Verbindung zur Datenbank hergestellt.");
        			System.out.println(connection);
 			} else {
 				System.out.println("Verbindung zur Datenbank konnte NICHT hergestellt werden.");
 			} // if - Feedback ob positiv oder negative war
        } // if - Überprüft ob connection leer oder geschlossen ist damit eine neue connection(link) gegeben werden kann
    } // connectDatabase()
    
    // Verbindung schließen - nach launch??
    public void disconnectDatabase() throws SQLException {
        if (connection != null && !connection.isClosed()) {
        		connection.close();
            System.out.println("Verbindung zur Datenbank geschlossen.");
        } // if - Wenn es ein Inhalt gibt und dieser nicht geschlossen ist schließe es
    } // disconnectDatabase()
    
} // class FitnessFX
