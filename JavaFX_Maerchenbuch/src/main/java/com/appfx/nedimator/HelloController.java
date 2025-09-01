package com.appfx.nedimator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HelloController {

    @FXML
    private TextArea tAText;

    @FXML
    private TextField tFTitle;

    @FXML
    void btnLoad(ActionEvent event) {
        // FileChooser (öffnet ein Fenster(Explorer) um nach Datei zu suchen)
        // Wir erstellen die regeln welche Art von Datei er suchen kann
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Märchen File");
        // fileChooser.setInitialFileName("jfxText.txt"); fürs Öffnen nicht benötigt
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),		// zeigt alle Daten
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),	// zeigt nur .txt Daten
                new FileChooser.ExtensionFilter("HTML Files", "*.html")	// zeigt nur .html Daten
        );

        // ACHTUNG OPEN VERSION fileChooser.showOpenDialog(stage); gibt uns den Pfad für selectedFile
        File selectedFile = fileChooser.showOpenDialog(MainApp.stage);
        // Kontrollieren ob Auswahl auch ein Inhalt/Pfad besitzt
        if(selectedFile != null) {
            // erstellt uns von der ausgewählten Datei den absoluten Pfad bis C:
            String filePath = selectedFile.getAbsolutePath();

            // Pfad wird readFileData gegeben und
            // Dateiinhalt wird gelesen und in die TextArea direkt gesetzt / per return von der Methode gegeben
            tAText.setText(readFileData(selectedFile));
            tFTitle.setText(selectedFile.getName());
        }
    }

    @FXML
    void btnNew(ActionEvent event) {
        tFTitle.setText("");
        tAText.setText("");
    }

    @FXML
    void btnQuit(ActionEvent event) {
        MainApp.stage.close();
    }

    @FXML
    void btnSave(ActionEvent event) {
        // FileChooser (öffnet ein Fenster(Explorer) um nach Datei zu suchen)
        // Wir erstellen die regeln welche Art von Datei er suchen kann
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Märchen File");
        fileChooser.setInitialFileName("" + tFTitle.getText() + ".txt");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),		// zeigt alle Daten
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),	// zeigt nur .txt Daten
                new FileChooser.ExtensionFilter("HTML Files", "*.html")	// zeigt nur .html Daten
        );

        // ACHTUNG SAVE VERSION fileChooser.showSaveDialog(stage); gibt uns den Pfad für selectedFile SPEICHERN/ERSTELLEN
        File selectedFile = fileChooser.showSaveDialog(MainApp.stage);

        if (selectedFile != null) {
            // erstellt uns von der ausgewählten Datei den absoluten Pfad bis C:
            String filePath = selectedFile.getAbsolutePath();

            // Inhalt von TextArea holen per textArea.getText()
            // In Datei schreiben welche vorher ausgewählt wurde Pfad
            // bzw. hintugefügt absolute Pfad ist in selectedFile
            writeDataToFile(selectedFile, tAText.getText());
        }

        String a = tAText.getText();


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

