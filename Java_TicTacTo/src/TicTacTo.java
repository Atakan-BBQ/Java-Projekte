import java.util.Random;
import java.util.Scanner;

public class TicTacTo {
	
	// boolean Checks
	static boolean player = true; // true player 1 false player 2
	static boolean isAgain = false;
	static boolean isInputCorrect = false; // 
	static boolean isInputDone = false; //
	static boolean isGameOver = false;
	static boolean isEnemyAI = false;
	// boolean Fields
	static boolean isFieldFree_1 = true; //
	static boolean isFieldFree_2 = true; //
	static boolean isFieldFree_3 = true; //
	static boolean isFieldFree_4 = true; //
	static boolean isFieldFree_5 = true; //
	static boolean isFieldFree_6 = true; //
	static boolean isFieldFree_7 = true; //
	static boolean isFieldFree_8 = true; //
	static boolean isFieldFree_9 = true; //
	
	// Strings
	static String[] gameBoard = new String[9];
	static String inputSymbol = " ";
	static String inputPlayer = " ";
	static String inputRematch = " ";
	static String currentPlayer = "Spieler 0";
	static String playerOneName = "Spieler 1 oder KI";
	static String playerTwoName = "Spieler 2 oder Spieler";	
	
	// Other
	static Scanner sc = new Scanner(System.in);
	static Random random = new Random();
	
	private static void resetGame() {
		for	(int i = 0; i < gameBoard.length; i++) {
			gameBoard[i] = "-";
		}
		
		isAgain = false;
		player = true; // 
		isInputCorrect = false; // 
		isInputDone = false; //
		isGameOver = false;
		isEnemyAI = false;
		isFieldFree_1 = true; //
		isFieldFree_2 = true; //
		isFieldFree_3 = true; //
		isFieldFree_4 = true; //
		isFieldFree_5 = true; //
		isFieldFree_6 = true; //
		isFieldFree_7 = true; //
		isFieldFree_8 = true; //
		isFieldFree_9 = true; //
		inputSymbol = " ";
		inputPlayer = " ";
		inputRematch = " ";
		currentPlayer = "Spieler 0";
	}
	
	private static void rematch() {
		
		boolean inputCheck = false;
		
		do {
			
			System.out.println("Nochmal spielen? [j/n]");
			inputRematch = sc.nextLine();
			
			if (inputRematch.matches("[j]")) {
				isAgain = true;
				inputCheck = true;
			} else if (inputRematch.matches("[n]")) {
				isAgain = false;
				inputCheck = true;
			} else {
			    System.out.println("Ungültige Eingabe! Bitte gib [j] oder [n] ein.");
			} // if - Prüft ob Eingabe 1-9 ist
		} while(!inputCheck);
	}
	
	private static void enemyType() {
		
		boolean inputCheck = false;
		
		do {
			
			System.out.println("Gegen (KI) spielen? [j/n]");
			inputRematch = sc.nextLine();
			
			if (inputRematch.matches("[j]")) {
				isEnemyAI = true;
				playerOneName = "KI";
				playerTwoName = "Spieler 2";
				inputCheck = true;
			} else if (inputRematch.matches("[n]")) {
				isEnemyAI = false;
				playerOneName = "Spieler 1";
				playerTwoName = "Spieler";
				inputCheck = true;
			} else {
			    System.out.println("Ungültige Eingabe! Bitte gib [j] oder [n] ein.");
			} // if - Prüft ob Eingabe 1-9 ist
			
		} while(!inputCheck);
	} // enemyType()
	
	private static void loadField () {
	
	System.out.printf(
				"+---+---+---+" + "\n" +
				"| %s | %s | %s |" + "\n" +
				"|---+---+---|" + "\n" +
				"| %s | %s | %s |" + "\n" +
				"|---+---+---|" + "\n" +
				"| %s | %s | %s |" + "\n" +
				"+---+---+---+" + "\n",
				gameBoard[0],
				gameBoard[1],
				gameBoard[2],
				gameBoard[3],
				gameBoard[4],
				gameBoard[5],
				gameBoard[6],
				gameBoard[7],
				gameBoard[8]
			);
	}
	
	static private void playerInput () {
		if(player) {
			inputSymbol = "X";
			player = !player;
			if (isEnemyAI) {
				currentPlayer = "KI";	
			} else {
				currentPlayer = "Spieler 1";				
			}
		} else {
			inputSymbol = "O";
			player = !player;
			if (isEnemyAI){
				currentPlayer = "Spieler";
			} else {
				currentPlayer = "Spieler 2";
			}
		}
		
		do {
			isInputDone = false;
		
			do {
				isInputCorrect = false;
				
				// if(player || (!player && !isEnemyAI) // Kürzer aber wegen Leserlichkeit / Verständlichkeit lange Version genommen
				if((player == true) || (player == false && isEnemyAI == false)) {
					
					System.out.printf("%s ist dran! \nWähle ein Feld von 1 bis 9: ", currentPlayer);
					inputPlayer = sc.nextLine();
			
					if (inputPlayer.matches("[1-9]")) {
						isInputCorrect = true;
					} else {
					    System.out.println("Ungültige Eingabe! Bitte eine Zahl von 1 bis 9 eingeben.");
					} // if - Prüft ob Eingabe 1-9 ist
				} else {
					System.out.printf("%s ist dran! \n", currentPlayer);
					inputPlayer = "" + random.nextInt(1, 10);
					System.out.println("KI hat [" + inputPlayer + "] gewählt.");
					isInputCorrect = true;
				}
				
			}while(!isInputCorrect); // do while - Eingabe überprüfen
			
			switch(inputPlayer) {
				case "1": 
					if (isFieldFree_1) {
						gameBoard[(1-1)] = inputSymbol;
						isFieldFree_1 = false;
						isInputDone = true;
					} else { 
						System.out.printf("Das %s. Feld ist bereits belegt\n", inputPlayer); 
					} 
					break;
				
				case "2": 
					if (isFieldFree_2) {
						gameBoard[2-1] = inputSymbol;
						isFieldFree_2 = false;
						isInputDone = true;
					} else { 
						System.out.printf("Das %s. Feld ist bereits belegt\n", inputPlayer); 
					} 
					break;
				
				case "3": 
					if (isFieldFree_3) {
						gameBoard[3-1] = inputSymbol;
						isFieldFree_3 = false;
						isInputDone = true;
					} else { 
						System.out.printf("Das %s. Feld ist bereits belegt\n", inputPlayer); 
					} 
					break;
					
				case "4": 
					if (isFieldFree_4) {
						gameBoard[4-1] = inputSymbol;
						isFieldFree_4 = false;
						isInputDone = true;
					} else { 
						System.out.printf("Das %s. Feld ist bereits belegt\n", inputPlayer); 
					} 
					break;
					
				case "5": 
					if (isFieldFree_5) {
						gameBoard[5-1] = inputSymbol;
						isFieldFree_5 = false;
						isInputDone = true;
					} else { 
						System.out.printf("Das %s. Feld ist bereits belegt\n", inputPlayer); 
					} 
					break;
					
				case "6": 
					if (isFieldFree_6) {
						gameBoard[6-1] = inputSymbol;
						isFieldFree_6 = false;
						isInputDone = true;
					} else { 
						System.out.printf("Das %s. Feld ist bereits belegt\n", inputPlayer); 
					} 
					break;
					
				case "7": 
					if (isFieldFree_7) {
						gameBoard[7-1] = inputSymbol;
						isFieldFree_7 = false;
						isInputDone = true;
					} else { 
						System.out.printf("Das %s. Feld ist bereits belegt\n", inputPlayer); 
					} 
					break;
					
				case "8": 
					if (isFieldFree_8) {
						gameBoard[8-1] = inputSymbol;
						isFieldFree_8 = false;
						isInputDone = true;
					} else { 
						System.out.printf("Das %s. Feld ist bereits belegt\n", inputPlayer); 
					} 
					break;
					
				case "9": 
					if (isFieldFree_9) {
						gameBoard[9-1] = inputSymbol;
						isFieldFree_9 = false;
						isInputDone = true;
					} else { 
						System.out.printf("Das %s. Feld ist bereits belegt\n", inputPlayer); 
					} 
					break;
				default:
					System.out.printf("Das %s. Feld ist bereits belegt\n", inputPlayer); 
			} // switch - Felder überprüfen

		}while(!isInputDone); // do while - Eingabe korrekt abgeschlossen
		
	} // playerInput()
	
	static private void checkCondition () {
		
		// Horizontal Spieler 1
		if( gameBoard[0] == "X" && gameBoard[1] == "X" && gameBoard[2] == "X" ||
			gameBoard[3] == "X" && gameBoard[4] == "X" && gameBoard[5] == "X" ||
			gameBoard[6] == "X" && gameBoard[7] == "X" && gameBoard[8] == "X" ) {
			
			System.out.println(playerOneName + " hat gewonnen!");
			isGameOver = true;
			loadField();
			return;
		}
		
		// Horizontal Spieler 2
		if( gameBoard[0] == "O" && gameBoard[1] == "O" && gameBoard[2] == "O" ||
			gameBoard[3] == "O" && gameBoard[4] == "O" && gameBoard[5] == "O" ||
			gameBoard[6] == "O" && gameBoard[7] == "O" && gameBoard[8] == "O" ) {
			
			System.out.println(playerTwoName + " hat gewonnen!");
			isGameOver = true;
			loadField();
			return;
		}
		
		// Vertikal Spieler 1
		if( gameBoard[0] == "X" && gameBoard[3] == "X" && gameBoard[6] == "X" ||
			gameBoard[1] == "X" && gameBoard[4] == "X" && gameBoard[7] == "X" ||
			gameBoard[2] == "X" && gameBoard[5] == "X" && gameBoard[8] == "X" ) {
			
			System.out.println(playerOneName + " hat gewonnen!");
			isGameOver = true;
			loadField();
			return;
		}
		
		// Vertikal Spieler 2
		if( gameBoard[0] == "O" && gameBoard[3] == "O" && gameBoard[6] == "O" ||
			gameBoard[1] == "O" && gameBoard[4] == "O" && gameBoard[7] == "O" ||
			gameBoard[2] == "O" && gameBoard[5] == "O" && gameBoard[8] == "O" ) {
			
			System.out.println(playerTwoName + " hat gewonnen!");
			isGameOver = true;
			loadField();
			return;
		}
		
		// Diagonal Spieler 1
		if( gameBoard[0] == "X" && gameBoard[4] == "X" && gameBoard[8] == "X" ||
			gameBoard[2] == "X" && gameBoard[4] == "X" && gameBoard[6] == "X" ) {
			
			System.out.println(playerOneName + " hat gewonnen!");
			isGameOver = true;
			loadField();
			return;
		}
		
		// Diagonal Spieler 2
		if( gameBoard[0] == "O" && gameBoard[4] == "O" && gameBoard[8] == "O" ||
			gameBoard[2] == "O" && gameBoard[4] == "O" && gameBoard[6] == "O" ) {
			
			System.out.println(playerTwoName + " hat gewonnen!");
			isGameOver = true;
			loadField();
			return;
		}
		
		// Unentschieden
		if ((!isFieldFree_1 && !isFieldFree_2 && !isFieldFree_3 &&
			!isFieldFree_4 && !isFieldFree_5 && !isFieldFree_6 &&
			!isFieldFree_7 && !isFieldFree_8 && !isFieldFree_9)) {
			
			System.out.println("Game over - Unentschieden!");
			isGameOver = true;
			loadField();
			return;
		}
	}

	public static void main(String[] args) {
		
		do {
			resetGame();
			enemyType();
			
			do {
				loadField();
				playerInput();
				checkCondition();
			} while(!isGameOver);
			
			rematch();
			
		} while(isAgain);
		
		System.out.println("ENDE ERREICHT");
		
	} // main()
} // class TicTacTo
