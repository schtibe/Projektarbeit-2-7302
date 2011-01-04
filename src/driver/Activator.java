package driver;

public abstract class Activator implements IActivator {
	
	private float amount;
	
	public Activator (float amnt){
		this.setValue(amnt);
	}
	
	@Override
	public float getValue() {
		return amount;
	}

	@Override
	public void setValue(float amnt) {
		if (amnt < 0) {
			amount = 0f;
		} else if (amnt > 1.0) {
			amount = 1f;
		} else {
			amount = amnt;
		}
	}
}
