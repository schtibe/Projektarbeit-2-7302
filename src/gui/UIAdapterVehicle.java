package gui;


import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Path;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import car.IVehicle;

import common.IVector;
import common.Vector;
import driver.IDriverView;

/**
 * A vehicle adapter implementation
 */
public class UIAdapterVehicle extends UIAdapter<IVehicle> implements IUIAdapterVehicle<IVehicle> {

	protected IVector oldDirection;
	protected IVector offsetVector = new Vector(new float[]{0, 0});
	protected Shape boundingBox;
	protected float angle=0, scale = 0;
	protected final IVector X_AXIS = new Vector(new float[]{1,0});
	protected float cosAlpha, newAngle, angleDiff;
	
	public UIAdapterVehicle(IVehicle mainObject, float scale, IVector offsetVector) throws Exception {
		super(mainObject);
		
		this.offsetVector = offsetVector;
		this.scale = scale;
		this.oldDirection = this.mainObject.getDirection().normalize();
		float cosAlpha = this.oldDirection.dot(X_AXIS);
		this.angle = (float) Math.acos(cosAlpha);
		this.boundingBox = new Rectangle(
				0,
				0,
				this.mainObject.getDimension().getLength() * scale *5,
				this.mainObject.getDimension().getWidth() * scale *5
		);	

		if (oldDirection.getComponent(1) < 0) {
			this.boundingBox = this.boundingBox.transform(Transform.createRotateTransform(-this.angle)); 
		} else {
			this.boundingBox = this.boundingBox.transform(Transform.createRotateTransform(this.angle));
		}

		this.boundingBox.setCenterX(this.mainObject.getPosition().getComponent(0) * scale + offsetVector.getComponent(0));
		this.boundingBox.setCenterY(this.mainObject.getPosition().getComponent(1) * scale + offsetVector.getComponent(1));
	}
	
	/**
	 * Applies the new direction (rotates)
	 */
	public void update() {
		IVector newDir = this.mainObject.getDirection().normalize();
		if(this.oldDirection.compareTo(newDir) != 0) {
			cosAlpha = newDir.dot(X_AXIS);
			newAngle = (float)Math.acos(cosAlpha);
			if (Math.abs(newDir.getComponent(1) + oldDirection.getComponent(1)) >
					newDir.getComponent(1)
				) {
				angleDiff = newAngle - this.angle;
			} else {
				angleDiff = newAngle + this.angle;
			}
			float length = this.mainObject.getDimension().getLength() * scale * 5;
			float width = this.mainObject.getDimension().getWidth() * scale * 5;
			Polygon p = new Polygon();
			float turnAngle;
			
			if (newDir.getComponent(1) < 0) {
				turnAngle = newAngle;
			} else {
				turnAngle = -newAngle;
			}
			
			p.addPoint(0, 0); // left upper corner
			p.addPoint( // right upper corner
					(float)(Math.cos(turnAngle)*length),
					(float)(-Math.sin(turnAngle)*length));
			p.addPoint(
					(float)(Math.cos(turnAngle)*length+Math.sin(turnAngle)*width),
					(float)(-Math.sin(turnAngle)*length+Math.cos(turnAngle)*width)
			);
			p.addPoint( // left lower corner
					 (float)(Math.sin(turnAngle)*width),
					 (float)(Math.cos(turnAngle)*width)
			);
			this.boundingBox = p;
			
			this.angle = newAngle;
			this.oldDirection = newDir;
		}
		this.boundingBox.setCenterX(this.mainObject.getPosition().getComponent(0) * scale + offsetVector.getComponent(0));
		this.boundingBox.setCenterY(this.mainObject.getPosition().getComponent(1) * scale + offsetVector.getComponent(1));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Shape getBoundingBox() {
		this.update();
		return boundingBox;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "new Angle: " + newAngle + "\n angleDiff: " + angleDiff;
	}

	
	@Override
	public Color getColor() {
		return new Color(Color.red);
	}

	
	
	@Override
	public void draw(Graphics g) {
		g.setColor(this.getColor());
		g.fill(this.getBoundingBox());
		g.draw(this.getBoundingBox());
		
		g.setColor(Color.yellow);
		Shape view = this.getDriverViewBoundingBox();
		g.draw(view);
	}
	
	
	/**
	 * @TODO refactor this
	 * @return
	 */
	protected Shape getDriverViewBoundingBox() {
		IDriverView view = this.mainObject.getDriverView();
		
		float angle = view.getAngle();
		IVector direction = view.getDirection();
		IVector position = view.getPosition();
		float distance = view.getDistance();
		
		float halfAngle = angle / 2;
		
		IVector a = (direction.rotate(halfAngle)).normalize().multiply(distance);
		IVector c = (direction.rotate(-halfAngle)).normalize().multiply(distance);
		
		a = a.add(position);
		c = c.add(position);
		
		Path path = new Path(
				position.getComponent(0) * scale + this.offsetVector.getComponent(0), 
				position.getComponent(1) * scale + this.offsetVector.getComponent(1)
		);
		
		
		path.lineTo(
				a.getComponent(0) * scale + this.offsetVector.getComponent(0), 
				a.getComponent(1) * scale + this.offsetVector.getComponent(1)
		);
		path.lineTo(
				c.getComponent(0) * scale + this.offsetVector.getComponent(0), 
				c.getComponent(1) * scale + this.offsetVector.getComponent(1)
		);
		path.lineTo(
				position.getComponent(0) * scale + this.offsetVector.getComponent(0), 
				position.getComponent(1) * scale + this.offsetVector.getComponent(1)
		);
		
		return path;
	}

	
}
