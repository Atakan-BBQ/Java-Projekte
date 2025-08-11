package app.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class TicTacTo extends Application {
	
	boolean player = true; // true player 1 / false player 2
	AudioClip pressButton = new AudioClip("file:src/PressButton.mp3");
	AudioClip win = new AudioClip("file:src/Win.wav");

	public static void main(String[] args) {
		
		launch(args);
		
	} // main()

	@Override
	public void start(Stage arg0) throws Exception {
		
		// 4. Nodes
		
		// 4.1 Labels
		Label currentPlayer = new Label("  Player X");
		currentPlayer.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
		if (player) {
			currentPlayer.setText("  Player X");
		} else {
			currentPlayer.setText("  Player O");
		}
		
		// 4.2 Buttons
		Button reset = new Button("reset");
		Button btn_1 = new Button("-");
		Button btn_2 = new Button("-");
		Button btn_3 = new Button("-");
		Button btn_4 = new Button("-");
		Button btn_5 = new Button("-");
		Button btn_6 = new Button("-");
		Button btn_7 = new Button("-");
		Button btn_8 = new Button("-");
		Button btn_9 = new Button("-");
		
		Button[] buttons = {btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9};
		
		reset.setMinHeight(25);
		reset.setMinWidth(100);
		reset.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
		
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].setMinHeight(100);
			buttons[i].setMinWidth(100);
			buttons[i].setFont(Font.font("Arial", FontWeight.BOLD, 40));
			
			final int index = i; // nötig wegen Lambda
			buttons[i].setOnAction(event -> pressButtonXO(buttons[index], currentPlayer, buttons));
		} // for - Buttons designen
		
		
		// 3. Layout Manager
		GridPane gridPane = new GridPane();
		
		for (int i = 0; i < buttons.length; i++) {
		    int col = i % 3;  // Spalte: 0,1,2
		    int row = i / 3;  // Zeile: 0,1,2
		    gridPane.add(buttons[i], col, row);
		} // for - Buttons einfügen
		
		gridPane.add(currentPlayer, 0, 4);
		gridPane.add(reset, 2, 4);
		
//		btn_1.setOnAction(event -> {
//			if (player) {
//				btn_1.setText("X");
//				currentPlayer.setText("  Player O");
//			} else {
//				btn_1.setText("O");
//				currentPlayer.setText("  Player X");
//			}
//			player = !player;
//		});
		
//		btn_1.setOnAction(event -> { pressButtonXO(btn_1, currentPlayer); });
//		btn_2.setOnAction(event -> { pressButtonXO(btn_2, currentPlayer); });
//		btn_3.setOnAction(event -> { pressButtonXO(btn_3, currentPlayer); });
//		btn_4.setOnAction(event -> { pressButtonXO(btn_4, currentPlayer); });
//		btn_5.setOnAction(event -> { pressButtonXO(btn_5, currentPlayer); });
//		btn_6.setOnAction(event -> { pressButtonXO(btn_6, currentPlayer); });
//		btn_7.setOnAction(event -> { pressButtonXO(btn_7, currentPlayer); });
//		btn_8.setOnAction(event -> { pressButtonXO(btn_8, currentPlayer); });
//		btn_9.setOnAction(event -> { pressButtonXO(btn_9, currentPlayer); });
		
		// Reset-Logik
		reset.setOnAction(event -> {
			for (Button btn : buttons) {
				btn.setText("-");
				btn.setDisable(false);
			}
			player = true;
			currentPlayer.setText("  Player X");
			activateAllButtons(buttons);
		});
		
		
		// 2. Scene
		Scene scene = new Scene(gridPane, 300, 350);
		
		
		// 1. Stage
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("JavaFX TicTacTo");
		stage.setResizable(false);
		stage.show();
		
	} // start()
	
	public void pressButtonXO(Button p_btn, Label p_label, Button[] p_buttons) {
		p_btn.setOnAction(event -> {
			if (player) {
				p_btn.setText("X");
				p_label.setText("  Player O");
			} else {
				p_btn.setText("O");
				p_label.setText("  Player X");
			}
			
			pressButton.play();
			p_btn.setDisable(true);
			player = !player;
			checkCondition(p_buttons, p_label);
		});	
	} // pressButtonXO()
	
	public void disableAllButtons(Button[] p_buttons) {
		for (Button btn : p_buttons) {
			btn.setDisable(true);			
		} // foreach - alle Buttons deaktivieren
	} // disableAllButtons
	
	public void activateAllButtons(Button[] p_buttons) {
		for (Button btn : p_buttons) {
			btn.setDisable(false);			
		} // foreach - alle Buttons deaktivieren
	} // disableAllButtons
	
	public void checkCondition (Button[] p_buttons, Label p_currentPlayer) {
		// Horizontal X Spieler
		if ((p_buttons[(1-1)].getText().equals("X") && p_buttons[(2-1)].getText().equals("X") && p_buttons[(3-1)].getText().equals("X")) ||
			(p_buttons[(4-1)].getText().equals("X") && p_buttons[(5-1)].getText().equals("X") && p_buttons[(6-1)].getText().equals("X")) ||
			(p_buttons[(7-1)].getText().equals("X") && p_buttons[(8-1)].getText().equals("X") && p_buttons[(9-1)].getText().equals("X")))
		{
			p_currentPlayer.setText("  X won!");
			disableAllButtons(p_buttons);
			win.play();
			return;
		}
		
		// Horizontal O Spieler
		if ((p_buttons[(1-1)].getText().equals("O") && p_buttons[(2-1)].getText().equals("O") && p_buttons[(3-1)].getText().equals("O")) ||
		    (p_buttons[(4-1)].getText().equals("O") && p_buttons[(5-1)].getText().equals("O") && p_buttons[(6-1)].getText().equals("O")) ||
		    (p_buttons[(7-1)].getText().equals("O") && p_buttons[(8-1)].getText().equals("O") && p_buttons[(9-1)].getText().equals("O")))
		{
		    p_currentPlayer.setText("  O won!");
		    disableAllButtons(p_buttons);
		    win.play();
		    return;
		}
		
		// Vertikal X Spieler
		if ((p_buttons[(1-1)].getText().equals("X") && p_buttons[(4-1)].getText().equals("X") && p_buttons[(7-1)].getText().equals("X")) ||
			(p_buttons[(2-1)].getText().equals("X") && p_buttons[(5-1)].getText().equals("X") && p_buttons[(8-1)].getText().equals("X")) ||
			(p_buttons[(3-1)].getText().equals("X") && p_buttons[(6-1)].getText().equals("X") && p_buttons[(9-1)].getText().equals("X")))
		{
			p_currentPlayer.setText("  X won!");
			disableAllButtons(p_buttons);
			win.play();
			return;
		}
		
		// Vertikal O Spieler
		if ((p_buttons[(1-1)].getText().equals("O") && p_buttons[(4-1)].getText().equals("O") && p_buttons[(7-1)].getText().equals("O")) ||
		    (p_buttons[(2-1)].getText().equals("O") && p_buttons[(5-1)].getText().equals("O") && p_buttons[(8-1)].getText().equals("O")) ||
		    (p_buttons[(3-1)].getText().equals("O") && p_buttons[(6-1)].getText().equals("O") && p_buttons[(9-1)].getText().equals("O")))
		{
		    p_currentPlayer.setText("  O won!");
		    disableAllButtons(p_buttons);
		    win.play();
		    return;
		}
		
		// Diagonal X Spieler
		if ((p_buttons[(1-1)].getText().equals("X") && p_buttons[(5-1)].getText().equals("X") && p_buttons[(9-1)].getText().equals("X")) ||
			(p_buttons[(3-1)].getText().equals("X") && p_buttons[(5-1)].getText().equals("X") && p_buttons[(7-1)].getText().equals("X")))
		{
			p_currentPlayer.setText("  X won!");
			disableAllButtons(p_buttons);
			win.play();
			return;
		}
		
		// Diagonal O Spieler
		if ((p_buttons[(1-1)].getText().equals("O") && p_buttons[(5-1)].getText().equals("O") && p_buttons[(9-1)].getText().equals("O")) ||
		    (p_buttons[(3-1)].getText().equals("O") && p_buttons[(5-1)].getText().equals("O") && p_buttons[(7-1)].getText().equals("O")))
		{
		    p_currentPlayer.setText("  O won!");
		    disableAllButtons(p_buttons);
		    win.play();
		    return;
		}
		
		// Unentschieden
		if (p_buttons[(1-1)].isDisable() && p_buttons[(2-1)].isDisable() && p_buttons[(3-1)].isDisable() &&
			p_buttons[(4-1)].isDisable() && p_buttons[(5-1)].isDisable() && p_buttons[(6-1)].isDisable() &&
			p_buttons[(7-1)].isDisable() && p_buttons[(8-1)].isDisable() && p_buttons[(9-1)].isDisable()) 
		{	
			p_currentPlayer.setText("  draw!");
			disableAllButtons(p_buttons);
			return;
		}
	} // checkCondition()
	
	
	
} // class JavaFX TicTacTo
