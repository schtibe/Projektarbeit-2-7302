package simulation;

/**
 * Events for the simulator
 * 
 * This class is currently not in use
 * @deprecated
 */
@Deprecated
public class SimulatorEvent extends Event<SimulatorEvent> {
	/**
	 * Initialise
	 * 
	 * @param timestamp
	 * @param target
	 */
	public SimulatorEvent(long timestamp, IEventTarget<SimulatorEvent> target) {
		super(timestamp, target);
	}

	@Override
	public void handleEvent() throws Exception {

	}
}
