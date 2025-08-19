module DataBase_FitnessFX {
	requires java.desktop;
	requires java.sql;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	
	opens app.fx to java.sql, javafx.base, javafx.controls, javafx.graphics;
}