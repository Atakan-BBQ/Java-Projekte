package app.fx;

public class DatabaseManager {
	
	private int id = 0;
	private String vorname = null;
	private String nachname = null;
	private String email = null;
	private String mitgliedschafts_typ = null;
	private String registrierungsdatum = null;
	
	
	public DatabaseManager(
			int id, 
			String vorname, 
			String nachname, 
			String email, 
			String mitgliedschafts_typ,
			String registrierungsdatum) 
	{
		this.id = id;
		this.vorname = vorname;
		this.nachname = nachname;
		this.email = email;
		this.mitgliedschafts_typ = mitgliedschafts_typ;
		this.registrierungsdatum = registrierungsdatum;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getVorname() {
		return vorname;
	}


	public void setVorname(String vorname) {
		this.vorname = vorname;
	}


	public String getNachname() {
		return nachname;
	}


	public void setNachname(String nachname) {
		this.nachname = nachname;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getMitgliedschafts_typ() {
		return mitgliedschafts_typ;
	}


	public void setMitgliedschafts_typ(String mitgliedschafts_typ) {
		this.mitgliedschafts_typ = mitgliedschafts_typ;
	}


	public String getRegistrierungsdatum() {
		return registrierungsdatum;
	}


	public void setRegistrierungsdatum(String registrierungsdatum) {
		this.registrierungsdatum = registrierungsdatum;
	}
	
} // Class DatabaseManager
