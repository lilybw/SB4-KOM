package dk.sdu.mmmi.cbse.entities;

import dk.sdu.mmmi.cbse.managers.ScreenManager;
import dk.sdu.mmmi.cbse.objectCapture.ICapture;

public abstract class SpaceObject {

	protected float x;
	protected float y;

	protected float velocityX;
	protected float velocityY;

	public SpaceObject setX(float x) {
		this.x = x;
		return this;
	}

	public SpaceObject setY(float y) {
		this.y = y;
		return this;
	}

	public SpaceObject setVelocityX(float velocityX) {
		this.velocityX = velocityX;
		return this;
	}

	public SpaceObject setVelocityY(float velocityY) {
		this.velocityY = velocityY;
		return this;
	}

	public SpaceObject setRadians(float radians) {
		this.radians = radians;
		return this;
	}

	public SpaceObject setSpeed(float speed) {
		this.speed = speed;
		return this;
	}

	protected float radians;
	protected float speed;
	protected float rotationSpeed;

	protected int width;
	protected int height;

	protected float[] shapex;
	protected float[] shapey;

	protected void wrap() {
		if(x < ScreenManager.BOUNDARY.getColliderMesh().minX().get()) x = ScreenManager.WIDTH;
		if(x > ScreenManager.BOUNDARY.getColliderMesh().maxX().get()) x = 0;
		if(y < ScreenManager.BOUNDARY.getColliderMesh().minY().get()) y = ScreenManager.HEIGHT;
		if(y > ScreenManager.BOUNDARY.getColliderMesh().maxY().get()) y = 0;
	}

	public float getX() {return x;}
	public float getY() {return y;}
	public float getSpeed() {return speed;}
	public float getRadians() {return radians;}
	public float getRotationSpeed() {return rotationSpeed;}
	public float getVelX() {return velocityX;}
	public float getVelY() {return velocityY;}

	
}


















