package common;

public class Rectangle implements IRectangle {

	private IVector bottomLeft;
	private IVector topRight;
	private float width;
	private float height;
	
	public Rectangle (IVector botleft, IVector topright){
		bottomLeft = botleft;
		topRight = topright;
	}
	
	@Override
	public IVector getBottomLeft() {
		return bottomLeft;
	}

	@Override
	public IVector getTopRight() {
		return topRight;
	}

	@Override
	public float getWidth() {
		if (width == 0){
			width = topRight.getComponent(0)-bottomLeft.getComponent(0);
			
		}
		return width;
	}

	@Override
	public float getHeight() {
		if (height == 0){
			height = topRight.getComponent(1)-bottomLeft.getComponent(1);
		}
		return height;
	}
	
	@Override
	public String toString (){
		return bottomLeft.toString()+":"+topRight.toString();
	}

}
