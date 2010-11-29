package simulation;

import common.GlobalConstants;

import environment.Gaia;

public class Simulator implements IEventTarget<SimulatorEvent> {

	protected long startTime;
	protected long lastIteration;

	public Simulator() throws Exception {

	}

	public void startSimulation(long timestamp, double factor) {
		this.startTime = timestamp;
		GlobalConstants.getInstance().setInitialTimestamp(timestamp);
		this.lastIteration = 0;
		GlobalConstants.getInstance().setTimeFactor(factor);
	}

	public synchronized void update(long timestamp) throws Exception {
		float difference = (timestamp - (this.startTime + this.lastIteration)); // Math.round((double)(timestamp-this.startTime)/GlobalConstants.getInstance().getTimeFactor());

		this.lastIteration += difference;
		Boolean goOn = true;
		Gaia.getInstance().updateVehicles(difference);
		while (goOn) {
			IEvent actualEvent = EventQueue.getInstance().getNextEvent(
					this.lastIteration);
			if (actualEvent != null) {
				actualEvent.handleEvent();
			} else {
				goOn = false;
			}
		}
	}

	@Override
	public void handleEvent(SimulatorEvent event) {
		// TODO Auto-generated method stub
	}
}
