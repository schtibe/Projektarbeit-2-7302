package driver;

import simulation.DriverEvent;
import simulation.IEventTarget;

public interface IDriver extends IEventTarget<DriverEvent> {

	public abstract void handleEvent(DriverEvent event) throws Exception;


}