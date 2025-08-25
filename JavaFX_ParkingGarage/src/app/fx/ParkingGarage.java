package app.fx;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

class ParkingGarage {

	final List<Floor> floors;
	private final int totalFloors;
	private final int spacesPerFloor;
	
	public ParkingGarage(int totalFloors, int spacesPerFloor) {
		this.totalFloors = totalFloors;
		this.spacesPerFloor = spacesPerFloor;
		
		this.floors = new ArrayList<>();
		for (int i = 0; i < totalFloors; i++) {
			floors.add(new Floor(i+1, spacesPerFloor));
		}
	}
	
	// Finds and parks a vehicle in the first available space.
	
	public boolean parkVehicle(AbstractVehicle vehicle) {
		
		Optional<ParkingSpace> vacantSpace = floors.stream()
					.flatMap(floor -> floor.getSpaces().stream())
					.filter(space -> !space.isOccupied())
					.findFirst();
		
		if(vacantSpace.isPresent()) {
			vacantSpace.get().parkVehicle(vehicle);
			System.out.println("Vehicle: " + vehicle.getLicensePlate() + " Parked Successfully.");
			return true;
		} else {
			System.out.println("No vacant space available for vehicle " + vehicle.getLicensePlate());
			return false;
		}
	}
	
	public boolean removeVehicle(String licensePlate) {
		
		Optional<ParkingSpace> occupiedSpace = floors.stream()
				.flatMap(floor -> floor.getSpaces().stream())
				.filter(space -> space.isOccupied() && space.getVehicle().getLicensePlate().equals(licensePlate))
				.findFirst();
		
		if(occupiedSpace.isPresent()) {
			AbstractVehicle vehicle = occupiedSpace.get().removeVehicle();
			System.out.println("Vehicle: " + vehicle.getLicensePlate() + " removed successfully");
			return true;
		} else {
			System.out.println("Vehicle with license plate " + licensePlate + " not found.");
			return false;
		}
	}
	
	public void printStatus () {
		System.out.println("----- Parking Garage Status -----");
		floors.forEach(floor -> {
			System.out.println("Floor " + floor.getFloorNumber() + ":");
			floor.getSpaces().forEach(space -> {
				System.out.println(space);
			});
		});
		
		System.out.println("---------------------------------");
	}
	
	public String printStatusJavaFXVersion() {
		StringBuilder printedText = new StringBuilder();

	    printedText.append("----- Parking Garage Status -----\n");

	    for (Floor floor : floors) {
	        printedText.append("Floor ").append(floor.getFloorNumber()).append(":\n");

	        for (ParkingSpace space : floor.getSpaces()) {
	            printedText.append(space).append("\n"); // \n wie bei println
	        }
	    }

	    printedText.append("---------------------------------\n");

	    return printedText.toString();
	}
	
	// Find a vehicle 'vehicleId' (anonymous class)
	
	public Optional<AbstractVehicle> findVehicleByIdWithAnnonymousClass(String vehicleId) {
		
		Predicate<AbstractVehicle> isTargetVehicle = new Predicate<AbstractVehicle>() {
			
			@Override
			public boolean test(AbstractVehicle vehicle) {
				return vehicle.getId().equals(vehicleId);
			}
		};
		
		return floors.stream()
				.flatMap(floor -> floor.getSpaces().stream())
				.filter(ParkingSpace::isOccupied)
				.map(ParkingSpace::getVehicle)
				.filter(isTargetVehicle)
				.findFirst();
	}
}
