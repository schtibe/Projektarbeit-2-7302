package driver;

import simulation.DriverEvent;
import simulation.IEventTarget;

public interface IDriver extends IEventTarget<DriverEvent> {

	@Override
	public abstract void handleEvent(DriverEvent event) throws Exception;


}