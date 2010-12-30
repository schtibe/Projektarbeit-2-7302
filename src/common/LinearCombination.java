package common;

public class LinearCombination {
	private float mu;
	private float lambda;
	
	public LinearCombination (float m, float l){
		mu = m;
		lambda = l;
	}
	
	public float getMu (){
		return mu;
	}
	
	public float getLambda(){
		return lambda;
	}
}
