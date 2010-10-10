package simulation;

/**
 * Events for the simulator
 */
public class SimulatorEvent extends Event<SimulatorEvent> {
	/**
	 * Initialize
	 * 
	 * @param timestamp
	 * @param target
	 */
	public SimulatorEvent(long timestamp, IEventTarget<SimulatorEvent> target) {
		super(timestamp, target);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleEvent() throws Exception {
		// TODO Auto-generated method stub

	}
}
