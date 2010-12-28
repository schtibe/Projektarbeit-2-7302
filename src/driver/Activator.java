package driver;

public abstract class Activator implements IActivator {
	
	private float amount;
	
	public Activator (float amnt){
		this.setValue(amnt);
	}
	
	@Override
	public float getValue() {
		// TODO Auto-generated method stub
		return amount;
	}

	@Override
	public void setValue(float amnt) {
		// TODO Auto-generated method stub
		if (amnt < 0){
			amount = 0f;
		}else if (amnt > 1.0){
			amount = 1f;
		}else{
			amount = amnt;
		}
	}

}
