package app.fx;

import java.util.UUID;

abstract class AbstractVehicle {
	
	private final String licensePlate;
	private final String id; // UUID
	
	public AbstractVehicle(String licensePlate) {
		
		this.licensePlate = licensePlate;
		this.id = UUID.randomUUID().toString();
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public String getId() {
		return id;
	}
	
	// Abstract method that must be implemented by subclasses
	public abstract String getType();
	
	@Override
	public String toString() {
		return "Type: " + getType() + ", License Plate: " + licensePlate + ", ID: " + id;
	}
}
