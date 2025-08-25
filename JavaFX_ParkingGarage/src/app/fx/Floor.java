package app.fx;

import java.util.ArrayList;
import java.util.List;

class Floor {
	
	private final int floorNumber;
	private final List<ParkingSpace> spaces;
	
	public Floor (int floorNumber, int numberOfSpaces) {
		this.floorNumber = floorNumber;
		this.spaces = new ArrayList<>();

		for(int i = 0; i < numberOfSpaces; i++) {
			spaces.add(new ParkingSpace(i+1));
		}
	}
	
	public int getFloorNumber() {
		return floorNumber;
	}
	
	public List<ParkingSpace> getSpaces() {
		return spaces;
	}
}
