package car;

import environment.Gaia;
import environment.ILane;

/**
 * The factory for vehicles
 */
public class VehicleFactory {

	/**
	 * All the possible vehicle types
	 */
	public enum VehicleType {
		car
	}

	/**
	 * Create a vehicle on a lane (on position 0)
	 * 
	 * @param vehicleType
	 * @param lane
	 * @return
	 * @throws Exception
	 */
	public static IVehicle createVehicle(VehicleType vehicleType, ILane lane)
			throws Exception {
		IVehicle vehicle = null;

		switch (vehicleType) {
			case car:
				vehicle = new Car(lane);
				break;
		}

		Gaia.getInstance().setVehicle(vehicle);
		vehicle.updatePosition(0.0f);
		return vehicle;
	}

	/**
	 * Create a vehicle on a lane at a certain position
	 * 
	 * @param vehicleType
	 * @param lane
	 * @param drivenLaneDistance
	 * @return
	 * @throws Exception
	 */
	public static IVehicle createVehicle(VehicleType vehicleType, ILane lane,
			float drivenLaneDistance) throws Exception {
		IVehicle vehicle = null;

		switch (vehicleType) {
		case car:
			vehicle = new Car(lane, drivenLaneDistance);
			break;
		}

		Gaia.getInstance().setVehicle(vehicle);
		return vehicle;
	}
}
