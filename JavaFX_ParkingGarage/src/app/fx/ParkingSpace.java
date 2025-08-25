package app.fx;

class ParkingSpace {
	
	private final int spaceNumber;
	private AbstractVehicle vehicle;
	private boolean isOccupied;
	
	public ParkingSpace (int spaceNumber) {
		this.spaceNumber = spaceNumber;
	}

	public AbstractVehicle getVehicle() {
		return vehicle;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public void parkVehicle(AbstractVehicle vehicle) { 
		this.vehicle = vehicle;
		this.isOccupied = true;
	}
	
	public AbstractVehicle removeVehicle() {
		AbstractVehicle parkedVehicle = this.vehicle;
		this.vehicle = null;
		this.isOccupied = false;
		return parkedVehicle;
	}
	
	@Override
	public String toString() {
		return "Space: " + spaceNumber + ", Status: " + (isOccupied ? "Occupied" : "Vacant");
	}
	
}
