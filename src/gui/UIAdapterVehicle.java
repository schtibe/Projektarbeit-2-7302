package gui;


import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Path;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import car.IVehicle;
import car.VehicleDimension;

import common.IObserver;
import common.IVector;
import common.Vector;

import driver.IDriverView;

/**
 * A vehicle adapter implementation
 */
public class UIAdapterVehicle extends UIAdapter<IVehicle> 
	implements IUIAdapterVehicle<IVehicle>, IObserver {

	protected IVector oldDirection;
	protected IVector offsetVector = new Vector(new float[]{0, 0});
	protected Shape boundingBox;
	protected float angle=0, scale = 0;
	protected final IVector X_AXIS = new Vector(new float[]{1,0});
	protected float cosAlpha, newAngle, angleDiff;
	
	protected static enum turnSignalType {
		left,
		right,
		off
	}
	
	protected turnSignalType turnSignal = turnSignalType.off;
	
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
				this.mainObject.getDimension().getLength() * scale * 5,
				this.mainObject.getDimension().getWidth() * scale * 5
		);	

		if (oldDirection.getComponent(1) < 0) {
			this.boundingBox = this.boundingBox.transform(Transform.createRotateTransform(-this.angle)); 
		} else {
			this.boundingBox = this.boundingBox.transform(Transform.createRotateTransform(this.angle));
		}

		this.boundingBox.setCenterX(this.mainObject.getPosition().getComponent(0) * scale + offsetVector.getComponent(0));
		this.boundingBox.setCenterY(this.mainObject.getPosition().getComponent(1) * scale + offsetVector.getComponent(1));
		
		this.mainObject.register(this);
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
	
	@Override
	public String toString() {
		return "new Angle: " + newAngle + "\n angleDiff: " + angleDiff;
	}

	@Override
	public void draw(Graphics g) {
		if (!this.mainObject.isFreezed()) {
			g.setColor(Color.red);
			g.fill(this.getBoundingBox());
		}
		
		g.setColor(Color.white);
		g.draw(this.getBoundingBox());
		
		if (GUIConstants.getInstance().showDriverView() 
				&& !this.mainObject.isFreezed()) {
			g.setColor(Color.yellow);
			Shape view = this.getDriverViewBoundingBox();
			g.draw(view);
		}
		
		this.drawTurnSignal(g);
	}
	
	protected void drawTurnSignal(Graphics g) {
		if (this.turnSignal != turnSignalType.off && !this.mainObject.isFreezed()) {
			VehicleDimension vd = this.mainObject.getDimension();
			IVector direction = this.mainObject.getDirection();
			IVector position = this.mainObject.getPosition();
			IVector lenDirection = direction.normalize().multiply(vd.getLength() / 4 * 3);
			IVector widthDirection;
			
			if (this.turnSignal == turnSignalType.right) {
				widthDirection = direction.rotate((float)Math.PI/2);
				widthDirection = widthDirection.normalize().multiply(vd.getWidth()/2 * 5);
				
			} else {
				widthDirection = direction.rotate((float)-Math.PI/2);
				widthDirection = widthDirection.normalize().multiply(vd.getWidth()/2 * 5);
			}
			
			IVector drawPos = position.add(widthDirection).add(lenDirection);
			
			float offsetX = offsetVector.getComponent(0);
			float offsetY = offsetVector.getComponent(1);
			g.setColor(Color.yellow);
			g.fill(
				new Ellipse(
					drawPos.getComponent(0) * scale + offsetX, 
					drawPos.getComponent(1) * scale + offsetY, 
					4, 
					4
				)
			);
		}
	}
	
	/**
	 * Get the shape for the driver's view
	 * @return
	 */
	protected Shape getDriverViewBoundingBox() {
		IDriverView view = this.mainObject.getDriverView();
		IVector position = view.getPosition().multiply(scale);
		float offsetX = this.offsetVector.getComponent(0);
		float offsetY = this.offsetVector.getComponent(1);
		
		IVector drawer = view.getDirection().normalize().multiply(view.getDistance() * scale);
		drawer = drawer.rotate(view.getAngle() / 2);
		
		Path path = new Path(
				position.getComponent(0) + offsetX, 
				position.getComponent(1) + offsetY
		);
		
		// draw the opening 
		path.lineTo(
				position.getComponent(0) + offsetX + drawer.getComponent(0), 
				position.getComponent(1) + offsetY + drawer.getComponent(1)
		);
		
		// draw the circle 
		int steps = 100; // the steps to rotate the driver view 
		float dvAngle = view.getAngle();
		for (float angle = dvAngle / steps; angle < view.getAngle(); angle += dvAngle / steps) {
			drawer = drawer.rotate(-dvAngle / steps);
			path.lineTo(
				position.getComponent(0) + offsetX + drawer.getComponent(0), 
				position.getComponent(1) + offsetY + drawer.getComponent(1)
			);
		}
		
		// draw the closing
		path.lineTo(
			position.getComponent(0) + offsetX,
			position.getComponent(1) + offsetY
		);
		
		return path;
	}

	
	@Override
	public void update(String message) {
		if (message.compareTo("signal right") == 0)  {
			this.turnSignal = gui.UIAdapterVehicle.turnSignalType.right;
		} else if (message.compareTo("signal left") == 0) {
			this.turnSignal = gui.UIAdapterVehicle.turnSignalType.left;
		} else if (message.compareTo("signal off") == 0) {
			this.turnSignal = gui.UIAdapterVehicle.turnSignalType.off;
		}
		
	}
}


