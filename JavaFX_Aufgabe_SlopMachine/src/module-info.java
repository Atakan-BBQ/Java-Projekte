module JavaFX_Aufgabe_SlopMachine {
	requires java.desktop;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.media;
	
	opens app.fx to javafx.base, javafx.controls, javafx.graphics, javafx.media;
}