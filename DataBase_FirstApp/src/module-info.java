module DataBase_FirstApp {
	requires java.sql;
	
	opens app.db to java.sql;
}