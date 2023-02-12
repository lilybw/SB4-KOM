package dk.sdu.mmmi.cbse.entities;

import dk.sdu.mmmi.cbse.managers.ScreenManager;

public abstract class SpaceObject {
	
	protected float x;
	protected float y;
	
	protected float velocityX;
	protected float velocityY;
	
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
	
}


















