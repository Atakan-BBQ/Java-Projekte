package app.db;

import java.sql.*;

public class MainApp {

	public static void main(String[] args) {
		
		// 1. 
		// jdbc:sqlite sagt das es ein sql DB ist
		// :hotel.db ist unsere DB Datei in src Ordner
		// ACHTUNG falls man namen falsch eingibt erstellt er eine Datei die aber leer ist
		// man merkt es natürlich erst dann wenn man auf Daten zugreifen will die im leeren nicht existeiren
		// falls man ein Eingabe fehler oder so gemacht hat
		String dbUrl = "jdbc:sqlite:src/hotel.db";
		
		// 2. Connection conn = DriverManager.getConnection(dbUrl);
		// DriverManager identifiziert welche Art von DB es ist in dem fall jdbc:sqlite
		// und merkt sich den Pfad also :hotel.db also direkt im src Ordner
		try (Connection conn = DriverManager.getConnection(dbUrl)) {
			
			// man kann innerhalb von try() schreiben oder innerhalb des Body vom try {}
			// Connection conn = DriverManager.getConnection(dbUrl);

			if(conn != null) {
				System.out.println("Connected to the database");
				System.out.println(conn);
			}
			
			// SQL Befehl was genommen werden soll
			String sql = "SELECT guest_id, name, phone, email FROM Guests";
			String sql2 = "SELECT * FROM Guests";
			String sql3Room = "SELECT room_id, room_number, room_type, price_per_night FROM Rooms";
			
			//
			// ACHTUNG: EIN! Statement DARF NUR EIN ResultSet GEBEN !!
			// Wenn man 2 oder mehr gleichzeitig benötigt dann benötigen diese ein eigenes Statement!
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			// Nr2 Statement + ResultSet
			Statement stmtRoom = conn.createStatement();
			ResultSet rsRoom = stmtRoom.executeQuery(sql3Room);
			
			//
			while(rs.next()) {
				int guestID = rs.getInt("guest_id");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				
				System.out.printf("Guest %d: %s, Phone: %s, Email: %s%n",guestID, name, phone, email);
			}
			
			while(rsRoom.next()) {
				int roomID = rsRoom.getInt("room_id");
				String roomNumber = rsRoom.getString("room_number");
				String roomType = rsRoom.getString("room_type");
				double pricePerNight = rsRoom.getDouble("price_per_night");
				
				System.out.printf("Room %d: %s, Roomtype: %s, Price per night: %.2f€%n",roomID, roomNumber, roomType, pricePerNight);
			}
			
			// room_id ist int int, string, int
			// Rooms (room_number, room_type, price_per_night)
			
		} catch (SQLException e) {
			System.out.println("Database connection failed.");
			e.printStackTrace();
		}
	}

}
