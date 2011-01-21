package gui;

/**
 * The basic abstract adpater
 * @param <E> the class to adapt
 */
public abstract class UIAdapter<E> implements IUIAdapter<E> {
	
	E mainObject;
	
	public UIAdapter(E mainObject) throws Exception {
		if(mainObject != null) {
			this.mainObject=mainObject;
		} else {
			throw new Exception("mainObject can't be null.");
		}
	}

	@Override
	public E getMainObject() {
		return this.mainObject;
	}
}
