package gui;

/**
 * The basic adapter interface
 * @param <E> the class to adapt
 */
public interface IUIAdapter<E> {
	/**
	 * Return the main object
	 * @return
	 */
	public E getMainObject();
}
