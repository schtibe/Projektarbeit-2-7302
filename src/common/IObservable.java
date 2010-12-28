package common;

public interface IObservable {
	public void register (IObserver obs);
	public void remove (IObserver obs);
	public void notify (String msg);
}
