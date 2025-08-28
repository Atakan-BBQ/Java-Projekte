module JavaFX_Zaehler {

	requires java.desktop;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	
	opens app.fx to javafx.base, javafx.controls, javafx.graphics;
}