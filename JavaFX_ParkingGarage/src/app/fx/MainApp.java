package app.fx;

/* VORWORT:
 * Doppelte Kennzeichen werden nicht überprüft
 * Wenn Parkplatz voll ist und ein Auto versucht zu parken wird es von vehiclesOutside trotzdem entfernt
 * Einige Methoden geben immer noch Konsolenzeilen als Feedback aus
 * 
 * Diese und weitere Punkte waren bei der Aufgabe nicht im Fokus da die Übung wo anders lag
 * Diese kleinen wichigen Details habe ich trotzdem ignoiert weil die Übung im Fokus stand
 * statt das alles korrekt funktioniert - Das Bewusstsein ist da aber leider fehlte die Zeit
 * 
 * Es ist etwas chaotisch da Code aus alten Projekt recycelt wurde statt direkt korrekten Code zu schreiben
 * Sollte Zeit in Zukunft existieren werden die Fehlerquellen in Zukunft beseitigt
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainApp extends Application{
	
	ParkingGarage parkingGarage = null;
	List<AbstractVehicle> vehiclesOutside = new ArrayList<>();

	public static void main(String[] args) {
		
		launch(args);
		
	} // main()

	@Override
	public void start(Stage stage) throws Exception {
		
		// 5. Externe Medien oder gemeinsame Fonts
		Font costumLabelFont = Font.font("Arial", FontWeight.MEDIUM, 20);
		Font customButtonFont = Font.font("Arial", FontWeight.BOLD, 15);
		
		// 4. Nodes
		
		// Label
		Label labelParkingData = new Label("Parkhaus Daten");
		Label labelVehicleData = new Label("Fahrzeug Daten");
		Label labelOperations = new Label("Operationen");
		
		Label[] myLabels = {labelParkingData, labelVehicleData, labelOperations}; 
		
		for(Label oneLabel : myLabels) {
			oneLabel.setFont(costumLabelFont);
		}
		
		// Button
		Button btnCreateParkingGarage = new Button("Einlegen");
		Button btnAddCar = new Button("Auto einlegen");
		Button btnAddMotorcycle = new Button("Motorrad einlegen");
		Button btnParakingGarageStatus = new Button("PH Status");
		Button btnPark = new Button("Parken");
		Button btnDriveOut = new Button("Rausfahren");
		Button btnSearch = new Button("Suchen");
		Button btnFilter = new Button("Filtern");
		Button btnGenerator = new Button("Generiere Fahrzeuge");
		
		Button[] myButtons = {btnCreateParkingGarage, btnAddCar, btnAddMotorcycle, btnParakingGarageStatus,
				btnPark, btnDriveOut, btnSearch, btnFilter, btnGenerator};
		
		for(Button oneButton : myButtons) {
			oneButton.setFont(customButtonFont);
			oneButton.setMinWidth(200);
			oneButton.setDisable(true);
		}
		btnCreateParkingGarage.setDisable(false);
		
		// TextField
		TextField tFTotalFloors = new TextField();
		TextField tFSpacesPerFloor = new TextField();
		TextField tFAddCar = new TextField();
		TextField tFAddMotorcycle = new TextField();
		TextField tFPark = new TextField();
		TextField tFDriveOut = new TextField();
		TextField tFSearch = new TextField();
		
		TextField[] myTextFields = {tFTotalFloors, tFSpacesPerFloor, tFAddCar, tFAddMotorcycle,
				tFPark, tFDriveOut, tFSearch};
		
		for(TextField oneTextField : myTextFields) {
			oneTextField.setMinWidth(150);
			oneTextField.setDisable(true);
		}
		tFTotalFloors.setDisable(false);
		tFSpacesPerFloor.setDisable(false);
		
		// TextArea
		TextArea tAParkData = new TextArea();
		TextArea tAFilter = new TextArea();
		
		// Radio Button
		RadioButton rBtnCar = new RadioButton("Auto");
		RadioButton rBtnMotorcycle = new RadioButton("Motorrad");
		rBtnCar.setDisable(true);
		rBtnMotorcycle.setDisable(true);

		// ToggleGroup damit nur ein RadioButton gleichzeitig aktiv ist
        ToggleGroup tGFilter = new ToggleGroup();
        // Radio Button einer Gruppe zuweisen
        rBtnCar.setToggleGroup(tGFilter);
        rBtnMotorcycle.setToggleGroup(tGFilter);

		
		// 3. Layout Manager
		GridPane gridPane = new GridPane();
		//gridPane.setGridLinesVisible(true);
		//gridPane.setAlignment(Pos.TOP_LEFT);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(10,10,10,10));
		
		
		// 3.1 Nodes einfügen
		
		// Labels
		gridPane.add(labelParkingData, 0, 0);
		gridPane.add(labelVehicleData, 0, 2);
		gridPane.add(labelOperations, 0, 5);
		
		// Buttons
		gridPane.add(btnCreateParkingGarage, 2, 1);
		gridPane.add(btnGenerator, 3, 1);
		gridPane.add(btnAddCar, 1, 3);
		gridPane.add(btnAddMotorcycle, 3, 3);
		gridPane.add(btnParakingGarageStatus, 2, 5);
		gridPane.add(btnPark, 1, 6);
		gridPane.add(btnDriveOut, 1, 7);
		gridPane.add(btnSearch, 1, 8);
		gridPane.add(btnFilter, 3, 9);
		
		// TextFields
		gridPane.add(tFTotalFloors, 0, 1);
		gridPane.add(tFSpacesPerFloor, 1, 1);
		gridPane.add(tFAddCar, 0, 3);
		gridPane.add(tFAddMotorcycle, 2, 3);
		gridPane.add(tFPark, 0, 6);
		gridPane.add(tFDriveOut, 0, 7);
		gridPane.add(tFSearch, 0, 8);
		
		// TextAreas
		gridPane.add(tAParkData, 2, 6);
		GridPane.setRowSpan(tAParkData, 3); // Mehrere Zeilen hoch, aber keine ColumnSpan!
		gridPane.add(tAFilter, 0, 10);
		GridPane.setColumnSpan(tAFilter, 4); // TextArea über 4 Spalten strecken
		
		tAParkData.setPrefWidth(200);
		tAParkData.setMaxWidth(600);
//		tAFilter.setPrefWidth(600);
		
		// Radio Buttons
		gridPane.add(rBtnCar, 1, 9);
		gridPane.add(rBtnMotorcycle, 2, 9);
		
		
		// 3.2 Events
		
		// --------------------------------------------------------
		
		// Parkhaus erstellen
		btnCreateParkingGarage.setOnAction(event -> {
			
			try {
				// Zwischenspeicher für Check
				int totalFloors = Integer.parseInt(tFTotalFloors.getText());
		        int spacesPerFloor = Integer.parseInt(tFSpacesPerFloor.getText());

		        // Prüfen ob die Zahlen positiv sind und min. 1 Parkplatz
		        if (totalFloors < 1 || spacesPerFloor < 1) {
		            showAlert("Die Anzahl der Etagen und Parkplätze muss positiv sein! \nUnd beide müssen mindestens den Wert 1 haben");
		            return; // Abbruch, Parkhaus wird nicht erstellt
		        } // if - bei negativen Zahlen abbrechen
				
				parkingGarage = new ParkingGarage(totalFloors, spacesPerFloor);
				tFTotalFloors.setText("");
				tFSpacesPerFloor.setText("");
				
				// aktiviere alle Buttons
				for(Button oneButton : myButtons) {
					oneButton.setDisable(false);
				}
				// ABER deaktivere ParkingGarage
				btnCreateParkingGarage.setDisable(true);
				
				// aktiviere alle TextFields
				for(TextField oneTextField : myTextFields) {
					oneTextField.setDisable(false);
				}
				rBtnCar.setDisable(false);
				rBtnMotorcycle.setDisable(false);
				
				// ABER deaktievere TotalFloor + SpacesPerFloor
				tFTotalFloors.setDisable(true);
				tFSpacesPerFloor.setDisable(true);
				
			} catch(NumberFormatException e) {
				showAlert("Es dürfen nur Zahlen benutzt werden!\n" + e);
			} // TryCatch - Korrekte int Eingabe überprüfen
			
			// Feedback fürs Debuggen TODO: später entfernen
			System.out.println("TotalFloors: " + tFTotalFloors.getText() + "\n" +
			"SpacesPerFloor: " + tFSpacesPerFloor.getText() + "\n" +
					"Erfolgreich erstellt!");
		});
		
		// --------------------------------------------------------
		
		// Auto hinzufügen
		btnAddCar.setOnAction(event -> {
			
			// parkingGarage.parkVehicle(new Car(tFAddCar.getText()));
			vehiclesOutside.add(new Car(tFAddCar.getText()));
			tFAddCar.setText("");
		});
		
		// Motorrad hinzufügen
		btnAddMotorcycle.setOnAction(event -> {
			
			//parkingGarage.parkVehicle(new Car(tFAddMotorcycle.getText()));
			vehiclesOutside.add(new Motorcycle(tFAddMotorcycle.getText()));
			tFAddMotorcycle.setText("");
		});
		
		// --------------------------------------------------------
		
		// Auto im Parkhaus parken
		btnPark.setOnAction(event -> {
			
			// String zwischenspeichern damit man nicht immer wieder ablesen soll bei Iterator
		    String inputFromTF = tFPark.getText().trim();
		    // vehicleToPark ist ein Zwischenpuffer (AbstractVehicle) um von vehiclesOutside Liste zu entfernen 
		    // und zur parkingGarage Liste(intern) hinzuzufügen
		    AbstractVehicle vehicleToPark = null;

		    // Liste mit Iterator verknüpfen
		    Iterator<AbstractVehicle> iteratorVehiclesOutside = vehiclesOutside.iterator();
		    
		    // Iterator SOLANGE durchlaufen bis hasNext 
		    // falls gefunden wird break
		    while (iteratorVehiclesOutside.hasNext()) {
		    		// Übergib das nächste Vehicle um in nächste Schritte abzufragen
		        AbstractVehicle vehicle = iteratorVehiclesOutside.next();
		        // Überprüfe ob Kennzeichen übereinstimmt
		        if (vehicle.getLicensePlate().equalsIgnoreCase(inputFromTF)) {

		            vehicleToPark = vehicle; // Fahrzeug dem Puffer zuweisen
		            iteratorVehiclesOutside.remove(); // Aus der vehicleOutside Liste entfernen
		            break;
		        }
		    } // while - Solange bis gefunden wird oder Ende erreicht wurde

		    // Aktion bei: Fahrzeug gefunden / nicht gefunden
		    if (vehicleToPark != null) {
		    		// Übergibt das Puffer Fahrzeug nun zur internen Parkhaus Liste
		        parkingGarage.parkVehicle(vehicleToPark);
		    } else {
		        showAlert("Fahrzeug nicht gefunden!");
		    } // if - Parken oder Feedback

		    tFPark.setText("");
		});
		
		// --------------------------------------------------------
		
		// VERSION 1 - Auto wird aus Parkhaus entfernt aber der Liste vehiclesOutside wieder eingefügt

		// Fahrzeug aus dem Parkhaus rausfahren/entfernen
		btnDriveOut.setOnAction(event -> {
		    // SIEHE "Auto im Parkhaus parken da" da Code ähnlich ist nur Gegenteil

		    String inputFromTF = tFDriveOut.getText().trim();
		    AbstractVehicle vehicleToRemove = null;

		    // NEU WICHTIG! PRAKTISCH!:
		    // Label gehört zur Schleife nicht zum break
		    // break labelName (unser Beispiel: "outerLoop") sagt: Beende die Schleife die dieses Label hat

		    // Durch die Floors und Spaces iterieren um das Fahrzeug zu finden
		    outerLoop:
		    for (Floor floor : parkingGarage.floors) {
		        for (ParkingSpace space : floor.getSpaces()) {
		            if (space.isOccupied() && space.getVehicle().getLicensePlate().equalsIgnoreCase(inputFromTF)) {
		                vehicleToRemove = space.getVehicle(); // Fahrzeug dem Puffer zuweisen
		                space.removeVehicle(); // Fahrzeug aus dem Parkhaus entfernen
		                break outerLoop; // WICHTIG: Mithilfe des Labels bricht das break die äußere Schleife ab wo das label gekennzeichnet ist
		            } // if - Prüft ob Fahrzeug parkt + Kennzeichen übereinstimmt
		        } // for - durchläuft alle Parkplätze einer Etage
		    } // for - durchläuft alle Etagen

		    // Aktion bei Fahrzeug gefunden / nicht gefunden
		    if (vehicleToRemove != null) {
		        vehiclesOutside.add(vehicleToRemove); // Fahrzeug wieder zur vehiclesOutside Liste hinzufügen
		        tAFilter.setText("Fahrzeug mit Kennzeichen \"" + vehicleToRemove.getLicensePlate() + "\" wurde erfolgreich rausgefahren!");
		    } else {
		        // vorherige Version mit vehicleToRemove.getLicensePlate() verursacht NullPointerException
		        showAlert("Fahrzeug mit dem Kennzeichen \"" + inputFromTF + "\" wurde nicht im Parkhaus gefunden!");
		    } // if - Fahrzeug gefunden / nicht gefunden

		    tFDriveOut.setText("");
		});

		
//		// VERSION 2 - Auto wird aus Parkhaus entfernt und somit für immer gelöscht
//		
//		// Fahrzeug aus dem Parkhaus entfernen
//		btnDriveOut.setOnAction(event -> {
//		    
//		    parkingGarage.removeVehicle(tFDriveOut.getText().trim());
//		    tFDriveOut.setText("");
//		});
		
		// --------------------------------------------------------
		
		// Suche Fahrzeug per Kennzeichen wenn vorhanden gib Kennzeichen + UUID wieder
		btnSearch.setOnAction(event -> {
			// Mithilfe von Kennzeichen suchen
		    String searchLicensePlate = tFSearch.getText().trim();
		    
		    // Sucht/vergleich im gesamten Parkhaus alle parkenden Fahrzeuge mit Kennzeichen
		    Optional<AbstractVehicle> foundVehicle = parkingGarage.floors.stream()
		        .flatMap(floor -> floor.getSpaces().stream())
		        .filter(ParkingSpace::isOccupied)
		        .map(ParkingSpace::getVehicle)
		        .filter(vehicle -> vehicle.getLicensePlate().equalsIgnoreCase(searchLicensePlate))
		        .findFirst();

		    // Wenn gesuchtes Fahrzeug mit Kennzeichen gefunden wurde
		    if (foundVehicle.isPresent()) {
		    		// Übergib referenz
		        AbstractVehicle vehicle = foundVehicle.get();
		        // Und gebe bei tAFilter das Kennzeichen sowie die UUID des Fahrzeuges aus
		        tAFilter.setText(
		            "Fahrzeug gefunden:\n" +
		            "Kennzeichen: " + vehicle.getLicensePlate() + "\n" +
		            "Interne ID: " + vehicle.getId()
		        );
		    } else {
		    		tAFilter.setText("Kein Fahrzeug mit Kennzeichen '" + searchLicensePlate + "' gefunden.");
		    } // if - Fahrzeug gefunden oder nicht

		    tFSearch.setText("");
		});
		
		// --------------------------------------------------------
		
		// Zeigt Status / Gesamte Liste von parkingGarage an
		btnParakingGarageStatus.setOnAction(event -> {
				    
			tAParkData.setText(parkingGarage.printStatusJavaFXVersion());
		});
		
		
		// --------------------------------------------------------
		
		// Zeigt je Filter vorhandene Fahrzeuge im Parkhaus an
		btnFilter.setOnAction(event -> {
			// Kurze Info rBtnCar.isSelected() ? X : Y wird 2x benötigt um richtige Klassenobjekt zuzuweisen und Text bei Ausgabe
			// rBtnCar.isSelected() reicht aus weil es gibt nur 2 Fälle und Existenz Überprüfung mit if ors und if isEmpty
			
		    // Prüft welcher RadioButton ausgewählt ist wenn garkeins skip
		    if (rBtnCar.isSelected() || rBtnMotorcycle.isSelected()) {
		    		// INTERESSANT NEU:
		    		// Class<?> kann beliebiges Objekt erstellen mit KLASSENNAME.class übergeben wir deren Bauplan bzw. das erstellt Objekt einen Class<?>
		    		// vehicleClass ist wie als hätten wir von Car vehichle = new Car(); erstellt
		    		// Class <?> hilft erstmal wenn noch nicht klar ist mit welchen Objekt man arbeiten wird 
		        Class<?> vehicleClass = rBtnCar.isSelected() ? Car.class : Motorcycle.class;
		        // Nachdem es zugewiesen wurde mit Car.class oder Motorcycle.class verhält sich vehicleClass so als wäre es von Anfang aus normal instanziiert worden

		        // Alle Fahrzeuge der ausgewählten Klasse filtern
		        List<AbstractVehicle> filteredVehicles = parkingGarage.floors.stream()
		                .flatMap(floor -> floor.getSpaces().stream())
		                .filter(ParkingSpace::isOccupied)
		                .map(ParkingSpace::getVehicle)
		                .filter(vehicleClass::isInstance)
		                .collect(Collectors.toList());

		        // Ausgabe in TextArea
		        if (filteredVehicles.isEmpty()) {
		            tAFilter.setText("Keine Fahrzeuge vom Typ " + (rBtnCar.isSelected() ? "Auto" : "Motorrad") + " gefunden.");
		        } else {
		            StringBuilder sb = new StringBuilder("--- Gefilterte Fahrzeuge ---\n");
		            for (AbstractVehicle v : filteredVehicles) {
		                sb.append(v.getLicensePlate()).append(" (ID: ").append(v.getId()).append(")\n");
		            } // for - Liste Zeile für Zeile ausgeben
		            tAFilter.setText(sb.toString());
		        } // if - leer oder gefunden für Feedback/Ausgabe
		    } else {
		        showAlert("Bitte wählen Sie zuerst einen Fahrzeugtyp aus (Auto oder Motorrad)!");
		    } // if - Auto oder Motorrad Radio Button
		});
		
		// --------------------------------------------------------
		
		// Fahrzeuge für Parkhaus generieren
		btnGenerator.setOnAction(event -> {
			
			// Sicherheit: Abfangen falls null auch wenn Button deaktiviert ist am Anfang
		    if (parkingGarage == null) {
		        showAlert("Bitte zuerst das Parkhaus erstellen!");
		        return;
		    }

		    // Gesamtanzahl der freien Parkplätze berechnen
		    int freeSpaces = parkingGarage.floors.stream()
		            .flatMap(floor -> floor.getSpaces().stream())
		            .filter(space -> !space.isOccupied())
		            .toArray().length;

		    // Sicherheit abfangen falls 0 oder Minuszahlen
		    if (freeSpaces <= 0) {
		        showAlert("Keine freien Plätze verfügbar, Parkhaus ist voll!");
		        return;
		    } // if - bei 0 oder weniger abfangen mit return sowie Feedback geben

		    // Zähler für Kennzeichen starten
		    int carCounter = 1;
		    int motorcycleCounter = 1;

		    // Bereits existierende Fahrzeuge berücksichtigen, um keine doppelten IDs zu erzeugen
		    for (Floor floor : parkingGarage.floors) {
		        for (ParkingSpace space : floor.getSpaces()) {
		            if (space.isOccupied()) {
		                if (space.getVehicle() instanceof Car) {
		                		carCounter++;
		                }
		                else if (space.getVehicle() instanceof Motorcycle) {
		                		motorcycleCounter++;
		                }
		            } // if - ist besetzt? wenn ja steigere im nächsten Schritt zähler je instanceOf
		        } // for - Geh alle Parkplätze durch
		    } // for - Geh alle Etagen durch

		    // Fahrzeuge generieren so viele wie freie Plätze verfügbar sind
		    for (int i = 0; i < freeSpaces; i++) {
		        AbstractVehicle vehicle;
		        if (i % 2 == 0) { // abwechselnd Auto / Motorrad Modulo Klassiker % 2
		            vehicle = new Car("a" + carCounter++);
		        } else {
		            vehicle = new Motorcycle("b" + motorcycleCounter++);
		        } // if - Abwechselnd Fahrzeuge generieren mit Zähler erhöhen

		        // Parkhaus VOLL = return false
		        boolean isThereSpace = parkingGarage.parkVehicle(vehicle);
		        if (!isThereSpace) {
		        		break; 
		        } // if - Falls Parkhaus voll ist abbrechen/beenden
		    } // for - Solange freie Plätze sind erstelle abwechselnd Auto / Motorrad

		    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Parkhaus gefüllt");
	        alert.setHeaderText(null);
	        alert.setContentText("Fahrzeuge wurden automatisch generiert und geparkt!");
	        alert.showAndWait();
		});

		// --------------------------------------------------------
		
		// 2. Scene
		Scene scene = new Scene(gridPane, 800, 600);
		
		// 1. Stage
		stage.setScene(scene);
		stage.setTitle("Parking Garage");
		stage.show();
		
	} // start()
	
	
	// 6. Zusätzliche Logik eigene Methoden
	
	private void showAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fehler");
        alert.setHeaderText(null);
        alert.setContentText(message); // Kontext kommt per Parameter
        alert.showAndWait();
	} // showAlert()
}
